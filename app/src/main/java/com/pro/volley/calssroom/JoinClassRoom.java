package com.pro.volley.calssroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.pro.volley.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinClassRoom extends Activity {
    MaterialButton bt_join_classroom;
    private SharedPreferences sharedPreferences;
    EditText et_class_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join_class_room);
        sharedPreferences = this.getSharedPreferences("com.pro.volley", MODE_PRIVATE);
        et_class_code = findViewById(R.id.et_classroom_join_code);
        bt_join_classroom = findViewById(R.id.bt_join_classroom);
        bt_join_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!et_class_code.getText().toString().equals(""))
                    join_class_room(et_class_code.getText().toString());

            }
        });
    }

    public void join_class_room(final String classcode) {
        Log.i("Entered here  dsfsdf :","entrweddasda sdasdahsfadsjfdsjfhjsdfhsahdfhasdhfhasdhfhsadf");


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("fetching data ");
        progressDialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.urlroot) + "/mycollege/Classroom/student/joinClassStudent.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        requestQueue.getCache().clear();
                        Log.i("Response :",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("title").equals("success")) {
                                Toast.makeText(getApplicationContext(),  jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                // return;
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("usn", sharedPreferences.getString("usn", "001"));
                params.put("classcode", classcode);
                return params;
            }
        };

        requestQueue.add(stringRequest);
        return;

    }
}