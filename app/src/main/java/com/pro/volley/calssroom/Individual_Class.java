package com.pro.volley.calssroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.pro.volley.R;
import com.pro.volley.profile.Profile;

public class Individual_Class extends AppCompatActivity {
    MaterialToolbar toolbar;
    TabLayout tabLayout;
    NavController navController;
    NavOptions navOptions;
    boolean flag = false;
    String CLASS_CODE = "null";

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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_classroom);
//        navOptions = new NavOptions.Builder()
//                .setLaunchSingleTop(true)
//                .setPopUpTo(navController.getGraph().getStartDestination(), false)
//                .build();
        navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.left_to_right)
                .setExitAnim(R.anim.left_to_right)
                //  .setPopEnterAnim(R.anim.right_to_left)
                //.setPopExitAnim(R.anim.right_to_left)
                .setPopUpTo(navController.getGraph().getStartDestination(), false)
                .build();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ClassRoom Name  :"+CLASS_CODE);
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


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        flag = false;
                        navController.navigate(R.id.individual_class_stream, null, navOptions);
                        break;
                    case 1:
                        flag = true;
                        navController.navigate(R.id.individual_Class_ClassWork, null, navOptions);
                        break;
                    case 2:
                        flag = true;
                        navController.navigate(R.id.individual_Class_People, null, navOptions);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (flag) {
            tabLayout.getTabAt(0).select();

        } else super.onBackPressed();
    }
}