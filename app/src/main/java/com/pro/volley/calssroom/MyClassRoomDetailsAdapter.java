package com.pro.volley.calssroom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.pro.volley.R;
import com.pro.volley.extra.timetable.Adapter2_table;
import com.pro.volley.extra.timetable.table_data;

import java.util.ArrayList;
import java.util.List;

public class MyClassRoomDetailsAdapter extends RecyclerView.Adapter<MyClassRoomDetailsAdapter.ViewHolder> {
    private  Context context;
    // private Classroom[] classroom;
    private List<Classroom> classrooms;

    public MyClassRoomDetailsAdapter(List<Classroom> classrooms, Context context) {
        this.context=context;
        this.classrooms=classrooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_classroom_class_title,parent,false);
        return  new MyClassRoomDetailsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

         Classroom myListData = classrooms.get(position);
        holder.tv_title.setText(myListData.title);
        holder.tv_code.setText(myListData.code);
        holder.materialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),"long click item: to enroll class alert dialog with two options ",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: ",Toast.LENGTH_LONG).show();
                Intent i=new Intent(view.getContext(),Individual_Class.class);
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
        public MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_code = itemView.findViewById(R.id.tv_classroom_row_code);
            this.tv_title = itemView.findViewById(R.id.tv_classroom_row_title);
            this.materialCardView = itemView.findViewById(R.id.materialCardView_classroom);
        }
    }
}
