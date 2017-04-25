package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.JenaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by Катерина on 25.04.2017.
 */
@Controller
public class BindController {

    @Autowired
    private JenaDAO jenaDAO;

    @RequestMapping(value="/bind", method= RequestMethod.POST)
    public ModelAndView setBind(Model model, @RequestParam String link,
                          @RequestParam(value="entity", required=true) String entity,
                          @RequestParam(value="resource", required=true) String resource) throws IOException {

        if (resource.toLowerCase().contains("chemspider")) {
            jenaDAO.setSameAs(entity, link);
            System.out.println(entity+"   "+resource+"    "+link);
        }
        /*
        System.out.println("ENTITY:  "+entity);
        System.out.println("RESOURCE:  "+resource);
        System.out.println("LINK:  "+link);
        */
        return new ModelAndView("redirect:/OntTree");
    }

}
