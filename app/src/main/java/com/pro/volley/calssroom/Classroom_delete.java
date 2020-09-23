package com.pro.volley.calssroom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Classroom_delete extends AppCompatActivity {
    String CLASS_CODE = "";
    AlertDialog.Builder builder;
    SharedPreferencesHelper sharedPreferencesHelper;
    SharedPreferencesHelper.ClassroomSharedPreference sharedPreference_classroom;
    Button bt_delete;
    TextView tv_msg, tv_msg2;
    DataBase_Manager_Class dataBase_manager_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_classroom_delete);
        dataBase_manager_class = new DataBase_Manager_Class(getApplicationContext());
        dataBase_manager_class.open();
        builder = new AlertDialog.Builder(getApplicationContext());
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "com.pro.volley");
        sharedPreference_classroom = sharedPreferencesHelper.getclassroomSharedPreference();
        bt_delete = findViewById(R.id.bt_classroom_delete);
        tv_msg = findViewById(R.id.tv_classroom_delete_code);
        tv_msg2 = findViewById(R.id.tv_classroom_delete_title);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unenroll_from_class();


            }
        });

        if (getIntent().hasExtra("CLASS_CODE") && getIntent().hasExtra("CLASS_TITLE")) {
            CLASS_CODE = getIntent().getStringExtra("CLASS_CODE");
            tv_msg.setText("Unenroll From  " + getIntent().getStringExtra("CLASS_TITLE") + " ?");
            tv_msg2.setText("Unenroll From  " + getIntent().getStringExtra("CLASS_TITLE") + " ?");


        } else {
            onBackPressed();
            Log.i("Elese par t fdsfds  :", "sdasdasdasdasda");
        }


    }

    private void unenroll_from_class() {
        if (getIntent().hasExtra("CLASS_CODE") && getIntent().hasExtra("CLASS_TITLE")) {
            CLASS_CODE = getIntent().getStringExtra("CLASS_CODE");
            tv_msg.setText("Unenroll From  " + getIntent().getStringExtra("CLASS_TITLE") + " ?");
            tv_msg2.setText("Unenroll From  " + getIntent().getStringExtra("CLASS_TITLE") + " ?");

            Log.i("asdasdasdasd", "dasdashasjbdasbdbashdasdsdjasbdasjd    " + CLASS_CODE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.urlclassstudent) + "/unEnRollStudent.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(" unenroll", response);
                    try {
                        JSONObject j = new JSONObject(response);
                        if (j.optString("title").equalsIgnoreCase("success")) {
                            sharedPreference_classroom.removeSaved_classrooms_data_array();
                            dataBase_manager_class.delete_details(CLASS_CODE);

                            onBackPressed();


                        } else if (j.optString("title").equalsIgnoreCase("unsuccess")) {
                            Toast.makeText(getApplicationContext(), "retry", Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                    Log.i("Error while  unenroll", "Un en roll" + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("usn", sharedPreferences.getString("usn", "001"));
                    params.put("usn", sharedPreferencesHelper.getUsn());
                    params.put("classcode", CLASS_CODE);
                    return params;
                }

            };

            RetryPolicy policy = new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);


        } else {
            onBackPressed();
            Log.i("Elese classroom delete   :", "sdasdasdasdasda");
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}