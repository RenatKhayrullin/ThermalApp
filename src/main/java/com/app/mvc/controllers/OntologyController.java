package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.JenaDAO;
import com.app.mvc.applicationServices.ResourceService;
import com.app.mvc.dataBaseDomainModel.ThirdPartyResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import org.springframework.ui.Model;

import java.util.Locale;

@Controller
public class OntologyController {

    @Autowired
    private JenaDAO jenaDAO;

    @Autowired
    private ResourceService resourceService;

    //вывод таблички иерархии классов
    @RequestMapping(value="/OntTree", method = RequestMethod.GET)
    public String showOntTree(Locale locale, Model model) throws IOException, JSONException {
        //List<CourtBranch> ontList = stardogDAO.makeOntTree();
        //List<CourtBranch> ontList = new ArrayList<>();

        String ontJson = jenaDAO.getTree();
        //logger.debug("Found " + ontList.size() + " number of dogs in the store");

        List<ThirdPartyResource> tpresources = resourceService.getAllResources();
        ObjectMapper jsonDataMapper = new ObjectMapper();
        String resources = jsonDataMapper.writeValueAsString(tpresources);
        //String ontJson = jsonDataMapper.writeValueAsString(ontList);

        model.addAttribute("thirdPartyRes", resources);
        //model.addAttribute("ontList", ontList);
        model.addAttribute("ontJson", ontJson);
        return "OntTree";
    }
}
