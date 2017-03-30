package com.app.mvc.controllers.FormClasses;


import java.util.List;

public class MetaInfo {

    private List<Long> substanceid;
    private List<Long> stateid;
    private List<Long> propertyid;

    public MetaInfo() {}

    public List<Long> getSubstanceid() {
        return substanceid;
    }

    public void setSubstanceid(List<Long> substanceid) {
        this.substanceid = substanceid;
    }

    public List<Long> getStateid() {
        return stateid;
    }

    public void setStateid(List<Long> stateid) {
        this.stateid = stateid;
    }

    public List<Long> getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(List<Long> propertyid) {
        this.propertyid = propertyid;
    }

    @Override
    public String toString() {
        return "DataMetaInfo [substanceid=" + substanceid + ", stateid=" + stateid + ", propertyid=" + propertyid + "]";
    }
}
