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
        TextView ApptCategoryName;
        TextView ApptNotes;
        TextView ApptDate;
        TextView ApptTime;

        ImageView ApptCatIcon;

        AppointmentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.AppointmentCardView);
            ApptCategoryName = (TextView)itemView.findViewById(R.id.appointment_cat_name);
            ApptNotes = (TextView)itemView.findViewById(R.id.appointment_notes);
            ApptDate =  (TextView)itemView.findViewById(R.id.appointment_date);
            ApptTime = (TextView)itemView.findViewById(R.id.appointment_time);
            ApptCatIcon = (ImageView)itemView.findViewById(R.id.appointment_cat_icon);
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
        appointmentViewHolder.ApptCategoryName.setText(appointments.get(i).getApptCategoryName());
        appointmentViewHolder.ApptNotes.setText(appointments.get(i).getApptNote());
        appointmentViewHolder.ApptCatIcon.setImageResource(appointments.get(i).getApptSubcategoryID());
        appointmentViewHolder.ApptDate.setText(appointments.get(i).getApptDate());
        appointmentViewHolder.ApptTime.setText(appointments.get(i).getApptTime());
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
