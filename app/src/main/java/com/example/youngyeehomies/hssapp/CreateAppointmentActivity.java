package com.example.youngyeehomies.hssapp;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;


public class CreateAppointmentActivity extends DrawerActivity {


    private Button dateSelectorBtn;
    private String[] radioItems;
    private LinearLayout linearLayout;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment_layout);
        set();
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);

        //Initialize current date value on button
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateSelectorBtn = (Button) findViewById(R.id.dateSlotSelection);
        dateSelectorBtn.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" "));

        addListenerOnDateButton();

        Spinner timeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);

        //timeSpinner Configurations
        TimeSpinnerAdapter timeAdapter = new TimeSpinnerAdapter(getApplicationContext(), R.layout.custom_spinner_layout, getResources().getStringArray(R.array.timeslot));
        timeSpinner.setAdapter(timeAdapter);


        //Populating and adding RadioGroup
        rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL); //layout of RadioGroup
        radioItems = getResources().getStringArray(R.array.clinics);
        linearLayout = (LinearLayout) findViewById(R.id.radioGroupLayout);
        createRadioButton(rg);
        linearLayout.addView(rg); //add the whole RadioGroup to the layout
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

    private void createRadioButton(RadioGroup rg) {

        final RadioButton[] rb = new RadioButton[5];

        for(int i=0; i<5; i++){
            rb[i]  = new RadioButton(this);
            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup
            rb[i].setText(radioItems[i]);
            rb[i].setTextSize(14);
        }

        rb[0].setChecked(true);

    }



    public void createAppointment(View view) {

        int selectedId = rg.getCheckedRadioButtonId(); //the index of the item in the RadioGroup
        //getDate and timeSlot and create the JSON object

        Intent completedCreationIntent = new Intent(this, ViewAppointmentActivity.class);

        if(true) {
            Toast.makeText(CreateAppointmentActivity.this, "Appointment has been created! A reminder notification will be sent one day before the day of the appointment!", Toast.LENGTH_LONG).show();
            startActivity(completedCreationIntent);
            finish();
        }
        else {
            Toast.makeText(CreateAppointmentActivity.this, "Appointment Creation Failed.", Toast.LENGTH_SHORT).show();
        }
    }
}