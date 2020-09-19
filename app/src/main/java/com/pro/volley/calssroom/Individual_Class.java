package com.pro.volley.calssroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.pro.volley.R;
import com.pro.volley.calssroom.Individual_Class_Fragments.Individual_class_viewpage_Adapter;
import com.pro.volley.profile.Profile;

public class Individual_Class extends AppCompatActivity {
    MaterialToolbar toolbar;
    TabLayout tabLayout;
    NavController navController;
    NavOptions navOptions;
    boolean flag = false;
    String CLASS_CODE = "null";
    ViewPager viewPager;
    Individual_class_viewpage_Adapter viewPagerAdapter;

    @Override
    protected void onPause() {
        Log.i("Indivial class", "Paused   ");
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_individual__class);
        if (getIntent().hasExtra("CLASS_CODE")) {

            CLASS_CODE = getIntent().getStringExtra("CLASS_CODE");
        } else {
            onBackPressed();
        }


        tabLayout = findViewById(R.id.tab_classroom);
        //
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ClassRoom Name  :" + CLASS_CODE);
        toolbar.inflateMenu(R.menu.menu_individual_classroom);
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_LONG).show();

//
                        return false;
                    case R.id.profile:
                        // Toast.makeText(getApplicationContext(), "ProfileSharedPreference", Toast.LENGTH_LONG).show();

                        Intent i=new Intent(getApplicationContext(), Profile.class);
                        startActivity(i);
                        return false;
                    case R.id.goto_home:
                        finish();
                }
                return false;
            }
        });


        viewPager = findViewById(R.id.viewPager_indi_class);
        viewPagerAdapter = new Individual_class_viewpage_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tab_classroom);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        if (flag) {
            tabLayout.getTabAt(0).select();

        } else super.onBackPressed();
    }
}