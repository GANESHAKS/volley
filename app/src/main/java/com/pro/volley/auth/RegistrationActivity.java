package com.pro.volley.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Toast.makeText(getApplicationContext(), "affasfasfa", Toast.LENGTH_SHORT).show();

        if(i==null){
            i=new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(i);finish();
        }else {
            startActivity(i);
            finish();
        }

    }
}