package com.pro.volley;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private Context context;
   private ClassroomSharedPreference classroomSharedPreference;
    public  ClassroomSharedPreference getclassroomSharedPreference(){
        return  new ClassroomSharedPreference(context);
    }

    public SharedPreferencesHelper(Context context, String activity_name) {
        this.sharedPreferences = context.getSharedPreferences(activity_name, Context.MODE_PRIVATE);
        this.context = context;
    }

    public SharedPreferencesHelper() {

    }

    //for login
    String email = "null";
    String usn = "null";

    //

    public String getEmail() {
        return sharedPreferences.getString("email", "null");
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString("email", email).apply();

    }

    public String getUsn() {
        return sharedPreferences.getString("usn", "null");
    }

    public void setUsn(String usn) {
        sharedPreferences.edit().putString("usn", usn).commit();
    }

    //for classroom
    public class ClassroomSharedPreference {
        SharedPreferences sharedPreferences_classroom;
        Context classroom_context;
        String saved_classrooms_data_array = "null";

        public ClassroomSharedPreference(Context context) {
            classroom_context = context;
            sharedPreferences_classroom=classroom_context.getSharedPreferences("com.pro.volley.classroom",Context.MODE_PRIVATE);

        }

        public void removeSaved_classrooms_data_array() {
            sharedPreferences.edit().remove("saved_classrooms_data_array").apply();
        }

        public void setSaved_classrooms_data_array(String saved_classrooms_data_array) {
            sharedPreferences.edit().putString("saved_classrooms_data_array", saved_classrooms_data_array).apply();
        }

        public String getSaved_classrooms_data_array() {
            return sharedPreferences.getString("saved_classrooms_data_array", "null");
        }

    }


    public class profile {
        SharedPreferences sharedPreferences;
        Context profile_context;
        String profile_usn = "null";
        String profile_name = "null", profile_email = "null", profile_phno = "null", profile_dept = "null", profile_sem = "null", profile_sec = "null", profile_picURL = "null", getProfile_imagedata = "null";

        public profile(Context context) {
            profile_context = context;

        }

        //For Profile


        public String getProfile_usn() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_usn(String profile_usn) {
            sharedPreferences.edit().putString("usn", profile_usn).apply();
        }


        public String getProfile_name() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_name(String profile_name) {
            this.profile_name = profile_name;
        }

        public String getProfile_email() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_email(String profile_email) {
            this.profile_email = profile_email;
        }

        public String getProfile_phno() {

            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_phno(String profile_phno) {
            this.profile_phno = profile_phno;
        }

        public String getProfile_dept() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_dept(String profile_dept) {
            this.profile_dept = profile_dept;
        }

        public String getProfile_sem() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_sem(String profile_sem) {
            this.profile_sem = profile_sem;
        }

        public String getProfile_sec() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_sec(String profile_sec) {
            this.profile_sec = profile_sec;
        }

        public String getProfile_picURL() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setProfile_picURL(String profile_picURL) {
            this.profile_picURL = profile_picURL;
        }

        public String getGetProfile_imagedata() {
            return sharedPreferences.getString("usn", "null");
        }

        public void setGetProfile_imagedata(String getProfile_imagedata) {
            this.getProfile_imagedata = getProfile_imagedata;
        }


    }
}