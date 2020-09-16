package com.pro.volley;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesHelper(Context context, String activity_name) {
        this.sharedPreferences = context.getSharedPreferences(activity_name, Context.MODE_PRIVATE);
        this.context = context;
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

}