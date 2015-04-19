package com.example.youngyeehomies.hssapp.Boundary;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.youngyeehomies.hssapp.Control.CustomSpinnerAdapter;
import com.example.youngyeehomies.hssapp.Boundary.DatePickerFragment;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.R;

import java.text.DecimalFormat;
import java.util.Calendar;

/*
** This is the fragment class which allows for the user select the date of appointment
 */

public class CreateAppointmentFragment2 extends Fragment {

    Button dateSelectorBtn;
    Spinner clinicSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.create_appt_fragment_layout2, container, false);

        //Populate Spinner
        if (Globals.clinicsInSpinner != null) {
            clinicSpinner = (Spinner) v.findViewById(R.id.clinicSelection);
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(v.getContext(), R.layout.custom_spinner_layout, Globals.clinicsInSpinner);
            clinicSpinner.setAdapter(customAdapter);
        }

        //Initialize next day's date value on button
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DecimalFormat twoFormat = new DecimalFormat("00");

        dateSelectorBtn = (Button) v.findViewById(R.id.dateSlotSelection);
        dateSelectorBtn.setText(new StringBuilder().append(twoFormat.format(day)).append("-").append(twoFormat.format(month + 1))
                .append("-").append(new DecimalFormat("0000").format(year)).append(" "));

        addListenerOnDateButton();

        return v;
    }


    // Initiates the Date Picker Dialog when date button is pressed
    public void addListenerOnDateButton() {


        dateSelectorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDatePickerDialog(v);
            }

        });

    }

    public void showDatePickerDialog(View v) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }
}