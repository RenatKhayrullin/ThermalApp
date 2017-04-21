package com.app.mvc.OntologyModelDao;

import com.app.mvc.TreeModel.CourtBranch;
import com.app.mvc.TreeModel.OntList;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.vocabulary.RDFS.label;

/**
 * Created by Катерина on 21.04.2017.
 */
@Repository
@Component
public class JenaDAO {

    private String filename = "OntologyStorage.rdf";
    private Model model = ModelFactory.createDefaultModel().read(FileManager.get().open(filename), "");
    private InfModel inf = ModelFactory.createRDFSModel(model);

    public JenaDAO() {}

    private String createNewSubject(String name) {
        return Constants.WWW + "#" + name;
    }

    Model loadModel(String filename) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(filename);
        if (in == null) {
            throw new IllegalArgumentException( "File: " + filename + " not found");
        }

        // read the RDF/XML file
        model.read(in, "");
        return model;
    }

    List<String> getSearchResults(String queryString) {

        Query jenaquery = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(jenaquery, inf);
        ResultSet jenaresults = qexec.execSelect();

        ArrayList<String> results = new ArrayList<String>();

        while(jenaresults.hasNext()) {
            String soln = jenaresults.next().toString();
            //System.out.println(soln);
            soln = soln.substring(soln.indexOf('#')+1, soln.indexOf('>'));
            results.add(soln);
        }
        return results;
    }

    //DataProperties
    public List<String> getDataProperties(String className) {
        String nameUri = createNewSubject(className);

        String query = "select ?dataprop WHERE " +
                "{?dataprop a <" + Constants.OWL + "#DatatypeProperty> ." +
                "?dataprop <" + Constants.RDFS + "#domain> <" +nameUri+"> .}";

        System.out.println("SparqlQuery for DataProp: "+query);
        return getSearchResults(query);
    }

    //ObjectProperties
    public List<String> getObjectProperties(String className) {
        String nameUri = createNewSubject(className);

        String query = "select ?objprop WHERE " +
                "{?objprop a <" + Constants.OWL + "#ObjectProperty> ." +
                "?objprop <" + Constants.RDFS + "#domain> <"+nameUri+"> .}";

        System.out.println("SparqlQuery for DataProp: "+query);
        return getSearchResults(query);
    }

    //Instances
    public List<String> getInstances(String className) {
        String nameUri = createNewSubject(className);

        String query = "SELECT ?s { ?s <" + Constants.ELEMENT + "> <"+nameUri+">}";

        System.out.println("SparqlQuery for DataProp: "+query);
        return getSearchResults(query);
    }

    //Labels
    public List<String> getLabel(String className) {
        String nameUri = createNewSubject(className);
        ArrayList<String> result = new ArrayList<String>();
        Resource resource = inf.getResource(nameUri);
        //System.out.println(resource.toString());
        String resourceLabel = resource.getProperty(label).getObject().toString();
        //System.out.println(resourceLabel);
        result.add(resourceLabel);
        return result;
    }
    /*
    //Tree hierarchy
    List<OntList> getOntList() {

        String query = "select distinct ?type ?supertype WHERE { { ?type a <" + Constants.CLASS.toString() + "> . }" +
                "OPTIONAL { ?supertype <" + Constants.SUBCLASS.toString() + "> ?type } . " +
                "} ORDER BY ?type ?supertype";

        System.out.println("Class Hierarchy: " + query);

        Query jenaquery = QueryFactory.create(query) ;
        QueryExecution qexec = QueryExecutionFactory.create(jenaquery, inf);
        ResultSet jenaresults = qexec.execSelect();
        ArrayList<OntList> ontLists = new ArrayList<OntList>();
        int count = 0;

        while(jenaresults.hasNext()) {
            OntList ontList = new OntList();
            ++count;
            QuerySolution sol = jenaresults.nextSolution();
            String ssol = sol.toString();
            //System.out.println("SOL: " + ssol);
            String type = sol.getResource("type").toString();
            if (type.contains("#")) {
                type = type.substring(type.indexOf('#')+1);
                System.out.println("TYPE: " + type);
                ontList.setName(type);
            } else continue;

            RDFNode st = sol.getResource("supertype");
            String supertype = (st == null)? "#Nothing" : st.toString();
            if (supertype.contains("#")) {
                supertype = supertype.substring(supertype.indexOf('#')+1);
                System.out.println("SUPERTYPE: " + supertype);
            }
            ontList.setParent(supertype);
            ontLists.add(ontList);
        }
        System.out.println(count);
        return ontLists;
    }

    public List<CourtBranch> buildOntTree() {
        List<OntList> results = getOntList();
        List<CourtBranch> elements = new ArrayList<CourtBranch>();

        String Base = "Thing";
        int i = 0; //счетчик
        int b = 1; //идентификатор базового класса
        boolean is = true;

        elements.add(new CourtBranch(++i, Base, 0));

        List<OntList> clear = new ArrayList<>();

        for (final OntList result : results) {
            if(!result.getParent().equals("Thing") && !result.getParent().equals("Nothing") && !result.getParent().equals(result.getName())) {
                clear.add(new OntList(result.getName(), result.getParent()));
            }
        }

        while (!clear.isEmpty()) {

            //algoritm
            List<OntList> baseList = new ArrayList<OntList>(); //base class
            List<OntList> childList = new ArrayList<OntList>(); //classes with children


            //разбиение на 2 циакла - цикл с базовым классом и все остальное
            for (final OntList result : clear) {
                if (result.getName().equals(Base)) {
                    baseList.add(new OntList(result.getName(), result.getParent()));
                } else {
                    childList.add(new OntList(result.getName(), result.getParent()));
                }
            }

            //цикл по base class
            //заполняем elements
            for (final OntList base : baseList) {
                for (final OntList child : childList) {
                    if (base.getParent().equals(child.getParent())) {
                        is = false;
                    }
                }
                if (is == true) {
                    elements.add(new CourtBranch(++i, base.getParent(), b));
                }
                is = true;
            }
            if (!childList.isEmpty()) {
                Base = childList.get(childList.size() - 1).getName();

                //узнаем индекс базового элемента
                for (final CourtBranch element : elements) {
                    if (element.getName().equals(Base)) {
                        b = element.getId();
                        break;
                    }
                }
            }
            clear = childList;
        }

        return elements;
    }
    */
}
