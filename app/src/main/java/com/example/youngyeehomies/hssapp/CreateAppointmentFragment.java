package com.example.youngyeehomies.hssapp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class CreateAppointmentFragment extends Fragment {

    Spinner typeSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //int position = getArguments().getInt("position");

        String[] typeOfAppointment = getResources().getStringArray(R.array.appointmentTypes);
        String[] clinics = getResources().getStringArray(R.array.clinics);

        View v = inflater.inflate(R.layout.create_appt_fragment_layout, container, false);

        //Populate Spinner
        typeSpinner = (Spinner) v.findViewById(R.id.apptTypeSpinner);
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(v.getContext(), R.layout.custom_spinner_layout, typeOfAppointment);
        typeSpinner.setAdapter(customAdapter);

        return v;
    }



}