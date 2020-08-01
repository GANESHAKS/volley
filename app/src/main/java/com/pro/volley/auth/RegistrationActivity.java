package com.pro.volley.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.R;

public class RegistrationActivity extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);
    }

    public void onClick(View view) {

        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}