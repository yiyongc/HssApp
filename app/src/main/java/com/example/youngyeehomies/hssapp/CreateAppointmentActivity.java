package com.example.youngyeehomies.hssapp;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;


public class CreateAppointmentActivity extends DrawerActivity {

    FragmentTransaction fTrans;
    android.app.FragmentManager fragmentManager;

    Spinner typeSpinner, clinicSpinner;
    int typeID;
    String apptType, clinic, date;
    int day, month, year;

    @Override
    protected void onResume() {
        super.onResume();
        Globals.drawerPosition = 0;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment_layout);
        set();
        Globals.drawerPosition = 0;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);

        fragmentManager = getFragmentManager();

        fTrans = fragmentManager.beginTransaction();



        if (savedInstanceState == null) {

            Fragment fragment1 = new CreateAppointmentFragment();
            fTrans.add(R.id.createApptSpace, fragment1);
            fTrans.commit();

        }



}






    public void createAppointment(View view) {

        String dateTimeObject, selectedDate, selectedTime;
        TextView selectedDateTV = (TextView) findViewById(R.id.dateSelected);
        Spinner selectedTimeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);
        selectedDate = selectedDateTV.getText().toString();
        selectedTime = selectedTimeSpinner.getSelectedItem().toString();

        dateTimeObject = new DateTimeConverter().convertDateAndTime(selectedDate, selectedTime);
        
        TextView test = (TextView) findViewById(R.id.textView33);
        test.setText(dateTimeObject);


        Intent completedCreationIntent = new Intent(this, ViewAppointmentActivity.class);

        if(true) {
            Toast.makeText(CreateAppointmentActivity.this, "Appointment has been created! A reminder notification will be sent one day before the day of the appointment!", Toast.LENGTH_LONG).show();
            //startActivity(completedCreationIntent);
            //finish();

        }
        else {
            Toast.makeText(CreateAppointmentActivity.this, "Appointment Creation Failed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void btnNext1(View view) {

        typeSpinner = (Spinner) findViewById(R.id.apptTypeSpinner);
        typeID = typeSpinner.getSelectedItemPosition();
        apptType = typeSpinner.getSelectedItem().toString();

        Bundle args = new Bundle();
        args.putInt("typeID", typeID);
        Fragment fragment2 = new CreateAppointmentFragment2();
        fragment2.setArguments(args);

        FragmentTransaction fTrans2 = fragmentManager.beginTransaction();
        fTrans2.replace(R.id.createApptSpace, fragment2);
        fTrans2.addToBackStack(null);
        fTrans2.commit();
    }

    public void btnNext2(View view) {
        Button dateSelectorBtn = (Button) findViewById(R.id.dateSlotSelection);
        clinicSpinner = (Spinner) findViewById(R.id.clinicSelection);
        clinic = clinicSpinner.getSelectedItem().toString();
        date = dateSelectorBtn.getText()+"";

        Bundle args = new Bundle();
        args.putInt("typeID", typeID);
        args.putString("selectedApptType", apptType);
        args.putString("selectedClinic", clinic);
        args.putString("selectedDate", date);

        Fragment fragment3 = new CreateAppointmentFragment3();
        fragment3.setArguments(args);

        FragmentTransaction fTrans3 = fragmentManager.beginTransaction();
        fTrans3.replace(R.id.createApptSpace, fragment3);
        fTrans3.addToBackStack(null);
        fTrans3.commit();
    }

    public void btnPrev(View view) {
        fragmentManager.popBackStackImmediate();
    }
}