package com.pro.volley.calssroom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MyClassRoomDetailsAdapter extends RecyclerView.Adapter<MyClassRoomDetailsAdapter.ViewHolder> {
    AlertDialog.Builder builder;
    // SharedPreferences sharedPreferences, sharedPreferences_classroom;
    SharedPreferencesHelper sharedPreferencesHelper;
    SharedPreferencesHelper.ClassroomSharedPreference sharedPreference_classroom;
    Classroom myListData;
    private Context context;
    // private Classroom[] classroom;
    private List<Classroom> classrooms;

    public MyClassRoomDetailsAdapter(List<Classroom> classrooms, Context context) {
        this.context = context;
        sharedPreferencesHelper = new SharedPreferencesHelper(context, "com.pro.volley");
        sharedPreference_classroom = sharedPreferencesHelper.new ClassroomSharedPreference(context);


        this.classrooms = classrooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_classroom_class_title, parent, false);
        return new MyClassRoomDetailsAdapter.ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        myListData = classrooms.get(position);
//        GradientDrawable gd = new GradientDrawable(
//                GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.parseColor(string_colors[position % string_colors.length]), Color.parseColor(string_colors[(position + 1) % string_colors.length])});
//      gd.setCornerRadius((float) 20.0);
//      gd.setPadding(10,10,10,10);
//      gd.setStroke(5,Color.parseColor(string_colors[(position+1)%string_colors.length]));
//        holder.materialCardView.setBackground(gd);
        holder.tv_title.setText(myListData.title);
        holder.tv_code.setText(myListData.code);
        builder = new AlertDialog.Builder(context);
        //builder.setTitle("Unernroll from "+myListData.title);

        holder.materialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Toast.makeText(v.getContext(), "long click item: to enroll class alert dialog with two options ", Toast.LENGTH_LONG).show();


                //Setting message manually and performing action on button click
                builder.setMessage("You'll be no longer be able to see the class or participate in it.. ")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//                                Toast.makeText(context, "you choose yes action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
                                unenroll_from_class();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                                Toast.makeText(context, "you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Unenroll from " + myListData.title + " ? ");
                alert.show();


                return true;
            }

            private void unenroll_from_class() {
                Log.i("asdasdasdasd","dasdashasjbdasbdbashdasdsdjasbdasjd    "+myListData.code);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.urlclassstudent) + "/unEnRollStudent.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(" unenroll", response);
                        try {
                            JSONObject j = new JSONObject(response);
                            if (j.optString("title").equalsIgnoreCase("success")) {
                                //  if (!sharedPreferencesHelper_classroom.getSaved_classrooms_data_array().equalsIgnoreCase("null")) {
                                sharedPreference_classroom.removeSaved_classrooms_data_array();
                                ClassroomFragment.deleted_refredsh = true;


                                //}

                            } else if (j.optString("title").equalsIgnoreCase("unsuccess")) {
                                Toast.makeText(context, "refresh your screen", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
                        Log.i("Error while  unenroll", "Un en roll" + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("usn", sharedPreferences.getString("usn", "001"));
                        params.put("usn", sharedPreferencesHelper.getUsn());
                        params.put("classcode", holder.tv_code.getText().toString());
                        return params;
                    }

                };

                RetryPolicy policy = new DefaultRetryPolicy(50000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(stringRequest);

            }
        });
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: ", Toast.LENGTH_LONG).show();
                Log.i("MYCLAASSROOM ADAPTER   :","dasdsadsada        code is "+myListData.code);
                Intent i = new Intent(view.getContext(), Individual_Class.class);
                i.putExtra("CLASS_CODE",holder.tv_code.getText().toString().trim());
                context.startActivity(i);



            }
        });

    }

    @Override
    public int getItemCount() {
        return classrooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_code;
        String string_colors[] = {"#544a7d", "#afd452", "#009FFF", "#ec2F4B", "#eaafc8", "#654ea3"};

        public MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_code = itemView.findViewById(R.id.tv_classroom_row_code);
            this.tv_title = itemView.findViewById(R.id.tv_classroom_row_title);
            this.materialCardView = itemView.findViewById(R.id.materialCardView_classroom);
            Random r=new Random();
            int i;
//            GradientDrawable gd = new GradientDrawable(
//                    GradientDrawable.Orientation.LEFT_RIGHT, new int[]{i=Color.parseColor(this.string_colors[r.nextInt(20)%this.string_colors.length]), Color.parseColor(this.string_colors[r.nextInt(20)%this.string_colors.length])});
//            gd.setCornerRadius((float) 20.0);
//   //         gd.setPadding(10,10,10,10);
//            gd.setStroke(5,i);
//            this.materialCardView.setBackground(gd);


        }
    }


}
