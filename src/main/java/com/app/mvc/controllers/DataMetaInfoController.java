package com.app.mvc.controllers;

import com.app.mvc.controllers.FormClasses.MetaInfo;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metadata")
public class DataMetaInfoController  {

    @RequestMapping(value = "/datainfo", headers = {"content-type=application/json"})
    @ResponseBody
    public String ShowMetaInfo(@RequestBody final MetaInfo dataMetaInfo, ModelMap model){
        Integer id = dataMetaInfo.getSubstanceid();
        System.out.println("PASSED PARAMS "+id.toString());
        model.addAttribute("metaData", id);

        return "JSON parsed";
    }
}