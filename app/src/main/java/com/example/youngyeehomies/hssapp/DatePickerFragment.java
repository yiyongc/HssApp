package com.example.youngyeehomies.hssapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

        Button activityButton = (Button) getActivity().findViewById(R.id.dateSlotSelection);

        if(selectedYear < year || (selectedYear == year && selectedMonth < month) || ((selectedYear == year) && (selectedMonth == month) && (selectedDay <= day)))
            Toast.makeText(getActivity().getApplicationContext(), "Unable to create appointments before current date!", Toast.LENGTH_LONG).show();
        else if(selectedYear > year || selectedMonth > (month+3) ||((selectedMonth == (month+3)) && (selectedDay > day)))
            Toast.makeText(getActivity().getApplicationContext(), "Appointments can only be made up to 3 months in advance!", Toast.LENGTH_LONG).show();
        else {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            DecimalFormat twoFormat = new DecimalFormat("00");

            activityButton.setText(new StringBuilder().append(twoFormat.format(day)).append("-").append(twoFormat.format(month + 1)).append("-").append(year).append(" "));
        }
    }

}