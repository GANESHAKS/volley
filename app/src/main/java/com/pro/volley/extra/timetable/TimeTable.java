package com.pro.volley.extra.timetable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TimeTable extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ItemClickListener {
    MaterialToolbar toolbar;
    RecyclerView recyclerView1, recyclerView2;

    Intent i;
    SharedPreferences sharedPreferences;
    HashMap<String, table_monday_sunday> timetables = new HashMap<>();//key will be table name data willbe a object
HashMap<String,String> dept_map=new HashMap<>();

    List<String> dept_value_lst = new ArrayList<String>();
    List<String> dept_key_lst = new ArrayList<String>();
    List<String> semlist = new ArrayList<String>();
    List<String> sectlist = new ArrayList<String>();
    List<String> dayslist = new ArrayList<String>();
    Adapter1_day adapter;
    Adapter2_table adapter2;

    Spinner sp_dept, sp_sem, sp_sec;
    String selected_dept;
    String selected_sem;
    String selected_sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time_table);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Time Table");
toolbar.inflateMenu(R.menu.menu_top_timetable);
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
                        Toast.makeText(getApplicationContext(), "ProfileSharedPreference", Toast.LENGTH_LONG).show();
                        i = new Intent(TimeTable.this, Profile.class);
                        startActivity(i);
                        finish();
                        return false;
                    case  R.id.menu_item_timetable_download: request_server();return  false;

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


        recyclerView1 = (RecyclerView) findViewById(R.id.rv_day_timetable);
        recyclerView1.setHasFixedSize(false);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = (RecyclerView) findViewById(R.id.rv_timetable);
        recyclerView2.setHasFixedSize(false);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));



        request_for_data();



        // request_server();


    }


    private void request_server() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Feching  data ");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "test.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                sharedPreferences.edit().putString("timetable",response).apply();
                Log.i("request server", "i got response from her  " );
                // Toast.makeText(getApplicationContext(),"successss",Toast.LENGTH_LONG);
                Initialize_data(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),"check your internet",Toast.LENGTH_SHORT).show();
                Log.i("request server", "error  " + error.getMessage());
            }
        });
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(TimeTable.this);
        queue.add(stringRequest);


    }

    private void set_spinner() {

        try {
            dept_key_lst.add(0, "choose dept");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, dept_key_lst
            );

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_dept.setAdapter(dataAdapter);
            semlist.add(0, "choose sem");
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, semlist
            );

            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_sem.setAdapter(dataAdapter1);
            sectlist.add(0, "choose section");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, sectlist
            );

            // Drop down layout style - list view with radio button
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_sec.setAdapter(dataAdapter3);


        } catch (Exception e) {
            e.printStackTrace();
            Log.i("set_spinner  ", "error   "+e.getMessage());
        }

    }

    private void request_for_data() {


        Log.i("Response", "hi macha u called me");
        String table;
        if (sharedPreferences.contains("timetable")) {
            Log.i("Response","hi macha im calling u");

            table = sharedPreferences.getString("timetable", "notfound");
            if(!table.equalsIgnoreCase("notfound")){
                Initialize_data(table);
            }

        } else {
          request_server();

        }
        set_spinner();//spinner

    }

    private void set_recycler_data_1() {
        // dayslist.clear();
        dayslist.add("Mon");
        dayslist.add("Tue");
        dayslist.add("Wed");
        dayslist.add("Thu");
        dayslist.add("Fri");
        dayslist.add("Sat");
        // String[] s=new String[]{"Mon","Tue","Wed","Thu","Fri","Sat"};
        //  if (!selected_sec.equals(sectlist.get(0)) &&
        //     !selected_sem.equals(semlist.get(0)) &&
        //   !selected_dept.equalsIgnoreCase(deptlist.get(0))){
        //  table_monday_sunday tms = timetables.get(selected_dept+selected_sem+selected_sec);

        // ArrayList<ArrayList<table_data>> tb = tms.arrayList;
        //   for (int i=0;i<=tms.no_of_days;i++){
        //     dayslist.add(s[i]);

        // }}
        adapter = new Adapter1_day(dayslist, getApplicationContext(), this);
        recyclerView1.setAdapter(adapter);


    }

    //to insert time table of all dept sem and sec with key value
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
                    if (!dept_value_lst.contains((dp))) {
                        dept_value_lst.add(dp);
                        dept_key_lst.add(eachsection.optString("dept"));

                        dept_map.put(eachsection.optString("dept"),dp);

                    }
                    if (!sectlist.contains((sc))) {
                        sectlist.add(sc);
                    }
                    if (!semlist.contains((sm))) {
                        semlist.add(sm);
                    }

                    timetables.put(eachsection.optString("title"), new table_monday_sunday(eachsection.optString("table")));//key value pair
                    Log.i("title  ", dp + sm + sc);


                }//for loop end

                set_recycler_data_1();
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


    @Override
    public void onItemClick(int position) {


        show_data(position);

    }

    //to show rv 2
    private void show_data(int position) {

        selected_dept = sp_dept.getSelectedItem().toString();
        selected_sem = sp_sem.getSelectedItem().toString();
        selected_sec = sp_sec.getSelectedItem().toString();
        if (!selected_sec.equals(sectlist.get(0)) &&
                !selected_sem.equals(semlist.get(0)) &&
                !selected_dept.equalsIgnoreCase(dept_value_lst.get(0))) {

            String selected_dept_val= null;
            try {
                selected_dept_val = dept_map.get(selected_dept);
                table_monday_sunday tms = timetables.get(selected_dept_val + selected_sem + selected_sec);
                Log.i("arralist of dept lst",dept_value_lst.toString()+selected_dept_val);

                ArrayList<ArrayList<table_data>> tb = tms.arrayList;
                if (position <= tms.no_of_days) {

                    adapter2 = new Adapter2_table(tb.get(position), getApplicationContext());
                    recyclerView2.setAdapter(adapter2);
                    final LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(this, R.anim.layou_anim_right_to_left);

                    recyclerView2.setLayoutAnimation(controller);
                  //  Adapter2_table.notifyDataSetChanged();
                    recyclerView2.scheduleLayoutAnimation();
                } else {
                    Toast.makeText(getApplicationContext(), " no data found" + tms.no_of_days, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"please select department",Toast.LENGTH_SHORT).show();
            }



        }  else {
            Toast.makeText(getApplicationContext(), "Please select combinaton", Toast.LENGTH_SHORT).show();
        }


    }
   
}