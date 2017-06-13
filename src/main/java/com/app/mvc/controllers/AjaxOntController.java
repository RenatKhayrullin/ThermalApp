package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.JenaDAO;
import com.app.mvc.jsonview.OntProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.apache.jena.vocabulary.RDFS.comment;
import static org.apache.jena.vocabulary.RDFS.label;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.app.mvc.jsonview.Views;

@RestController
public class AjaxOntController
{
    //@Autowired
    //private StardogDao stardogDAO;

    @Autowired
    private JenaDAO jenaDAO;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    //вывод objectproperty и dataproperty для классов
    @RequestMapping(value="/OntTree/OntProperty")
    public OntProperties GetOntProperty (@RequestBody String thisClass) throws IOException, JSONException {

        String ontClassName = thisClass.replace("\"","");

        System.out.println("CLASS NAME : "+ ontClassName);
        JSONArray ontObjProperties = jenaDAO.getObjectProperties(ontClassName);
        JSONArray ontDataProperties = jenaDAO.getDataProperties(ontClassName);
        JSONArray ontInstances = jenaDAO.getInstances(ontClassName);
        List<String> ontLabel = jenaDAO.getClassProperty(ontClassName, label);
        List<String> ontComment = jenaDAO.getClassProperty(ontClassName, comment);
        List<String> ontEquivalentClass = jenaDAO.getOwlProperty(ontClassName, "equivalentClass");

        OntProperties ontProperties = new OntProperties();

        if (ontObjProperties.length() > 0 || ontDataProperties.length() > 0
                || ontLabel.size() > 0 || ontEquivalentClass.size() > 0) {
            ontProperties.setCode("200");
            ontProperties.setMsg("");
            ontProperties.setDataProperties(ontDataProperties.toString());
            ontProperties.setObjProperties(ontObjProperties.toString());
            ontProperties.setStringIndividuals(ontInstances.toString());
            ontProperties.setLabel(ontLabel);
            ontProperties.setComment(ontComment);
            if (ontEquivalentClass.size() > 0)
                ontProperties.setEquivalentClass(ontEquivalentClass.get(0));
        } else {
            ontProperties.setCode("400");
            ontProperties.setMsg("No data!");
        }

        return ontProperties;
    }

    @JsonView(Views.Public.class)
    //вывод info для экземпляров
    @RequestMapping(value="/OntTree/InstInfo")
    public OntProperties GetInstanceInfo (@RequestBody String thisInstance) throws IOException, JSONException {

        String ontInstName = thisInstance.replace("\"","");
        System.out.println("INSTANCE NAME : "+ ontInstName);

        String className = jenaDAO.getClassName(ontInstName);
        JSONObject response = new JSONObject();

        JSONArray ontObjProperties = jenaDAO.getObjectProperties(className);
        JSONArray ontDataProperties = jenaDAO.getDataProperties(className);

        for (int i = 0; i < ontObjProperties.length(); ++i) {
            String objProp = ontObjProperties.getJSONObject(i).getString("field");
            String result = jenaDAO.getInstanceProperty(ontInstName, objProp);
            if (!result.isEmpty()) {
                response.put(objProp, result);
            }
        }

        for (int i = 0; i < ontDataProperties.length(); ++i) {
            String dataProp = ontDataProperties.getJSONObject(i).getString("field");
            String result = jenaDAO.getInstanceProperty(ontInstName, dataProp);
            if (!result.isEmpty()) {
                response.put(dataProp, result);
            }
        }

        List<String> ontSameAs = jenaDAO.getOwlProperty(ontInstName, "sameAs");

        response.put("sameAs", ontSameAs.toArray());

        OntProperties ontProperties = new OntProperties();
        ontProperties.setInstInfo(response.toString());
        return ontProperties;

    }
}
