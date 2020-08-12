package com.pro.volley.details.timetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.pro.volley.R;
import com.pro.volley.Settings;
import com.pro.volley.profile.Profile;

import org.json.JSONObject;

public class TimeTable extends AppCompatActivity {
    MaterialToolbar toolbar;
    Intent i;
    SharedPreferences sharedPreferences;

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

        showTimeTable();


    }

    private void showTimeTable() {
        String table;
        if (sharedPreferences.contains("timetable")) {
            table = sharedPreferences.getString("timetable", "notfound");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "test.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  sharedPreferences.edit().putString("timetable",response);
                    Log.i("Response",response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

            };
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}