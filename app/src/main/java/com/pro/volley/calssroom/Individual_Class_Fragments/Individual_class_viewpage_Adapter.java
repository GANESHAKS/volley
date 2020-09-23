package com.pro.volley.calssroom.Individual_Class_Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Individual_class_viewpage_Adapter extends FragmentPagerAdapter {
    String CLASS_CODE = "";

    public Individual_class_viewpage_Adapter(@NonNull FragmentManager fm, String CLASS_CODE) {
        super(fm);
        this.CLASS_CODE = CLASS_CODE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new Individual_class_stream(CLASS_CODE);
        } else if (position == 1) {
            fragment = new Individual_Class_ClassWork(CLASS_CODE);
        } else {
            fragment = new Individual_Class_People(CLASS_CODE);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "STREAM";
        } else if (position == 1) {
            title = "CLASS WORK";
        } else if (position == 2) {
            title = "PEOPLE";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
