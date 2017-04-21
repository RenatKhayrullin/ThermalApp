package com.app.mvc.db2ont;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (	this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }
}

public class SubstanceInStatesClassDataGenerator {
    public static final String NS = "http://www.ThermalDb.ru#";

    public static final String SubstanceInStateURI = NS+"SubstanceInState";
    public static final String SubstanceURI = NS+"ChemicalSubstance";
    public static final String StateURI = NS+"State";
    public static final String DataSetURI = NS+"DataSet";
    public static final String PointOfMeasureURI = NS+"PointOfMeasure";

    public static final String isASubstanceURI = NS+"isASubstance";
    public static final String isInStateURI = NS+"isInState";
    public static final String isADataForSubstanceInStateURI = NS+"isADataForSubstanceInState";
    public static final String isAPartOfDataSetURI = NS+"isAPartOfDataSet";

    public static void doGenerateSubstanceInStatesDataClasses (OntModel model) {
        OntProperty propIsASubstance = model.getOntProperty(isASubstanceURI);
        OntProperty propIsInState = model.getOntProperty(isInStateURI);
        OntClass clsSubstancesInStates = model.getOntClass(SubstanceInStateURI);

        Set<Pair<Individual, Individual>> pairsInds = new HashSet<Pair<Individual, Individual>>();
        ExtendedIterator<Individual> itSiS = model.listIndividuals(clsSubstancesInStates);
        while (itSiS.hasNext()) {
            Individual indSiS = itSiS.next();

            Individual indSubstance = ((OntResource) indSiS.getPropertyValue(propIsASubstance)).asIndividual();
            Individual indState = ((OntResource) indSiS.getPropertyValue(propIsInState)).asIndividual();
            pairsInds.add(new Pair(indSubstance, indState));
        }

        Iterator<Pair<Individual, Individual>> itPairsInds = pairsInds.iterator();
        while (itPairsInds.hasNext()) {
            Pair<Individual, Individual> pair = itPairsInds.next();
            generateClass(model, pair.getFirst(), pair.getSecond());
        }
    }

    public static void generateClass (OntModel model, Individual substance, Individual state) {
        //substance in state anon class
        OntProperty propIsASubstance = model.getOntProperty(isASubstanceURI);
        OntProperty propIsInState = model.getOntProperty(isInStateURI);
        OntClass clsSubstancesInStates = model.getOntClass(SubstanceInStateURI);

        HasValueRestriction restrSubstance = model.createHasValueRestriction(null, propIsASubstance, substance);
        HasValueRestriction restrState = model.createHasValueRestriction(null, propIsInState, state);

        IntersectionClass clsSubstancesInStatesIntersection =
                model.createIntersectionClass(null, model.createList(new RDFNode[] {clsSubstancesInStates, restrSubstance, restrState}));

        //intersect with datasets
        OntProperty propIsDataForSubstanceInState = model.getOntProperty(isADataForSubstanceInStateURI);
        OntClass clsDataDocumentDescriptions = model.getOntClass(DataSetURI);

        SomeValuesFromRestriction restrIsDataForSubstanceInState =
                model.createSomeValuesFromRestriction(null, propIsDataForSubstanceInState, clsSubstancesInStatesIntersection);

        IntersectionClass clsDataDocumentDescriptionsIntersection =
                model.createIntersectionClass(null, model.createList(new RDFNode[] {clsDataDocumentDescriptions, restrIsDataForSubstanceInState}));

        //final intersect
        OntProperty propIsAPartOfDataSet = model.getOntProperty(isAPartOfDataSetURI);
        OntClass clsPointsOfMeasure = model.getOntClass(PointOfMeasureURI);

        SomeValuesFromRestriction restrIsAPartOfDataSet =
                model.createSomeValuesFromRestriction(null, propIsAPartOfDataSet, clsDataDocumentDescriptionsIntersection);

        IntersectionClass clsResultIntersection =
                model.createIntersectionClass(null, model.createList(new RDFNode[] {clsPointsOfMeasure, restrIsAPartOfDataSet}));

        String ResultURI = NS+substance.getLocalName()+"In"+state.getLocalName()+"Data";
        OntClass clsResult = model.createClass(ResultURI);
        clsResult.addEquivalentClass(clsResultIntersection);
    }
}
