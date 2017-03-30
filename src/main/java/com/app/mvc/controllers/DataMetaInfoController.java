package com.app.mvc.controllers;

import com.app.mvc.controllers.FormClasses.MetaInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class DataMetaInfoController  {

    @RequestMapping(value = "/metadata/datainfo", method = RequestMethod.POST /*, *headers = {"content-type=application/json"}*/)
    public String ShowMetaInfo(@ModelAttribute final MetaInfo dataMetaInfo, ModelMap model){
        model.addAttribute("dataMetaInfo", dataMetaInfo);
        return "MetaInfoPage";
    }
}