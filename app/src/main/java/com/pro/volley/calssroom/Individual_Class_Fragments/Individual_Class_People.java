package com.pro.volley.calssroom.Individual_Class_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.volley.R;

import java.util.ArrayList;

public class Individual_Class_People extends Fragment {
    ArrayList<Peoples> peoples;
    RecyclerView recyclerView;

    public Individual_Class_People() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        peoples = new ArrayList<>();
        Peoples p = new Peoples("Teachers", "header");
        Peoples p0 = new Peoples("ganesh", "people");
        Peoples p1 = new Peoples("Students", "header");
        Peoples p2 = new Peoples("ganesh", "people");
        peoples = new ArrayList<>();
        peoples.add(p0);
        peoples.add(p1);
        peoples.add(p2);
        IndividualClass_people_adapter adapter = new IndividualClass_people_adapter(peoples, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_individual__class__people, container, false);

        recyclerView = view.findViewById(R.id.rv_indi_class_people);


        return view;
    }
}