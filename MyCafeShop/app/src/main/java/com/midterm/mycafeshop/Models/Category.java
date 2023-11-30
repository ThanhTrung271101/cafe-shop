package com.example.finalproject.Models;

public class Category {
    String Id;
    String Name;
    public Category(){}
    public Category(String Id,String Name) {
        this.Id=Id;
        this.Name=Name;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
}
