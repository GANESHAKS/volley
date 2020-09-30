package com.pro.volley.calssroom.Individual_Class_Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

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
import com.arasthel.asyncjob.AsyncJob;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peoples = new ArrayList<>();
        dbManager = new DataBase_Manager_Class(getContext());
        dbManager.open();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("ONACTIVITY CReated :", "INDIVIDUAL CLASs");
        //TODO    fetch data from databse data

        ArrayList<Peoples> students = dbManager.fetch_people(CLASS_CODE);
        updateUI(students);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Runnable r = new Runnable() {
                //   @Override
                // public void run() {
                Log.i("SWIPED  : ", "Swiped");
                down_load_data();

                //}
                //};
                //Thread t = new Thread(r);
                //t.start();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_individual__class__people, container, false);
        swipeRefreshLayout = view.findViewById(R.id.sr_classroom_people);
        recyclerView = view.findViewById(R.id.rv_indi_class_people);
        ArrayList<Peoples> p = new ArrayList<>();
        p.add(new Peoples("123", "updating", "", "header"));
        updateUI(p);
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
            ArrayList<Peoples> peoplesArrayList = new ArrayList<>();


            for (Peoples p : arrayList) {
                if (p.getItem_type().equalsIgnoreCase("teacher")) {
                    t.add(p);
                } else {
                    s.add(p);
                }
            }

            if (t.size() != 0) {
                peoplesArrayList.add(new Peoples("123", "Teachers", "null", "header"));
                peoplesArrayList.addAll(t);
            }

            if (s.size() != 0) {
                peoplesArrayList.add(new Peoples("123", "Students", "null", "header"));
                peoplesArrayList.addAll(s);
            }
            if (peoplesArrayList.size() == 0) {
                peoplesArrayList.add(new Peoples("123", "refresh", "", "header"));
            }

            IndividualClass_people_adapter adapter = new IndividualClass_people_adapter(peoplesArrayList, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layou_anim_right_to_left);
            recyclerView.setLayoutAnimation(controller);
            try {


            } catch (Exception e) {
                Log.e(" update ui  :: ", e.getMessage());
            }

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
                            AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
                                @Override
                                public void doOnBackground() {
                                    final ArrayList<Peoples> p = new ArrayList<>();
                                    saveInDatabase(teacher);
                                    saveInDatabase(students);
                                    p.addAll(dbManager.fetch_people(CLASS_CODE));
                                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                        @Override
                                        public void doInUIThread() {
                                            updateUI(p);
                                        }
                                    });
                                }
                            });
                            // ArrayList<Peoples> te = teacher;
                            // ArrayList<Peoples> s = students;
                           /* new AsyncJob.AsyncJobBuilder<Boolean>().doInBackground(
                                    new AsyncJob.AsyncAction<Boolean>() {
                                        @Override
                                        public Boolean doAsync() {


                                            return true;
                                        }
                                    }).doWhenFinished(new AsyncJob.AsyncResultAction() {
                                @Override
                                public void onResult(Object o) {
                                  //  Toast.makeText(getContext(),"updated",Toast.LENGTH_SHORT).show();
                                }
                            }).create().start();
*/
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