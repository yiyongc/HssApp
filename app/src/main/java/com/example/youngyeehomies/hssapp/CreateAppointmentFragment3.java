package com.example.youngyeehomies.hssapp;


import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class CreateAppointmentFragment3 extends Fragment {

    String[] timeSlotsAvailable;
    Spinner timeSlotSpinner;
    String selectedDate, selectedClinic, selectedApptType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        selectedClinic = getArguments().getString("selectedClinic");
        selectedDate = getArguments().getString("selectedDate");
        selectedApptType = getArguments().getString("selectedApptType");

        timeSlotsAvailable = getResources().getStringArray(R.array.timeslot);

        View createAppointmentView3 = inflater.inflate(R.layout.create_appt_fragment_layout3, container, false);

        //Display details of appointment creation
        TextView selectedApptTypeTV = (TextView) createAppointmentView3.findViewById(R.id.apptTypeSelected);
        selectedApptTypeTV.setText(selectedApptType);
        selectedApptTypeTV.setTypeface(null, Typeface.ITALIC);

        TextView selectedClinicTV = (TextView) createAppointmentView3.findViewById(R.id.clinicSelected);
        selectedClinicTV.setText(selectedClinic);
        selectedClinicTV.setTypeface(null, Typeface.ITALIC);

        TextView selectedDateTV = (TextView) createAppointmentView3.findViewById(R.id.dateSelected);
        selectedDateTV.setText(selectedDate);
        selectedDateTV.setTypeface(null, Typeface.ITALIC);



        return createAppointmentView3;
    }

}