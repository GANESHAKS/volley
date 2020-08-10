package com.pro.volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.volley.profile.Profile;

public class MainActivity extends AppCompatActivity  {

    MaterialToolbar toolbar; Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationview);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        toolbar=findViewById(R.id.toolbar);


        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){

                    case R.id.settings:Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_LONG).show();
                       i=new Intent(MainActivity.this, Settings.class);
                        startActivity(i);
                        finish();
                        return false;
                    case R.id.profile:
                        Toast.makeText(getApplicationContext(),"profile",Toast.LENGTH_LONG).show();
                       i=new Intent(MainActivity.this, Profile.class);
                        startActivity(i);
                        finish();
                        return false;

                }
                return false;
            }
        });



    } @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    /*

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch(item.getItemId()){

            case R.id.settings:Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_LONG).show();
                return false;
            case R.id.profile:
                Toast.makeText(getApplicationContext(),"profile",Toast.LENGTH_LONG).show();
                return false;

        }
        return false;
    }*/
}