package com.app.mvc.TreeModel;

public class OntList {

    private String name;
    private String child;

    public OntList() {}

    public OntList(String name, String parent ) {
        this.child = parent;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setParent(String parent) {
        this.child = parent;
    }
    public String getName() { return name; }
    public String getParent() {
        return child;
    }
}
