package com.pro.volley.extra;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.pro.volley.R;
import com.pro.volley.Settings;
import com.pro.volley.extra.attendance.Attendance;
import com.pro.volley.extra.department.Department;
import com.pro.volley.extra.events.Events;
import com.pro.volley.extra.library.Library;
import com.pro.volley.extra.timetable.TimeTable;
import com.pro.volley.profile.Profile;

public class DetailFragment extends Fragment implements View.OnClickListener {
    //declarions
    CardView cv_dept, cv_event, cv_timetable, cv_attendence, cv_library;
    MaterialToolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_extra, container, false);

        cv_dept = view.findViewById(R.id.tv_dept);
        cv_event = view.findViewById(R.id.tv_event);
        cv_timetable = view.findViewById(R.id.tv_timetable);
        cv_attendence = view.findViewById(R.id.tv_attendance);
        cv_library = view.findViewById(R.id.tv_library);
        cv_dept.setOnClickListener(this);
        cv_library.setOnClickListener(this);
        cv_event.setOnClickListener(this);
        cv_timetable.setOnClickListener(this);
        cv_attendence.setOnClickListener(this);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_top);
        toolbar.setTitle("Extra");
        toolbar.setTitleTextColor(Color.parseColor("#ff0000"));
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings:
                        Toast.makeText(getContext(), "settings", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(), Settings.class);
                        startActivity(i);
                        //  finish();
                        return false;
                    case R.id.profile:
                        // Toast.makeText(getApplicationContext(), "ProfileSharedPreference", Toast.LENGTH_LONG).show();
                        i = new Intent(getContext(), Profile.class);
                        startActivity(i);
                        //    finish();
                        return false;

                }
                return false;
            }
        });
        return view;


    }


    @Override
    public void onClick(View v) {
        final Intent i;
        switch (v.getId()) {
            case R.id.tv_attendance:
                Toast.makeText(getContext(), "Attenence", Toast.LENGTH_SHORT).show();
                 i = new Intent(getContext(), Attendance.class);
                startActivity(i);

                return;
            case R.id.tv_dept:
                Toast.makeText(getContext(), "dept", Toast.LENGTH_SHORT).show();
                 i= new Intent(getContext(), Department.class);
                startActivity(i);
                return;
            case R.id.tv_event:
                Toast.makeText(getContext(), "event", Toast.LENGTH_SHORT).show();
                i = new Intent(getContext(), Events.class);
                startActivity(i);
                return;
            case R.id.tv_timetable:
                Toast.makeText(getContext(), "timetable", Toast.LENGTH_SHORT).show();
                i = new Intent(getContext(), TimeTable.class);
                startActivity(i);
                return;
            case R.id.tv_library:
                Toast.makeText(getContext(), "Library", Toast.LENGTH_SHORT).show();
                i = new Intent(getContext(), Library.class);
                startActivity(i);
                return;

        }
    }

}