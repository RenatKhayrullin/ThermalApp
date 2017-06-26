package com.app.mvc.controllers;

import com.app.mvc.applicationServices.MetaInfoService;
import com.app.mvc.controllers.FormClasses.MetaInfo;
import com.app.mvc.controllers.FormClasses.NumericData;
import com.app.mvc.dataBaseDomainModel.DataSource;
import com.app.mvc.dataBaseDomainModel.MeasurementUncertainty;
import com.app.mvc.dataBaseDomainModel.UncertaintyType;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class DataMetaInfoController  {
    @Autowired
    private MetaInfoService metaInfoService;

    @RequestMapping(value = "/metadata/datainfo", method = RequestMethod.POST  /*, headers = {"charset=UTF-8"}*/)
    public String ShowMetaInfo(@ModelAttribute final MetaInfo dataMetaInfo, ModelMap model) throws JSONException {

        String JSONdata = metaInfoService.getMetaInfo(
                dataMetaInfo.getSubstanceid(),
                dataMetaInfo.getStateid(),
                dataMetaInfo.getPropertyid());
        System.out.println("METADATA:  "+ JSONdata);

        model.addAttribute("dataMetaInfo", JSONdata);
        return "MetaInfoPage";
    }

    @RequestMapping(value = "/metadata/numericinfo", method = RequestMethod.POST /*, headers = {"charset=UTF-8"}*/)
    public String ShowData(@RequestParam String metaData, ModelMap model) throws IOException, JSONException {

        System.out.println("MetaData:  "+metaData);
        JSONObject numericData = metaInfoService.getNumericData(metaData);

        model.addAttribute("substance", numericData.get("substance"));
        model.addAttribute("state", numericData.get("state"));
        model.addAttribute("data", numericData);
        return "NumericDataPage";
    }
}