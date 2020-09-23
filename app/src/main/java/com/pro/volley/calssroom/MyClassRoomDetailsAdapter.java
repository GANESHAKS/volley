package com.pro.volley.calssroom;

import android.content.Context;
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

import com.google.android.material.card.MaterialCardView;
import com.pro.volley.R;
import com.pro.volley.SharedPreferencesHelper;

import java.util.List;
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
    DataBase_Manager_Class dataBase_manager_class;

    public MyClassRoomDetailsAdapter(List<Classroom> classrooms, Context context) {
        this.context = context;
        sharedPreferencesHelper = new SharedPreferencesHelper(context, "com.pro.volley");
        sharedPreference_classroom = sharedPreferencesHelper.new ClassroomSharedPreference(context);
        dataBase_manager_class = new DataBase_Manager_Class(context);
        dataBase_manager_class.open();

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

                Intent i = new Intent(context, Classroom_delete.class);
                i.putExtra("CLASS_CODE", holder.tv_code.getText().toString().trim());
                i.putExtra("CLASS_TITLE", holder.tv_title.getText().toString().trim());
                context.startActivity(i);


                // Toast.makeText(v.getContext(), "long click item: to enroll class alert dialog with two options ", Toast.LENGTH_LONG).show();


                //Setting message manually and performing action on button click
               /* builder.setMessage("You'll be no longer be able to see the class or participate in it.. ")
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

*/
                return true;
            }



        });

        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: ", Toast.LENGTH_LONG).show();
                boolean f = false;

                if (dataBase_manager_class.class_Contains_code(holder.tv_code.getText().toString().trim())) {

                    Intent i = new Intent(view.getContext(), Individual_Class.class);
                    i.putExtra("CLASS_CODE", holder.tv_code.getText().toString().trim());
                    context.startActivity(i);
                    f = false;
                } else {
                    Log.i("MYCLAASSROOM ADAPTER   :", "onclick        code is :" + holder.tv_code.getText().toString().trim()
                    );
                    Toast.makeText(context, "Class not found Refresh your Screen", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        if (classrooms != null) {

            return classrooms.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_code;
        String[] string_colors = {"#544a7d", "#afd452", "#009FFF", "#ec2F4B", "#eaafc8", "#654ea3"};

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
