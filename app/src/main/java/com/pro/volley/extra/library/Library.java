package com.pro.volley.extra.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pro.volley.R;

public class Library extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_library);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}