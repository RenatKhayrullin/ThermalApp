package com.app.mvc.applicationServices;

import com.app.mvc.controllers.FormClasses.NumericData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Катерина on 22.05.2017.
 */
public interface MetaInfoService {

    public String getMetaInfo(List<Long> substance, List<Long> state, List<Long> quantity) throws JSONException;
    public JSONObject getNumericData(String metaData) throws JSONException;

}
