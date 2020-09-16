package com.pro.volley.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.pro.volley.MainActivity;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {

    private SharedPreferencesHelper sharedPreferencesHelper;

    Button bt_login;
    EditText et_email, et_pass;
    MaterialCheckBox cb_login_as_faculty;
    String e = "ganeshks439@gmail.com", p = "12345";
    boolean bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);


        sharedPreferencesHelper= new SharedPreferencesHelper(getApplicationContext(),"com.pro.volley");
        bt_login = findViewById(R.id.bt_login);
        cb_login_as_faculty = findViewById(R.id.cb_login_as_faculty);
        et_email = findViewById(R.id.et_email_login);
        et_pass = findViewById(R.id.et_pass_login);
        bt_login.setOnClickListener(this);

    }


    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        final Intent i;

        switch (v.getId()) {
            case R.id.bt_login:
                //  e = et_email.getText().toString();
                //p = et_pass.getText().toString();
                if (cb_login_as_faculty.isChecked()) {
                    Toast.makeText(getApplicationContext(),"not developed",Toast.LENGTH_SHORT).show();

                } else {
                    login_as_student();
                }

                return;
            case R.id.tv_reg_login:
                i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();


        }

    }


    public void login_as_student() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("fetching data ");
        progressDialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.url) + "auth/studentLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                requestQueue.getCache().clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("title").equals("success")) {
                        bool = true;
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                     //   sharedPreferences.edit().putString("email", e).apply();
                        sharedPreferencesHelper.setEmail(e);
                        sharedPreferencesHelper.setUsn(jsonObject.optString("usn"));
                      //  sharedPreferences.edit().putString("usn", jsonObject.getString("usn")).apply();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish(); // return;
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("title") + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        bool = false;
                        //return;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
                params.put("password", p);
                params.put("usertype","student");
                return params;
            }
        };

        requestQueue.add(stringRequest);
return;

    }
}