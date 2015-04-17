package com.example.youngyeehomies.hssapp.Boundary;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youngyeehomies.hssapp.R;

/*
** This is the fragment class which allows for the user select the time slot of appointment
 */

public class CreateAppointmentFragment3 extends Fragment {

    String selectedDate, selectedClinic, selectedApptType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        selectedClinic = getArguments().getString("selectedClinic");
        selectedDate = getArguments().getString("selectedDate");
        selectedApptType = getArguments().getString("selectedApptType");


        View v = inflater.inflate(R.layout.create_appt_fragment_layout3, container, false);

        //Display details of appointment creation
        TextView selectedApptTypeTV = (TextView) v.findViewById(R.id.apptTypeSelected);
        selectedApptTypeTV.setText(selectedApptType);
        selectedApptTypeTV.setTypeface(null, Typeface.ITALIC);

        TextView selectedClinicTV = (TextView) v.findViewById(R.id.clinicSelected);
        selectedClinicTV.setText(selectedClinic);
        selectedClinicTV.setTypeface(null, Typeface.ITALIC);

        TextView selectedDateTV = (TextView) v.findViewById(R.id.dateSelected);
        selectedDateTV.setText(selectedDate);
        selectedDateTV.setTypeface(null, Typeface.ITALIC);


        return v;
    }

}