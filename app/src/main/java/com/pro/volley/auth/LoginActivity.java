package com.pro.volley.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
    }


    public void onClick(View view) {


        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);

        startActivity(i);
        finish();


    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}