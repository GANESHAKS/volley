package com.pro.volley.calssroom.Individual_Class_Fragments;

public class Peoples {
    String item_type = "";
    String name = "";
    String id = "";
    String pic = "";

    public Peoples(String id, String name, String pic, String item_type) {
        this.item_type = item_type;
        this.name = name;
        this.id = id;
        this.pic = pic;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}
