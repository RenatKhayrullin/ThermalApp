package com.app.mvc.controllers.FormClasses;

/**
 * Created by Катерина on 22.04.2017.
 */
public class SearchRequest {

    private String entity;
    private String resource;

    SearchRequest() {}

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }

    @Override
    public String toString() {
        return "SearchRequest [entity=" + entity + ", resource=" + resource + "]";
    }
}
