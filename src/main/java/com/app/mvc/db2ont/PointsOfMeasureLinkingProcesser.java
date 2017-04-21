package com.app.mvc.db2ont;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

class LinkedGroupKey {
    private String DataSet;
    private int    RowCount;

    public LinkedGroupKey(String dataSet, int rowCount) {
        DataSet = dataSet;
        RowCount = rowCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LinkedGroupKey)) return false;

        LinkedGroupKey k = (LinkedGroupKey) o;
        return this.DataSet.  contentEquals(k.DataSet) &&
                this.RowCount == k.RowCount;
    }

    @Override
    public int hashCode() {
        return DataSet.length() + 31 * RowCount;
    }
}

public class PointsOfMeasureLinkingProcesser {
    public static void doPMLinking(OntModel model) {
        Map<LinkedGroupKey, Set<Individual>> groups = new HashMap<LinkedGroupKey, Set<Individual>>();
        OntProperty propLinked = model.getOntProperty("http://www.ThermalDb.ru#linkedWithQuantityValues");
        
        groups = divideIntoGroups(model, groups);
        Set<LinkedGroupKey> keys = groups.keySet();
        Iterator<LinkedGroupKey> it = keys.iterator();
        while (it.hasNext()) {
            LinkedGroupKey k = it.next();
            Set<Individual> inds = groups.get(k);
            Iterator<Individual> it2 = inds.iterator();
            while (it2.hasNext()) {
                Individual ind1 = it2.next();
                
                Iterator<Individual> it3 = inds.iterator();
                while (it3.hasNext()) {
                    Individual ind2 = it3.next();
                    String name1 = ind1.getLocalName();
                    String name2 = ind2.getLocalName(); 
                    if (!name1.contentEquals(name2)) {
                        ind1.addProperty(propLinked, ind2);
                    }
                }
            }
        }
        
        clearRowCount(model);
    }
    
    public static Map<LinkedGroupKey, Set<Individual>> divideIntoGroups(OntModel model, Map<LinkedGroupKey, Set<Individual>> groups) {
        OntProperty propRowCount  = model.getOntProperty("http://www.ThermalDb.ru#hasRowCount");
        OntProperty propDataSetId = model.getOntProperty("http://www.ThermalDb.ru#isAPartOfDataSet");
        OntClass    clsPM         = model.getOntClass   ("http://www.ThermalDb.ru#PointOfMeasure");


        ExtendedIterator<Individual> it = model.listIndividuals(clsPM);
        while (it.hasNext()) {
            Individual ind = it.next();

            String  DataSetName   = ind.getPropertyValue(propDataSetId).asResource().getLocalName();
            Integer RowCountVal   = ind.getPropertyValue(propRowCount) .asLiteral ().getInt();
            if (RowCountVal == 0) {     //for ontology constants
                continue;
            }
            LinkedGroupKey k = new LinkedGroupKey(DataSetName, RowCountVal);
            if (groups.get(k) == null)
                groups.put(k, new HashSet<Individual>());
            groups.get(k).add(ind);
        }
        
        return groups;
    }
    
    public static void clearRowCount(OntModel model) {
        OntProperty propRowCount  = model.getOntProperty("http://www.ThermalDb.ru#hasRowCount");
        ExtendedIterator<Individual> it = model.listIndividuals();
        while (it.hasNext()) {
            Individual ind = it.next();
            ind.removeAll(propRowCount);
        }
        propRowCount.remove();
    }
}
