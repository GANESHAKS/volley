package com.pro.volley.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.MainActivity;
import com.pro.volley.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(Profile.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}