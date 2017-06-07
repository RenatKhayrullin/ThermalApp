package com.app.mvc.controllers;

import com.app.mvc.applicationServices.MetaInfoService;
import com.app.mvc.controllers.FormClasses.MetaInfo;
import com.app.mvc.controllers.FormClasses.NumericData;
import com.app.mvc.dataBaseDomainModel.DataSource;
import com.app.mvc.dataBaseDomainModel.MeasurementUncertainty;
import com.app.mvc.dataBaseDomainModel.UncertaintyType;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
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

        String JSONdata = metaInfoService.getMetaInfo(dataMetaInfo.getSubstanceid(), dataMetaInfo.getStateid(),
                dataMetaInfo.getPropertyid());
        System.out.println(JSONdata);
        model.addAttribute("dataMetaInfo", JSONdata);
        return "MetaInfoPage";
    }

    @RequestMapping(value = "/metadata/numericinfo", method = RequestMethod.POST /*, headers = {"charset=UTF-8"}*/)
    public String ShowData(@RequestParam String metaData, ModelMap model) throws IOException {

        //System.out.println(metaData);
        List<NumericData> numericDataList = metaInfoService.getNumericData(metaData);
        ObjectMapper jsonDataMapper = new ObjectMapper();
        String data = jsonDataMapper.writeValueAsString(numericDataList);

        List<DataSource> dataSources = metaInfoService.getAllDataSources();
        List<UncertaintyType> measurementUncertainties = metaInfoService.getAllMeasurementUncertainties();

        String sources = jsonDataMapper.writeValueAsString(dataSources);
        String uncertainties = jsonDataMapper.writeValueAsString(measurementUncertainties);

        //String encodeSources = new String(sources.getBytes("UTF-8"),"ISO-8859-1");
        System.out.println(sources);

        model.addAttribute("dataSources", sources);
        model.addAttribute("uncertainties", uncertainties);
        model.addAttribute("numericData", data);
        return "NumericDataPage";
    }
}