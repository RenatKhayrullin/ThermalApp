package com.app.mvc.OntologyModelDao;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.method.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
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
    private OntModel ontModel;
    private InfModel inf;
    private static int counter = 0;

    public JenaDAO() throws FileNotFoundException {
        filename = Constants.HOME + "OntologyStorage.rdf";
        model = ModelFactory.createDefaultModel();//.read(FileManager.get().open(filename), "");
        InputStream in = new FileInputStream(filename);
        model = model.read(in, null);
        inf = ModelFactory.createRDFSModel(model);
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
    }

    private String createNewSubject(String name) {
        return Constants.WWW + "#" + name;
    }

    private List<String> getSearchResults(String queryString) {

        Query jenaquery = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(jenaquery, inf);
        ResultSet jenaresults = qexec.execSelect();
        ArrayList<String> results = new ArrayList<String>();

        while(jenaresults.hasNext()) {
            QuerySolution s = jenaresults.next();
            System.out.println("JenaResult   "+s.get("entity"));
            String soln = s.toString();
            System.out.println(soln);
            if (soln.contains("_:b")) continue;
            if ((soln.contains("#")) && (soln.contains(">")))
                soln = soln.substring(soln.indexOf('#')+1, soln.indexOf('>'));
            if (soln.contains("\""))
                    soln = soln.substring(soln.indexOf('"') + 1, soln.lastIndexOf('"'));
            if (soln.contains("<") && soln.contains(">"))
                soln = soln.substring(soln.indexOf('<')+1, soln.indexOf('>'));
            results.add(soln);
        }
        return results;
    }

    private JSONArray getResultJson(String query, String field) throws JSONException {
        Query jenaQuery = QueryFactory.create(query) ;
        QueryExecution qexec = QueryExecutionFactory.create(jenaQuery, inf);
        ResultSet jenaResults = qexec.execSelect();
        JSONArray result = new JSONArray();

        while(jenaResults.hasNext()) {
            String solution = jenaResults.next().get(field).toString();
            if (solution.contains("#"))
                solution = solution.substring(solution.indexOf('#')+1);
            else
            if (solution.contains("\""))
                solution = solution.substring(solution.indexOf('"')+1, solution.lastIndexOf('"'));
            else continue;
            JSONObject object = new JSONObject();
            object.put("field", solution);
            object.put("info", solution);
            result.put(object);
        }
        return result;
    }

    //DataProperties
    public JSONArray getDataProperties(String className) throws JSONException {
        String nameUri = createNewSubject(className);

        String query = "select ?dataProperty WHERE " +
                "{?dataProperty a <" + Constants.OWL + "#DatatypeProperty> ." +
                "?dataProperty <" + Constants.RDFS + "#domain> <" +nameUri+"> .}";

        System.out.println("DATA PROPERTIES: "+query);
        return getResultJson(query, "dataProperty");
    }

    //ObjectProperties
    public JSONArray getObjectProperties(String className) throws JSONException {
        String nameUri = createNewSubject(className);

        String query = "select ?objectProperty WHERE " +
                "{?objectProperty a <" + Constants.OWL + "#ObjectProperty> ." +
                "?objectProperty <" + Constants.RDFS + "#domain> <"+nameUri+"> .}";

        System.out.println("OBJECT PROPERTIES: "+query);
        return getResultJson(query, "objectProperty");
    }

    //Instances
    public JSONArray getInstances(String className) throws JSONException {
        String nameUri = createNewSubject(className);

        String query = "SELECT ?instance { ?instance <" + Constants.ELEMENT + "> <"+nameUri+">}";

        System.out.println("INSTANCES: "+query);
        return getResultJson(query, "instance");
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
        Statement resourceProperty = resource.getProperty(property);
        if (resourceProperty == null) return result;
        else {
            String prop = resourceProperty.getObject().toString();
            result.add(prop);
        }
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


    //owl properties
    public List<String> getOwlProperty(String entityName, String property) throws IOException {
        String nameUri = createNewSubject(entityName);

        String query = "SELECT ?entity WHERE {<" + nameUri+ "> <" + Constants.OWL +
                "#" + property + "> ?entity.}";

        System.out.println("OWL PROPERTY:   "+query);
        List <String> queryResult = getSearchResults(query);
        return queryResult;
    }

    public String getTree() throws IOException, JSONException {

        String query = "select distinct ?type ?supertype \n" +
                "WHERE {\n" +
                " { ?type a <http://www.w3.org/2002/07/owl#Class> . } \n" +
                "OPTIONAL { ?type <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?supertype } .\n" +
                "OPTIONAL { ?type <http://www.w3.org/2002/07/owl#equivalentClass> " +
                "[a <http://www.w3.org/2002/07/owl#Class> ; <http://www.w3.org/2002/07/owl#intersectionOf> " +
                "[<http://www.w3.org/1999/02/22-rdf-syntax-ns#rest>*/<http://www.w3.org/1999/02/22-rdf-syntax-ns#first> ?supertype] ]. }}";

        System.out.println(query);
        return getJsonTree(query);
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

    public void setSubClass(String instName, String link) throws IOException {
        String className = getClassName(instName);
        String classUri = createNewSubject(className);
        Resource superClass = model.getResource(classUri);
        System.out.println("SuperClass   " + superClass.getURI());

        Resource existingResource = superClass;
        String newName = "", newnameUri = "";
        Property property = model.getProperty(Constants.SUBCLASS.toString());

        do {
            ++counter;
            newName = instName + '-' + counter;
            newnameUri = createNewSubject(newName);
            existingResource = model.getResource(newnameUri);
        }
        while (model.contains(existingResource, property));

        System.out.println("ExistingResource   " + newnameUri);

        OntClass newResource = ontModel.createClass(newnameUri);
        newResource.addSuperClass(superClass);
        Resource differentResource = ontModel.getResource(link);
        newResource.setEquivalentClass(differentResource);

        OutputStream out = new FileOutputStream(filename);
        ontModel.write(out);
        out.close();

    }

    private String getJsonTree(String query) throws JSONException {
        Query jenaquery = QueryFactory.create(query) ;
        QueryExecution qexec = QueryExecutionFactory.create(jenaquery, ontModel);
        ResultSet jenaresults = qexec.execSelect();
        int id = 1;
        JSONArray array = new JSONArray();
        JSONObject allTypes = new JSONObject();
        JSONObject list = new JSONObject();
        list.put("Thing", 1);
        JSONObject object = new JSONObject();
        object.put("id", list.getString("Thing"));
        object.put("parent", "#");
        object.put("text", "Thing");
        array.put(object);

        while(jenaresults.hasNext()) {
            QuerySolution solution = jenaresults.next();
            String type = solution.get("type").toString();
            if (type.contains("#")) {
                if (solution.contains("supertype")) {
                    String supertype = solution.get("supertype").toString();
                    if (supertype.contains("#") && ! supertype.equalsIgnoreCase(type)) {
                        if (! allTypes.has(type)) allTypes.put(type, supertype);
                    }
                }
                if (! allTypes.has(type)) {
                    allTypes.put(type, "Thing");
                }
            }
        }
        Iterator<String> keys = allTypes.keys();
        while (keys.hasNext()){
            String key = keys.next();
            String value = allTypes.getString(key);

            if (! list.has(key)) {
                ++id;
                list.put(key, id);
            }
            if (! list.has(value)) {
                ++id;
                list.put(value, id);
            }
            JSONObject newObject = new JSONObject();
            newObject.put("id", list.getString(key));
            newObject.put("parent", list.getString(value));
            newObject.put("text", key.substring(key.indexOf('#')+1));
            array.put(newObject);
        }
        System.out.println(array);
        return array.toString();
    }
}
