package com.app.mvc.jsonview;

import com.complexible.stardog.plan.filter.functions.rdfterm.Str;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

//katya says hello
public class OntProperties
{
    @JsonView(Views.Public.class)
    String msg;
    @JsonView(Views.Public.class)
    String code;
    @JsonView(Views.Public.class)
    String objProperties;
    @JsonView(Views.Public.class)
    String dataProperties;
    @JsonView(Views.Public.class)
    String individuals;
    @JsonView(Views.Public.class)
    List<String> label;
    @JsonView(Views.Public.class)
    List<String> comment;
    @JsonView(Views.Public.class)
    String instInfo;
    @JsonView(Views.Public.class)
    String equivalentClass;

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getObjProperties() { return objProperties; }

    public void setObjProperties(String objProperties) { this.objProperties = objProperties; }

    public String getDataProperties() { return dataProperties; }

    public void setDataProperties(String dataProperties) { this.dataProperties = dataProperties; }

    public String getIndividuals() { return individuals; }

    public void setStringIndividuals(String individuals) {
        this.individuals = individuals;
    }

    public List<String> getLabel() { return this.label; }

    public void setLabel(List<String> label) { this.label = label; }

    public List<String> getComment() { return this.comment; }

    public void setComment(List<String> comment) { this.comment = comment; }

    public String getInstInfo() { return instInfo; }

    public void setInstInfo(String instInfo) { this.instInfo = instInfo; }

    public String getEquivalentClass() { return equivalentClass; }

    public void setEquivalentClass(String equivalentClass) { this.equivalentClass = equivalentClass; }

}
