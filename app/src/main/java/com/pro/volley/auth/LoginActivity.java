package com.pro.volley.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pro.volley.R;

public class LoginActivity extends AppCompatActivity {
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
    }


    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "affasfasfa", Toast.LENGTH_SHORT).show();



        if(i==null){
            i=new Intent(LoginActivity.this,RegistrationActivity.class);
            startActivity(i);finish();
        }else {
            startActivity(i);finish();
        }

    }
}