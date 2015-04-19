package com.example.youngyeehomies.hssapp.Boundary;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.youngyeehomies.hssapp.Control.CustomSpinnerAdapter;
import com.example.youngyeehomies.hssapp.R;

/*
** This is the fragment class which allows for the user select the type of appointment
*
* Created by Chee Yi Yong
 */

public class CreateAppointmentFragment extends Fragment {

    Spinner typeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] typeOfAppointment = getResources().getStringArray(R.array.appointmentTypes);

        View v = inflater.inflate(R.layout.create_appt_fragment_layout, container, false);

        //Populate Spinner with all available types of appointments
        typeSpinner = (Spinner) v.findViewById(R.id.apptTypeSpinner);
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(v.getContext(), R.layout.custom_spinner_layout, typeOfAppointment);
        typeSpinner.setAdapter(customAdapter);

        return v;
    }



}