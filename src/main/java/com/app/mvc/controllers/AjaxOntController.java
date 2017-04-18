package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.StardogDao;
import com.app.mvc.jsonview.OntProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.app.mvc.jsonview.Views;

@RestController
public class AjaxOntController
{
    @Autowired
    private StardogDao stardogDAO;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    //вывод objectproperty и dataproperty для классов
    @RequestMapping(value="/OntTree/OntProperty")
    public OntProperties GetOntProperty (@RequestBody String thisClass) {

        String ontClassName = thisClass.replace("\"","");

        System.out.println("CLASS NAME : "+ ontClassName);

        List<String> ontObjProperties = stardogDAO.getObjProperty(ontClassName);
        List<String> ontDataProperties = stardogDAO.getDataProperty(ontClassName);

        OntProperties ontProperties = new OntProperties();

        if (ontObjProperties.size() > 0 || ontDataProperties.size() > 0) {
            ontProperties.setCode("200");
            ontProperties.setMsg("");
            ontProperties.setDataProperties(ontDataProperties);
            ontProperties.setObjProperties(ontObjProperties);
        } else {
            ontProperties.setCode("400");
            ontProperties.setMsg("No data!");
        }

        return ontProperties;
    }
}
