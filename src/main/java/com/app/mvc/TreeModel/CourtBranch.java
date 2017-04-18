package com.app.mvc.TreeModel;

public class CourtBranch {

    private int id;
    private String name;
    private int parentId;
    /*private List<String> elements;*/

    public CourtBranch() {}
    public CourtBranch(int id, String name, int parentId)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}