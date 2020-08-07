package com.pro.volley.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.R;
import com.pro.volley.home.Home;

public class LoginActivity extends Activity {

    private SharedPreferences sharedPreferences;
    Button bt_login;
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        sharedPreferences=getSharedPreferences("com.pro.volley.mainactivity",MODE_PRIVATE);
        bt_login=findViewById(R.id.bt_login);
        et_email=findViewById(R.id.et_email_auth);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().putString("email",et_email.getText().toString()).apply();
                Intent i=new Intent(LoginActivity.this, Home.class);
                startActivity(i);
                finish();
            }
        });

    }


    public void et_click(View view) {

        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(i);
        finish();

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void bt_login_click(View view) {



    }
}