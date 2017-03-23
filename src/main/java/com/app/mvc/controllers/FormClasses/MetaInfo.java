package com.app.mvc.controllers.FormClasses;


public class MetaInfo {

    private Integer substanceid;
    private Integer stateid;
    private Integer propertyid;

    public MetaInfo() {}

    public Integer getSubstanceid() {
        return substanceid;
    }

    public void setSubstanceid(Integer substanceid) {
        this.substanceid = substanceid;
    }

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }

    public Integer getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(Integer propertyid) {
        this.propertyid = propertyid;
    }

    @Override
    public String toString() {
        return "DataMetaInfo [substanceid=" + substanceid + ", stateid=" + stateid + ", propertyid=" + propertyid + "]";
    }
}
