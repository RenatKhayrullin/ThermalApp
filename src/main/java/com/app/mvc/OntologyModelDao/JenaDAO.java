package com.app.mvc.OntologyModelDao;

import com.app.mvc.TreeModel.CourtBranch;
import com.app.mvc.TreeModel.OntList;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.vocabulary.RDFS.Resource;
import static org.apache.jena.vocabulary.RDFS.comment;
import static org.apache.jena.vocabulary.RDFS.label;

/**
 * Created by Катерина on 21.04.2017.
 */
@Repository
@Component
public class JenaDAO {

    private String filename;
    private Model model;
    private InfModel inf;

    public JenaDAO() throws FileNotFoundException {
        filename = Constants.HOME + "OntologyStorage.rdf";
        model = ModelFactory.createDefaultModel();//.read(FileManager.get().open(filename), "");
        InputStream in = new FileInputStream(filename);
        model = model.read(in, null);
        inf = ModelFactory.createRDFSModel(model);
    }

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
            if ((soln.contains("#")) && (soln.contains(">")))
                soln = soln.substring(soln.indexOf('#')+1, soln.indexOf('>'));
            if (soln.contains("\""))
                    soln = soln.substring(soln.indexOf('"') + 1, soln.lastIndexOf('"'));
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

    //Class
    public String getClassName(String instName) {
        String nameUri = createNewSubject(instName);

        String query = "SELECT ?class { <" + nameUri + "> <" + Constants.ELEMENT + ">  ?class. ?class a <" +
                Constants.OWL + "#Class>}";

        System.out.println("CLASS: "+query);
        List<String> result = getSearchResults(query);

        return result.get(0);
    }

    //Class Property (RDFS)
    public List<String> getClassProperty(String className, Property property) {
        String nameUri = createNewSubject(className);
        ArrayList<String> result = new ArrayList<String>();
        Resource resource = inf.getResource(nameUri);
        //System.out.println(resource.toString());
        Statement resourceProperty = resource.getProperty(property);
        if (resourceProperty == null) return result;
        else {
            String prop = resourceProperty.getObject().toString();
            result.add(prop);
        }
        //System.out.println(resourceLabel);
        return result;
    }

    //Instance Property
    public String getInstanceProperty(String instName, String propertyName) {
        String nameUri = createNewSubject(instName);

        String query = "SELECT ?value WHERE {<" + nameUri + "> <" + Constants.WWW +
                "#" + propertyName + "> ?value.}";

        System.out.println(query);
        List <String> queryResult = getSearchResults(query);
        if (queryResult.size() > 0) {
            String result = queryResult.get(0);
            return result;
        } else return "";
    }


    //sameAs
    public List<String> getSameAs(String instName) throws IOException {
        String nameUri = createNewSubject(instName);

        String query = "SELECT ?entity WHERE {<" + nameUri+ "> <" + Constants.OWL +
                "#sameAs> ?entity.}";

        System.out.println(query);
        List <String> queryResult = getSearchResults(query);
        return queryResult;
    }

    public void setSameAs(String className, String newEntity) throws IOException {
        String nameUri = createNewSubject(className);
        Resource resource = model.getResource(nameUri);
        if (resource == null) return;
        System.out.println(resource.toString());
        Property property = model.getProperty("http://www.w3.org/2002/07/owl#sameAs");
        System.out.println(property.getURI());
        Statement statement = model.createStatement(resource, property, newEntity);
        model.add(statement);
        OutputStream out = new FileOutputStream(filename);
        model.write(out);
        out.close();
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
