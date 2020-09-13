package com.pro.volley.extra;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pro.volley.R;
import com.pro.volley.extra.attendance.Attendance;
import com.pro.volley.extra.department.Department;
import com.pro.volley.extra.events.Events;
import com.pro.volley.extra.library.Library;
import com.pro.volley.extra.timetable.TimeTable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    //declarions
    CardView cv_dept, cv_event, cv_timetable, cv_attendence, cv_library;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_detail, container, false);

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