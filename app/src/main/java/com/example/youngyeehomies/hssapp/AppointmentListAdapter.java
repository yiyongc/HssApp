package com.example.youngyeehomies.hssapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import java.util.List;


/**
 * Created by Young Yee on 11/4/2015.
 *
 * This class allows CardViews to be populated into a RecyclerView
 */


public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder>{

    List<AppointmentListItem> appointments;

    AppointmentListAdapter(List<AppointmentListItem> appointments){
        this.appointments = appointments;
    }


    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {


        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        AppointmentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.AppointmentCardView);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }



    }
    @Override
    public int getItemCount() {
        return appointments.size();
    }
    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_row_layout, viewGroup, false);
        AppointmentViewHolder Avh = new AppointmentViewHolder(v);
        return Avh;
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder appointmentViewHolder, int i) {
        appointmentViewHolder.personName.setText(appointments.get(i).getApptCategoryName());
        appointmentViewHolder.personAge.setText(appointments.get(i).getApptNote());
        appointmentViewHolder.personPhoto.setImageResource(appointments.get(i).getApptSubcategoryID());
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
