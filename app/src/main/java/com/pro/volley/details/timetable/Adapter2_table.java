package com.pro.volley.details.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.volley.R;

import java.util.List;

public class Adapter2_table extends RecyclerView.Adapter<Adapter2_table.ViewHolder> {

    public Adapter2_table(List<table_data> listItems, Context context) {
        this.context=context;
                this.listItems=listItems;
    }
   List<table_data> listItems;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_table_table_data,parent,false);
        return  new Adapter2_table.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

table_data tb=listItems.get(position);
        holder.tv_from.setText(tb.from);
        holder.tv_to.setText(tb.to);
        holder.tv_sub.setText(tb.subject);
        holder.tv_fac.setText(tb.faculty);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { TextView tv_from;
        TextView tv_to;
        TextView tv_sub;
        TextView tv_fac;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_from=itemView.findViewById(R.id.tv_row_time_table_from);
            tv_fac=itemView.findViewById(R.id.tv_row_time_table_faculty);
            tv_sub=itemView.findViewById(R.id.tv_row_time_table_sub);
            tv_to=itemView.findViewById(R.id.tv_row_time_table_to);

        }
    }
}
