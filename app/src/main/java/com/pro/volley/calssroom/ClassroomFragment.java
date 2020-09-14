package com.pro.volley.calssroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro.volley.R;

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
    RecyclerView recyclerView;
    List<Classroom> list;
    String downloaded_response = "null";
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences, sharedPreferences_classroom;


    public ClassroomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        list = new ArrayList<Classroom>();

        final View view = inflater.inflate(R.layout.layout_classroom, container, false);
        recyclerView = view.findViewById(R.id.rv_classroom);
        swipeRefreshLayout = view.findViewById(R.id.sr_classroom);
        fab_classroom = view.findViewById(R.id.fab_join_class);
        sharedPreferences = view.getContext().getSharedPreferences("com.pro.volley", Context.MODE_PRIVATE);
        sharedPreferences_classroom = view.getContext().getSharedPreferences("com.pro.volley.classroom", Context.MODE_PRIVATE);
        fab_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_classroom_clicked();
            }
        });
        context = view.getContext();
        load_recyclerView(view.getContext());
        swiped();


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


    }

    private void load_recyclerView(Context context) {

        /** Classroom c = new Classroom("1st class", "A");
         Classroom c1 = new Classroom("2st class", "b");
         list.add(c);
         list.add(c1);*/
        if (sharedPreferences_classroom.contains("saved_classrooms_data_array")) {
            update_user_interface();
        } else {
            download_content_from_server();
        }


    }

    private void update_user_interface() {
        Log.i("user interface ", "user interface");
        String class_array = sharedPreferences_classroom.getString("saved_classrooms_data_array", "null");
        if (class_array.equalsIgnoreCase("null")) {
            Toast.makeText(context, "Try to download again", Toast.LENGTH_SHORT);


        } else {

            list = new ArrayList<Classroom>();
            try {
                JSONArray jsonArray = new JSONArray(class_array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Classroom c = new Classroom(jsonObject.optString("class_code"), jsonObject.optString("class_title"));
                    list.add(c);

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                MyClassRoomDetailsAdapter adapter = new MyClassRoomDetailsAdapter(list, context);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);


            } catch (JSONException j) {
                Log.i("Json array ", "Error");
            }

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
                    getResources().getString(R.string.urlroot) +
                            "/mycollege/Classroom/student/returnJoinedClass.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    downloaded_response = response;
                    // Log.i("got Response", response);

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
                    params.put("usn", sharedPreferences.getString("usn", "17cs030"));
                    return params;
                }

            };

            int socketTimeOut = 5000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
               stringRequest.setRetryPolicy(policy);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(stringRequest);





            return downloaded_response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            swipeRefreshLayout.setRefreshing(false);
            Log.i("onPostExecute", " executed" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optString("title", "null").equalsIgnoreCase("success")) {
                    sharedPreferences_classroom.edit().putString("saved_classrooms_data_array", jsonObject.optString("message")).apply();
                    update_user_interface();

                }
            } catch (Exception e) {
                Log.i("Got excespptoion in onpostexecute", "onPost Exe");

            }

        }
    }
}