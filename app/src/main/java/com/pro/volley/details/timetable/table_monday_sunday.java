package com.pro.volley.details.timetable;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class table_monday_sunday {
    public ArrayList<ArrayList<table_data>> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ArrayList<table_data>> arrayList) {
        this.arrayList = arrayList;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    ArrayList<ArrayList<table_data>> arrayList = new ArrayList<ArrayList<table_data>>();
    String string;

    public int getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    int no_of_days;

    public table_monday_sunday(String string) {

        this.string = string;
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray arr = jsonArray.getJSONArray(i);
                ArrayList<table_data> tbarray = new ArrayList<>();
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject jsonObject = arr.getJSONObject(j);
                    table_data tb = new table_data(jsonObject.optString("From"), jsonObject.optString("To"), jsonObject.optString("subject"), jsonObject.optString("teacher"));
                    tbarray.add(tb);
                }
                arrayList.add(tbarray);


                setNo_of_days(i);


            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("table_monday_sunday","some error in processing data  "+e.getMessage());

        }


    }

    public ArrayList<ArrayList<table_data>> extract_data() {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return arrayList;
    }
}
