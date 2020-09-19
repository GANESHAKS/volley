package com.pro.volley.calssroom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro.volley.R;
import com.pro.volley.Settings;
import com.pro.volley.SharedPreferencesHelper;
import com.pro.volley.profile.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ClassroomFragment extends Fragment {
    FloatingActionButton fab_classroom;
    public static boolean deleted_refresh = false;
    RecyclerView recyclerView;
    List<Classroom> list;
    String downloaded_response = "null";
    Context context;
    MaterialToolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    //SharedPreferences sharedPreferences, sharedPreferences_classroom;
    SharedPreferencesHelper sharedPreferencesHelper;
    SharedPreferencesHelper.ClassroomSharedPreference sharedPreference_classroom;


    MyClassRoomDetailsAdapter adapter;

    public ClassroomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     **/
/*

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("first time","entered oncreatew time");


    }

*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<Classroom>();

//download_content_from_server();
        Log.i("Activity createwe", "Activity created");
        fab_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_classroom_clicked();
            }
        });
        update_user_interface();
        load_recyclerView(getContext());
        swiped();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        sharedPreferences = context.getSharedPreferences("com.pro.volley", Context.MODE_PRIVATE);
//        sharedPreferences_classroom = context.getSharedPreferences("com.pro.volley.classroom", Context.MODE_PRIVATE);

        //sharedPreference_classroom = new SharedPreferencesHelper(context, "com.pro.volley.classroom");
        //sharedPreference_classroom=sharedPreferencesHelper.new ClassroomSharedPreference(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(context, "com.pro.volley");
        sharedPreference_classroom = sharedPreferencesHelper.getclassroomSharedPreference();
//        download_content_from_server_first_time(context);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.layout_classroom, container, false);
        recyclerView = view.findViewById(R.id.rv_classroom);
        swipeRefreshLayout = view.findViewById(R.id.sr_classroom);
        fab_classroom = view.findViewById(R.id.fab_join_class);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_top);
        toolbar.setTitle("ClassRoom");
        toolbar.setTitleTextColor(Color.parseColor("#ff0000"));
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings:
                        Toast.makeText(context, "settings", Toast.LENGTH_LONG).show();
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
        context = view.getContext();
        update_user_interface();
        return view;
    }

    private void swiped() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                download_content_from_server();

            }
        });
    }

    private void download_content_from_server() {
        AsyncTaskExample asyncTask = new AsyncTaskExample();
        asyncTask.execute();
        // update_user_interface();
    }

    private void load_recyclerView(Context context) {
        if (!sharedPreference_classroom.getSaved_classrooms_data_array().equalsIgnoreCase("null")) {
            update_user_interface();
        } else {
            download_content_from_server();
        }


    }

    public void update_user_interface() {
        //String class_array = sharedPreferences_classroom.getString("saved_classrooms_data_array", "null");
        String class_array = sharedPreference_classroom.getSaved_classrooms_data_array();
        list = new ArrayList<Classroom>();
        if (deleted_refresh) {
            deleted_refresh = false;
            download_content_from_server();

        }
        if (class_array.equalsIgnoreCase("null")) {

            Toast.makeText(context, "Try to download again", Toast.LENGTH_SHORT);
            download_content_from_server();

        } else if (class_array.equalsIgnoreCase("zeroclassroomjoined")) {
            Log.i("user interface ", "zeroclassess joined");

        } else {
            Log.i("user interface ", "contains data" + class_array);

            try {

                JSONArray jsonArray = new JSONArray(class_array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Classroom c = new Classroom(jsonObject.optString("class_code"), jsonObject.optString("class_title"));
                    list.add(c);

                }


            } catch (JSONException j) {
                Log.i("Json array ", "Error");
            }

        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //if (list.size()>1){
            recyclerView.setLayoutManager(new GridLayoutManager(context,2));
            // In landscape
              //  }else {
                //recyclerView.setLayoutManager(new LinearLayoutManager(context));
           // }
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // In portrait
        }
        adapter = new MyClassRoomDetailsAdapter(list, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onREsume  ", "onresume called");

        try {
            update_user_interface();
        } catch (Exception e) {
            Log.i("Onresume  errorrrrrr   ", e.getMessage());
            e.printStackTrace();

        }
    }

    private void fab_classroom_clicked() {
        //join class
        Intent i = new Intent(getContext(), JoinClassRoom.class);
        startActivity(i);

    }

    private class AsyncTaskExample extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", " executed");

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {


            Log.i("doInBackground", " executed");

            //
            // final RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.urlclassstudent) +
                            "/returnJoinedClass.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    downloaded_response = response;
                    Log.i("got Response  doin back   ", response + "    ");

                    try {
                        JSONObject jsonObject = new JSONObject(downloaded_response);
                       // Log.i("doin back", " executed" + downloaded_response);
                        if (jsonObject.optString("title").equalsIgnoreCase("success")) {
                            //sharedPreferences_classroom.edit().putString("saved_classrooms_data_array", jsonObject.optString("message")).apply();
                            sharedPreference_classroom.setSaved_classrooms_data_array(jsonObject.optString("message"));
                            update_user_interface();

                        } else if (jsonObject.optString("title").equalsIgnoreCase("unsuccess")) {
                            //     sharedPreferences_classroom.edit().putString("saved_classrooms_data_array","null").apply();
                            //  sharedPreferencesHelper_classroom.removeSaved_classrooms_data_array();
                            sharedPreference_classroom.setSaved_classrooms_data_array("zeroclassroomjoined");
                            //sharedPreferences_classroom.edit().remove("saved_classrooms_data_array").apply();
                            update_user_interface();

                        }
                    } catch (Exception e) {
                        Log.i("Got excespptoion in doinback", "doin background Exe" + e.getMessage());

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("got error", "no response" + error.getMessage());
                    Toast.makeText(context, "connection failed", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("usn", sharedPreferences.getString("usn", "17cs030"));
                    params.put("usn", sharedPreferencesHelper.getUsn());
                    return params;
                }

            };

            int socketTimeOut = 50000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);


            return "null";
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            swipeRefreshLayout.setRefreshing(false);



        }

    }
}