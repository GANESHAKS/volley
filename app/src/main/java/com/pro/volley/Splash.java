package com.pro.volley;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.auth.LoginActivity;
import com.pro.volley.auth.RegistrationActivity;

public class Splash extends AppCompatActivity {
public static int Splash_time=400;
SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("com.pro.firebase.login",MODE_PRIVATE);



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i;
                    if (sharedPreferences.contains("email")) {
                        i = new Intent(Splash.this, RegistrationActivity.class);//this action has to change
                        //start home activity
                        startActivity(i);
                        finish();
                    } else {
                        i = new Intent(Splash.this, LoginActivity.class);
                        startActivity(i);
                        finish();


                    }


                }
            },Splash_time);

    }
}