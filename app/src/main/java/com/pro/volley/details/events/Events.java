package com.pro.volley.details.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.pro.volley.MainActivity;
import com.pro.volley.R;
import com.pro.volley.Settings;
import com.pro.volley.profile.Profile;

public class Events extends AppCompatActivity {
    MaterialToolbar toolbar;Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_events);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){

                    case R.id.settings:Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_LONG).show();
                       i=new Intent(Events.this, Settings.class);
                        startActivity(i);
                        finish();
                        return false;
                    case R.id.profile:
                        Toast.makeText(getApplicationContext(),"profile",Toast.LENGTH_LONG).show();
                        i=new Intent(Events.this, Profile.class);
                        startActivity(i);
                        finish();
                        return false;

                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}