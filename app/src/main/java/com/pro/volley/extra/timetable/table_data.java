package com.pro.volley.extra.timetable;

public class table_data {
    String from;

    public table_data(String from, String to, String  subject, String faculty) {
        this.from = from;
        this.to = to;
        this.faculty = faculty;
        this.subject = subject;
    }

    String to; String faculty;
    String subject;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
