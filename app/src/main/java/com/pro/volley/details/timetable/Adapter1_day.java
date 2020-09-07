package com.pro.volley.details.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.volley.R;

import java.util.List;

public class Adapter1_day extends RecyclerView.Adapter<Adapter1_day.ViewHolder> {
    public Adapter1_day(List<String> list, Context context,ItemClickListener clickListener)
    {
        this.context=context;
        this.clickListener=clickListener;
        this.listItems=list;
    }
    List<String> listItems;
    Context context;
    private ItemClickListener clickListener;
    @NonNull
    @Override
    public Adapter1_day.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_timetable_day,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter1_day.ViewHolder holder, int position) {
            holder.tv_head.setText(listItems.get(position));
                }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder  {
        TextView tv_head;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_head=itemView.findViewById(R.id.tv_row_timetable_days);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });

        }


    }
}
