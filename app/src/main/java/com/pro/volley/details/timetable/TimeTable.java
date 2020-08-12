package com.pro.volley.details.timetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.pro.volley.R;
import com.pro.volley.Settings;
import com.pro.volley.profile.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeTable extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MaterialToolbar toolbar;
    Intent i;
    SharedPreferences sharedPreferences;

    HashMap<String, String> dept;
    HashMap<String, String> sem;
    HashMap<String, String> section;

    List<String> deptlist = new ArrayList<String>();
    List<String> semlist = new ArrayList<String>();
    List<String> sectlist = new ArrayList<String>();

    Spinner sp_dept, sp_sem, sp_sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time_table);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Time Table");
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();
                        i = new Intent(TimeTable.this, Settings.class);
                        startActivity(i);
                        finish();
                        return false;
                    case R.id.profile:
                        Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_LONG).show();
                        i = new Intent(TimeTable.this, Profile.class);
                        startActivity(i);
                        finish();
                        return false;

                }
                return false;
            }
        });
        sharedPreferences = this.getSharedPreferences("com.pro.volley.details.timetable", MODE_PRIVATE);
        Toast.makeText(getApplicationContext(), "calling", Toast.LENGTH_LONG);
        Log.i("Response", "hi macha im calling u");


        sp_dept = (Spinner) findViewById(R.id.sp_dept_timetable);
        sp_sem = (Spinner) findViewById(R.id.sp_sem_timetable);
        sp_sec = (Spinner) findViewById(R.id.sp_sec_timetable);

        sp_dept.setOnItemSelectedListener(this);
        sp_sem.setOnItemSelectedListener(this);
        sp_sec.setOnItemSelectedListener(this);

        showTimeTable();
        set_spinner();
    }

    private void set_spinner() {
        deptlist.add(0,"choose dept");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,deptlist
                );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_dept.setAdapter(dataAdapter);
        semlist.add(0,"choose sem");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,semlist
        );

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_sem.setAdapter(dataAdapter1);
        sectlist.add(0,"choose section");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,sectlist
        );

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_sec.setAdapter(dataAdapter3);



    }

    private void showTimeTable() {
        Log.i("Response", "hi macha u called me");
        String table;
       /* if (sharedPreferences.contains("timetable")) {
            Log.i("Response","hi macha im calling u");

            table = sharedPreferences.getString("timetable", "notfound");

        } else {*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "test.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // sharedPreferences.edit().putString("timetable",response);
                Log.i("Response", "i got response from her  " + response);
                // Toast.makeText(getApplicationContext(),"successss",Toast.LENGTH_LONG);
                Initialize_data(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Response", "she rejected me" + error.getMessage());
            }
        });
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(TimeTable.this);
        queue.add(stringRequest);
    }

    private void Initialize_data(String string) {


        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                Log.i("message ", "data is correct");
                JSONArray jsonArray = new JSONArray(jsonObject.optString("timetable"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject eachsection = jsonArray.getJSONObject(i);
                    String sm, sc, dp;
                    sm = eachsection.optString("title").substring(1, 2);
                    sc = eachsection.optString("title").substring(2);
                    dp = eachsection.optString("title").substring(0, 1);
                    if (!deptlist.contains((dp))) {
                        deptlist.add(dp);
                    }
                    if (!sectlist.contains((sc))) {
                        sectlist.add(sc);
                    }
                    if (!semlist.contains((sm))) {
                        semlist.add(sm);
                    }
                    Log.i("title  ", dp + sm + sc);


                }//for loop end


            } else {
                Log.i(" message", "Error");
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("message ", "something error in data  " + e.getMessage());


        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}