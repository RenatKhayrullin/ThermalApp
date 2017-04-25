package com.app.mvc.jsonview;

import com.app.mvc.TreeModel.Elements;
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
    List<String> objProperties;
    @JsonView(Views.Public.class)
    List<String> dataProperties;
    @JsonView(Views.Public.class)
    List<String> individuals;
    @JsonView(Views.Public.class)
    List<String> label;
    @JsonView(Views.Public.class)
    List<String> comment;
    @JsonView(Views.Public.class)
    String instInfo;

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public List<String> getObjProperties() { return objProperties; }

    public void setObjProperties(List<String> objProperties) { this.objProperties = objProperties; }

    public List<String> getDataProperties() { return dataProperties; }

    public void setDataProperties(List<String> dataProperties) { this.dataProperties = dataProperties; }

    public List<String> getIndividuals() { return individuals; }

    public void setIndividuals(List<Elements> individuals) {
        ArrayList<String> stringInds = new ArrayList<String>();
        for (Elements ind: individuals) {
            String element = ind.getElement();
            stringInds.add(element);
        }
        this.individuals = stringInds;
    }

    public void setStringIndividuals(List<String> individuals) {
        this.individuals = individuals;
    }

    public List<String> getLabel() { return this.label; }

    public void setLabel(List<String> label) { this.label = label; }

    public List<String> getComment() { return this.comment; }

    public void setComment(List<String> comment) { this.comment = comment; }

    public String getInstInfo() { return instInfo; }

    public void setInstInfo(String instInfo) { this.instInfo = instInfo; }

}
