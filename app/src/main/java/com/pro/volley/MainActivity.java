package com.pro.volley;

import android.os.Bundle;
import  com.pro.volley.auth.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(Intents.createIntentLogin());


    }
}