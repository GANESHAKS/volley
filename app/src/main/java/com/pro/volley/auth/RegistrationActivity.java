package com.pro.volley.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pro.volley.MainActivity;
import com.pro.volley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    Intent i;
    Button bt_reg;
    EditText et_usn, et_email, et_p1, et_p2;
    String e = "ganeshka439@gmail.com", p1 = "12345", p2 = "12345", u = "17cs030", vkey;
    private SharedPreferences sharedPreferences;
    private RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);
        bt_reg = findViewById(R.id.bt_reg);
        sharedPreferences = this.getSharedPreferences("com.pro.volley", MODE_PRIVATE);
        et_usn = findViewById(R.id.et_usn_reg);
        et_email = findViewById(R.id.et_email_reg);
        et_p1 = findViewById(R.id.et_pass1_reg);
        et_p2 = findViewById(R.id.et_pass2_reg);


        bt_reg.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        i = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_reg:
                i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.bt_reg:


                // e=et_email.getText().toString().toLowerCase().trim();
                //u=et_usn.getText().toString().toLowerCase().trim();
                //p1=et_p1.getText().toString().trim();
                //p2=et_p2.getText().toString().trim();
                registration();

                break;


        }
    }

    public void registration() {
        Log.i("thiswillcheckonclick","clicked here");
/*
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("registering user ");
        progressDialog.show();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "registration.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    Log.i("response :",response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("0")) {

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        // sharedPreferences.edit().putString("email", e).apply();
                        Intent i=new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        return;


                    } else {

                        Toast.makeText(getApplicationContext(), jsonObject.getString("success") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        return;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), " " + "Server error", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", e);
                params.put("pass1", p1);
                params.put("pass2", p2);
                params.put("usn", u);
                return params;
            }
        };
         RequestQueue requestQueue1 = Volley.newRequestQueue(this);

        requestQueue1.add(stringRequest2);
*/
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "registration.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.i("check",response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("0")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                            } else
                                {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "In catch " + e.toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", e);
                params.put("pass1", p1);
                params.put("pass2", p2);
                params.put("usn", u);
                return params;
            }
        };
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest2.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
        queue.add(stringRequest2);

    }
}