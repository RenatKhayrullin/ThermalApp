package com.app.mvc.applicationServices;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Катерина on 22.05.2017.
 */
public interface MetaInfoService {

    public String getMetaInfo(List<Long> substance, List<Long> state, List<Long> quantity) throws JSONException;
}
