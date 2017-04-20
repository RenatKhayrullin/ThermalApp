package com.app.mvc.jsonview;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

//katya says hello
public class OntProperties
{
    @JsonView(Views.Public.class)
    String msg;
    @JsonView(Views.Public.class)
    String code;
    @JsonView(Views.Public.class)
    List<String> objProperties;
    @JsonView(Views.Public.class)
    List<String> dataProperties;

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public List<String> getObjProperties() { return objProperties; }

    public void setObjProperties(List<String> objProperties) { this.objProperties = objProperties; }

    public List<String> getDataProperties() { return dataProperties; }

    public void setDataProperties(List<String> dataProperties) { this.dataProperties = dataProperties; }
}
