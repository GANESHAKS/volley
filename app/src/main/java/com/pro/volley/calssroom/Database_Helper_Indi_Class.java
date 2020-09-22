package com.pro.volley.calssroom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database_Helper_Indi_Class extends SQLiteOpenHelper {
    // Definition of table and column names of people table
    public static final String TABLE_PEOPLE = "People";
    public static final String COLUMN_PEOPLE_ID = "Usn";
    public static final String COLUMN_PEOPLE_NAME = "Name";
    public static final String COLUMN_PEOPLE_TYPE = "Type";
    public static final String COLUMN_PEOPLE_CODE = "Code";
    // Definition of table and column names of work table
    public static final String TABLE_WORK = "WORK";
    public static final String COLUMN_WORK_ID = "WORKID";
    public static final String COLUMN_WORK_DESC = "WORKDESC";
    public static final String COLUMN_WORK_CODE = "CODE";
    public static final String COLUMN_WORK_NO_OF_ATTACHMENT = "NO_OF_ATTACH";
    // Definition of table and column names of work table
    public static final String TABLE_STREAM = "STREAM";
    public static final String COLUMN_STREAM_ID = "STREAMID";
    public static final String COLUMN_STREAM_DESC = "STREAMDESC";
    public static final String COLUMN_STREM_CODE = "CODE";
    public static final String COLUMN_STREAM_NO_OF_ATTACHMENT = "NO_OF_ATTACH";
    // Create Statement for details Table
    public static final String TABLE_CLASSROOM_DETAILS = "DETAILS";
    public static final String COLUMN_DETAILS_CODE = "_ID";
    public static final String COLUMN_DETAILS_TITLE = "title";
    private static final String DATABASE_NAME = "CLASSROOMS.db";
    private static final int DATABASE_VERSION = 3;
    //
    private static final String CREATE_TABLE_DETAILS = "CREATE TABLE " + TABLE_CLASSROOM_DETAILS + "(" +
            COLUMN_DETAILS_CODE + " TEXT PRIMARY KEY," +
            COLUMN_DETAILS_TITLE + " TEXT" +
            ");";
    private static final String CREATE_TABLE_PEOPLE = "CREATE TABLE " + TABLE_PEOPLE + " (" +
            COLUMN_PEOPLE_ID + " TEXT, " +
            COLUMN_PEOPLE_NAME + " TEXT, " +
            COLUMN_PEOPLE_CODE + " TEXT ," +

            COLUMN_PEOPLE_TYPE + " TEXT, " + COLUMN_PEOPLE_CODE + "TEXT,PRIMARY KEY(" + COLUMN_PEOPLE_CODE + "," + COLUMN_PEOPLE_ID + "),"
            + "FOREIGN KEY(" + COLUMN_PEOPLE_CODE + ") REFERENCES " + TABLE_CLASSROOM_DETAILS + "(" + COLUMN_DETAILS_CODE + ") ON DELETE CASCADE "
            + ");";
    private static final String CREATE_TABLE_WORK = "CREATE TABLE " + TABLE_WORK + " (" +
            COLUMN_WORK_ID + " TEXT, " +
            COLUMN_WORK_DESC + " TEXT, " +
            COLUMN_WORK_CODE + " TEXT, " +
            COLUMN_WORK_NO_OF_ATTACHMENT + " TEXT, " + COLUMN_WORK_CODE + "TEXT,PRIMARY KEY(" + COLUMN_WORK_CODE + "," + COLUMN_WORK_ID + "),"
            + "FOREIGN KEY(" + COLUMN_WORK_CODE + ") REFERENCES " + TABLE_CLASSROOM_DETAILS + "(" + COLUMN_DETAILS_CODE + ") ON DELETE CASCADE "
            + ");";
    private static final String CREATE_TABLE_STREAM = "CREATE TABLE " + TABLE_STREAM + " (" +
            COLUMN_STREAM_ID + " TEXT, " +
            COLUMN_STREAM_DESC + " TEXT, " +
            COLUMN_STREM_CODE + " TEXT ," +
            COLUMN_STREAM_NO_OF_ATTACHMENT + " TEXT, " + COLUMN_STREM_CODE + "TEXT,PRIMARY KEY(" + COLUMN_STREM_CODE + "," + COLUMN_STREAM_ID + "),"
            + "FOREIGN KEY(" + COLUMN_STREM_CODE + ") REFERENCES " + TABLE_CLASSROOM_DETAILS + "(" + COLUMN_DETAILS_CODE + ") ON DELETE CASCADE "
            + ");";
    private static Database_Helper_Indi_Class mInstance = null;


    public Database_Helper_Indi_Class(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Database_Helper_Indi_Class getInstance(Context ctx) {
        if (mInstance == null) {
            Log.d("databasehelper:  getInstance", "databse instance id null");
            mInstance = new Database_Helper_Indi_Class(ctx.getApplicationContext());
            if (mInstance != null) {

                //  Log.d("databasehelper:  getInstance", "databse instance id  is not  null");
            }
        } else {
            Log.d("databasehelper:  getInstance", "databse instance id  is not  null");

        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate should always create your most up to date database
        // This method is called when the app is newly installed

        db.execSQL(CREATE_TABLE_DETAILS);
        db.execSQL(CREATE_TABLE_STREAM);
        db.execSQL(CREATE_TABLE_WORK);
        db.execSQL(CREATE_TABLE_PEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade is responsible for upgrading the database when you make
        // changes to the schema. For each version the specific changes you made
        // in that version have to be applied.
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            switch (version) {
                case 2:
                    db.execSQL("ALTER TABLE " + TABLE_PEOPLE + " ADD COLUMN " +
                            COLUMN_PEOPLE_TYPE + " TEXT;");
                    break;
                case 3:
                    db.execSQL(CREATE_TABLE_WORK);
                    break;
            }
        }
    }

}
