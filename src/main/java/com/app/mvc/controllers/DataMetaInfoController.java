package com.app.mvc.controllers;

import com.app.mvc.applicationServices.MetaInfoService;
import com.app.mvc.controllers.FormClasses.MetaInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class DataMetaInfoController  {
    @Autowired
    private MetaInfoService metaInfoService;

    @RequestMapping(value = "/metadata/datainfo", method = RequestMethod.POST /*, *headers = {"content-type=application/json"}*/)
    public String ShowMetaInfo(@ModelAttribute final MetaInfo dataMetaInfo, ModelMap model) throws JSONException {

        String JSONdata = metaInfoService.getMetaInfo(dataMetaInfo.getSubstanceid(), dataMetaInfo.getStateid(),
                dataMetaInfo.getPropertyid());
        System.out.println(JSONdata);
        model.addAttribute("dataMetaInfo", JSONdata);
        return "MetaInfoPage";
    }

    @RequestMapping(value = "/metadata/metainfo", method = RequestMethod.POST /*, *headers = {"content-type=application/json"}*/)
    public String ShowData(@RequestParam String metaData, ModelMap model) {

        System.out.println(metaData);
        return "";
    }
}