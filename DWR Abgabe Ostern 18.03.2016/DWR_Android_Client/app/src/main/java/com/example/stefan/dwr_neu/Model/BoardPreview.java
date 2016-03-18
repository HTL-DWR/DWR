package com.example.stefan.dwr_neu.Model;

public class BoardPreview {

    int id;
    String name;



    public BoardPreview(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public BoardPreview() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return getName();
    }




}