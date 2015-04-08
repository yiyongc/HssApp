package com.example.youngyeehomies.hssapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;


import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

        Button activityButton = (Button) getActivity().findViewById(R.id.dateSlotSelection);

        year = selectedYear;
        month = selectedMonth;
        day = selectedDay;

        activityButton.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" "));

    }

}