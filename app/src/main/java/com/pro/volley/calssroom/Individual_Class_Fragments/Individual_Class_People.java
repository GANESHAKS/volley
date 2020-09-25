package com.pro.volley.calssroom.Individual_Class_Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.pro.volley.R;
import com.pro.volley.calssroom.DataBase_Manager_Class;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Individual_Class_People extends Fragment {
    ArrayList<Peoples> peoples;
    RecyclerView recyclerView;
    DataBase_Manager_Class dbManager;
    SwipeRefreshLayout swipeRefreshLayout;
    String CLASS_CODE = "";
    Handler handler;
    public Individual_Class_People(String CLASS_CODE) {
        // Required empty public constructor
        this.CLASS_CODE = CLASS_CODE;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* Peoples p0 = new Peoples("123456", "nagnna", "null", "people");
        Peoples p1 = new Peoples("213123", "Students", "null", "header");
        Peoples p2 = new Peoples("23123", "raaju bhai", "null", "people");
        peoples = new ArrayList<>();
        peoples.add(p);
        peoples.add(p0);
        peoples.add(p1);
        peoples.add(p2);
        Peoples p = new Peoples("12345", "Teachers", "null", "header");
*/
        //TODO    fetch data from databse data
        ArrayList<Peoples> students = dbManager.fetch_people(CLASS_CODE);

        updateUI(students);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                down_load_data();
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peoples = new ArrayList<>();
        dbManager = new DataBase_Manager_Class(getContext());
        dbManager.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_individual__class__people, container, false);
        swipeRefreshLayout = view.findViewById(R.id.sr_classroom_people);
        recyclerView = view.findViewById(R.id.rv_indi_class_people);


        return view;
    }

    void down_load_data() {
        background_download asyncTask = new background_download();
        asyncTask.execute();


    }

    private void updateUI(ArrayList<Peoples> arrayList) {
        if (arrayList != null) {
            ArrayList<Peoples> t = new ArrayList<>();
            ArrayList<Peoples> s = new ArrayList<>();
            for (Peoples p : arrayList) {
                if (p.getItem_type().equalsIgnoreCase("teacher")) {
                    t.add(p);
                } else {
                    s.add(p);
                }
            }
            ArrayList<Peoples> peoplesArrayList = new ArrayList<>();
            peoplesArrayList.add(new Peoples("123", "Teachers", "null", "header"));
            peoplesArrayList.addAll(t);
            peoplesArrayList.add(new Peoples("123", "Students", "null", "header"));
            peoplesArrayList.addAll(s);
            IndividualClass_people_adapter adapter = new IndividualClass_people_adapter(peoplesArrayList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }


    }

    private void saveInDatabase(ArrayList<Peoples> peoples) {
        if (peoples != null) {
            for (Peoples p : peoples) {

                dbManager.insert_people(p, CLASS_CODE);
            }
        }


    }

    public class background_download extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.i("onPreExecute", " executed   :::" + CLASS_CODE);
            updateUI(dbManager.fetch_people(CLASS_CODE));
            swipeRefreshLayout.setRefreshing(false);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", " executed");

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("Enetrs doin back", "doin bacl");
            final ArrayList<Peoples> teacher = new ArrayList<>();
            final ArrayList<Peoples> students = new ArrayList<>();
            /*handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {

                    ArrayList<Peoples> peoples1 = new ArrayList<>();
                    peoples1.add(new Peoples("123", "Teachers", "null", "header"));
                    peoples1.addAll(teacher);
                    peoples1.add(new Peoples("123", "Students", "null", "header"));
                    peoples1.addAll(students);

                    updateUI(peoples1);
                }
            };
*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.urlclassstudent) + "/returnPeopleInClass.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("INDIVIDUAL CLASS_PEOPLE :::: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString("title").equalsIgnoreCase("success")) {
                            String msg = jsonObject.optString("message", "null");
                            //todo save data in  sqlite database

                            JSONArray jsonArray = jsonObject.getJSONArray("message");
                            ArrayList<Peoples> peoples = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject j = jsonArray.getJSONObject(i);

                                Peoples p = new Peoples(j.optString("id"), j.optString("name"), j.optString("pic"), j.optString("type"));
                                if (j.optString("type").equalsIgnoreCase("teacher")) {
                                    teacher.add(p);
                                } else students.add(p);

                            }
                            ArrayList<Peoples> te = teacher;
                            ArrayList<Peoples> s = students;
                            saveInDatabase(te);
                            saveInDatabase(s);

                            //                                  handler.sendEmptyMessage(0);
                        }


                    } catch (Exception e) {
                        Log.e("error in Indi class people : ", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("erroer in background req", " error listener");
                    CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout_indiclass);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "unable to connect to server", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("usn", sharedPreferences.getString("usn", "17cs030"));
                    params.put("class_code", CLASS_CODE);
                    return params;
                }

            };
            int socketTimeOut = 50000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(stringRequest);

            return null;

        }
    }
}