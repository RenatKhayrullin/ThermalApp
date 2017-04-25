package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.JenaDAO;
import com.app.mvc.OntologyModelDao.StardogDao;
import com.app.mvc.TreeModel.Elements;
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
    @Autowired
    private StardogDao stardogDAO;

    @Autowired
    private JenaDAO jenaDAO;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    //вывод objectproperty и dataproperty для классов
    @RequestMapping(value="/OntTree/OntProperty")
    public OntProperties GetOntProperty (@RequestBody String thisClass) throws IOException {

        String ontClassName = thisClass.replace("\"","");

        System.out.println("CLASS NAME : "+ ontClassName);
        /*
        List<String> ontObjProperties = stardogDAO.getObjProperty(ontClassName);
        List<String> ontDataProperties = stardogDAO.getDataProperty(ontClassName);
        List<Elements> ontInstances = stardogDAO.getInstances(ontClassName);
        */
        List<String> ontObjProperties = jenaDAO.getObjectProperties(ontClassName);
        List<String> ontDataProperties = jenaDAO.getDataProperties(ontClassName);
        List<String> ontInstances = jenaDAO.getInstances(ontClassName);
        List<String> ontLabel = jenaDAO.getClassProperty(ontClassName, label);
        List<String> ontComment = jenaDAO.getClassProperty(ontClassName, comment);
        //List<String> ontSameAs = jenaDAO.getSameAs("http://www.ThermalDb.ru#Oxygen");

        //System.out.println("OLD: "+ontSameAs);

        //jenaDAO.setSameAs("http://www.ThermalDb.ru#Oxygen", "NewOxygen");
        //ontSameAs = jenaDAO.getSameAs("http://www.ThermalDb.ru#Oxygen");
        //System.out.println("NEW: "+ontSameAs);

        OntProperties ontProperties = new OntProperties();

        if (ontObjProperties.size() > 0 || ontDataProperties.size() > 0 || ontLabel.size() > 0) {
            ontProperties.setCode("200");
            ontProperties.setMsg("");
            ontProperties.setDataProperties(ontDataProperties);
            ontProperties.setObjProperties(ontObjProperties);
            ontProperties.setStringIndividuals(ontInstances);
            ontProperties.setLabel(ontLabel);
            ontProperties.setComment(ontComment);
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

        List<String> ontObjProperties = jenaDAO.getObjectProperties(className);
        List<String> ontDataProperties = jenaDAO.getDataProperties(className);

        for (String objProp: ontObjProperties) {
            String result = jenaDAO.getInstanceProperty(ontInstName, objProp);
            if (!result.isEmpty()) {
                response.put(objProp, result);
            }
        }

        for (String dataProp: ontDataProperties) {
            String result = jenaDAO.getInstanceProperty(ontInstName, dataProp);
            if (!result.isEmpty()) {
                response.put(dataProp, result);
            }
        }

        List<String> ontSameAs = jenaDAO.getSameAs(ontInstName);
        response.put("sameAs", ontSameAs.toArray());


        //response.put("class", className);

        //System.out.println(response.toString());

        OntProperties ontProperties = new OntProperties();
        ontProperties.setInstInfo(response.toString());
        return ontProperties;

    }
    }
