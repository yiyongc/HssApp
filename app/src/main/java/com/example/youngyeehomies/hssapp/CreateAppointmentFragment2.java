package com.example.youngyeehomies.hssapp;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class CreateAppointmentFragment2 extends Fragment {

    Button dateSelectorBtn;
    Spinner clinicSpinner;
    String[] clinicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //int typeID = getArguments().getInt("typeID");

        clinicList = getResources().getStringArray(R.array.clinics);

        View v = inflater.inflate(R.layout.create_appt_fragment_layout2, container, false);

        //TextView textView = (TextView) v.findViewById(R.id.page2textView);
        //textView.setText(Integer.toString(typeID));

        //Populate Spinner
        clinicSpinner = (Spinner) v.findViewById(R.id.clinicSelection);
        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(v.getContext(), R.layout.custom_spinner_layout, clinicList);
        clinicSpinner.setAdapter(customAdapter);

        //Initialize current date value on button
        final Calendar c = Calendar.getInstance();
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