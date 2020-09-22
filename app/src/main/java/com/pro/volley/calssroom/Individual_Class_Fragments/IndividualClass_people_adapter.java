package com.pro.volley.calssroom.Individual_Class_Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.volley.R;

import java.util.ArrayList;

public class IndividualClass_people_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_HEADER = 0;
    public static final int ITEM_PEOPLE = 1;
    ArrayList<Peoples> peoplesList;
    Peoples peoples;
    Context context;

    public IndividualClass_people_adapter(ArrayList<Peoples> peoplesList, Context context) {
        this.peoplesList = peoplesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            Log.i("OncreateView Holder retuned  ", "  " + viewType);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_indi_class_people_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            Log.i("OncreateView Holder retuned else ", "  " + viewType);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_indivial_class_people, parent, false);
            return new PeopleViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        peoples = peoplesList.get(position);
        if (getItemViewType(position) == ITEM_HEADER) {
            Log.d("OnBind    if true", peoples.getName());
            //do initializations
            ((HeaderViewHolder) holder).tv_header.setText(peoples.getName());
            // ((HeaderViewHolder) holder).setHeader(peoplesList.get(position));
        } else {
            Log.d("OnBind    if false  ", peoples.getName());
            //((PeopleViewHolder) holder).setPeople(peoplesList.get(position));
            ((PeopleViewHolder) holder).tv_name.setText(peoples.getName());
            //do initializations
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (peoplesList.get(position).getItem_type().equalsIgnoreCase("header")) {
            //   Log.d("Entered getItemView Type", "item view" + position);
            return ITEM_HEADER;
        } else {
            // Log.d("Entered getItemView Type", "item view" + position);
            return ITEM_PEOPLE;
        }
    }


    @Override
    public int getItemCount() {
        return peoplesList.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_header;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_header = itemView.findViewById(R.id.tv_item_indi_class_people_header);
            //image beku
        }


    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {

       public ImageView imageView;
        public TextView tv_name;

        PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_item_indi_class_people_name);
        }


    }


}
