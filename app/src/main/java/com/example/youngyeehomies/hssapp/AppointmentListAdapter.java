package com.example.youngyeehomies.hssapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import java.util.List;


/**
 * Created by Young Yee on 11/4/2015.
 *
 * This class allows CardViews to be populated into a ListView
 */


public class AppointmentListAdapter extends ListView.Adapter<AppointmentListAdapter.AppointmentViewHolder>{
    public static class AppointmentViewHolder extends ListView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }



}
