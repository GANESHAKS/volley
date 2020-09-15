package com.pro.volley.calssroom;

public class Classroom {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String title;

    public Classroom(String code,String title ) {
        this.title = title;
        this.code = code;
    }

    String code;
}
