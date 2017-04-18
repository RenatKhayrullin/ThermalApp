package com.app.mvc.controllers;

import org.springframework.stereotype.Controller;
import com.app.mvc.TreeModel.CourtBranch;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import java.util.List;
import com.app.mvc.OntologyModelDao.StardogDao;
import org.springframework.ui.Model;

import java.util.Locale;

@Controller
public class OntologyController {
    private static final Logger logger = LoggerFactory.getLogger(StardogDao.class);

    @Autowired
    private StardogDao stardogDAO;

    //вывод таблички иерархии классов
    @RequestMapping(value="/OntTree", method = RequestMethod.GET)
    public String showOntTree(Locale locale, Model model) {
        List<CourtBranch> ontList = stardogDAO.makeOntTree();
        logger.debug("Found " + ontList.size() + " number of dogs in the store");
        model.addAttribute("ontList", ontList);
        return "OntTree";
    }
}
