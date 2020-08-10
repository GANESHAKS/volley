package com.pro.volley;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.profile.Profile;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(Settings.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}