package com.pro.volley.calssroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DataBase_Manager_Class {

    private Database_Helper_Indi_Class dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DataBase_Manager_Class(Context c) {
        context = c;
    }

    public DataBase_Manager_Class open() throws SQLException {
        dbHelper = Database_Helper_Indi_Class.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        //   dbHelper.close();
    }

    public void insert_details(Classroom c) {

        ContentValues contentValue = new ContentValues();


        contentValue.put(Database_Helper_Indi_Class.COLUMN_DETAILS_CODE, c.getCode().trim());
        contentValue.put(Database_Helper_Indi_Class.COLUMN_DETAILS_TITLE, c.getTitle().trim());
        try {
            //   database.rawQuery("Insert into " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS +" values ( " +c.getCode() + " , " + c.getTitle() + ");", null);
            // database.rawQuery("schema  " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS, null);
            long c1 = database.insertOrThrow(Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS, null, contentValue);
          /*  Cursor resultSet = database.rawQuery("pragma table_info (" + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS + ")", null);
            resultSet.moveToFirst();
            String[] cname;
            cname = new String[2];
            int count = 0;
            if (resultSet != null) {
                while (resultSet.isAfterLast() == false) {
                    Log.d(" insert details", "try block  " + resultSet.getString(resultSet.getColumnIndex("name")));
                    cname[count] = resultSet.getString(resultSet.getColumnIndex("name"));

                    resultSet.moveToNext();
                }
            }*/

            close();
        } catch (Exception e) {
            Log.d("Error insert details", "Catch block  " + e.getMessage());
            close();
            e.printStackTrace();
        }
    }

    public ArrayList<Classroom> fetch_details() {
        ArrayList<Classroom> arrayList = new ArrayList<>();
        String query = "select * from " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS;
        String[] columns = new String[]{Database_Helper_Indi_Class.COLUMN_DETAILS_CODE, Database_Helper_Indi_Class.COLUMN_DETAILS_TITLE};

        try {

           /* Cursor resultSet = database.rawQuery("pragma table_info (" + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS + ")", null);
            resultSet.moveToFirst();
            String[] cname;
            cname = new String[2];
            int count = 0;
            if (resultSet != null) {
                while (resultSet.isAfterLast() == false) {
                    Log.d(" fetch details", "try block  " + resultSet.getString(resultSet.getColumnIndex("name")));
                    cname[count] = resultSet.getString(resultSet.getColumnIndex("name"));

                    resultSet.moveToNext();
                }
            }*/
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null) {
                Log.d("fetch data try block ", " no of rows = " + cursor.getCount());

                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    String c = cursor.getString(cursor.getColumnIndex(Database_Helper_Indi_Class.COLUMN_DETAILS_CODE));
                    String t = cursor.getString(cursor.getColumnIndex(Database_Helper_Indi_Class.COLUMN_DETAILS_TITLE));

                    arrayList.add(new Classroom(c, t));
                    Log.d("fetch data try block ", "  code = " + c);


                    cursor.moveToNext();
                }
            }
            cursor.close();
            close();
        } catch (Exception e) {
            close();
            Log.d("Error fetch details", "Catch block  " + e.getMessage());

            e.printStackTrace();
        }


        return arrayList;
    }

    public int getDetailsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS.trim();
        Cursor cursor = database.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean class_Contains_code(String s) {
        int count = 0;

        try {
            String countQuery = "SELECT  * FROM " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS.trim() + " where " + Database_Helper_Indi_Class.COLUMN_DETAILS_CODE + " = '" + s.trim() + "'";
            String[] args = new String[]{"dbms"};
            Cursor c = database.rawQuery(countQuery, null);

            count = c.getCount();
            Log.i(" DAta base contains code   :", s.trim() + "        no of row is " + count);

            c.close();
            if (count == 1) {
                return true;
            }
        } catch (Exception e) {
            Log.d("CLAss COntaains  code  :  ", "class contains code  :  " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public void delete_details(String code) {
        try {
            String countQuery = "SELECT  * FROM " + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS.trim() + " where " + Database_Helper_Indi_Class.COLUMN_DETAILS_CODE + " = '" + code.trim() + "'";
            Log.i("database delete classroom  :::    :", "raaju" + code.trim() + "hey buddu");


            ArrayList<Classroom> arrayList = fetch_details();
            if (arrayList != null) {
                for (Classroom c : arrayList) {
                    Log.e("in delete details :::", ":code :" + c.getCode() + ":title :" + c.getTitle());
                }
            }
            Log.e("in delete details :::", ":code to be deleted :" + Database_Helper_Indi_Class.COLUMN_DETAILS_CODE + "::" + code.trim() + ":");
            Log.e("in delete details :::", ":query is :" + Database_Helper_Indi_Class.COLUMN_DETAILS_CODE.trim() + " = '" + code.trim() + "'");
            Cursor resultSet = database.rawQuery("pragma table_info (" + Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS + ")", null);
            resultSet.moveToFirst();
            String[] cname;
            cname = new String[10];
            int count = 0;
            if (resultSet != null) {
                while (resultSet.isAfterLast() == false) {
                    Log.d(" insert details", "try block  " + resultSet.getString(resultSet.getColumnIndex("name")));
                    cname[count] = resultSet.getString(resultSet.getColumnIndex("name"));
                    Log.e("in delete details :::", ":row name is:" + cname[count] + ":::");

                    resultSet.moveToNext();
                }


                database.delete(Database_Helper_Indi_Class.TABLE_CLASSROOM_DETAILS, Database_Helper_Indi_Class.COLUMN_DETAILS_CODE.trim() + "=?", new String[]{code.trim()});
                close();
            }
        } catch (Exception e1) {
            close();
            e1.printStackTrace();
        }

    }
}
