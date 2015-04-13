package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Entities.AppointmentDetailsItem;


public class ViewAppointmentDetailsActivity extends Activity {

    AppointmentDetailsItem appointmentDetails;
    int AppointmentID;
    ImageView AppointmentCatIcon;
    TextView AppointmentDetailsNote;
    TextView AppointmentDetailsCatName;
    TextView AppointmentDetailsSubCat;
    TextView AppointmentDetailsTime;
    TextView AppointmentDetailsDate;
    TextView AppointmentDetailsClinic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_details);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int value = extras.getInt("AppointmentID");
            //populate text views and image views with data method
            populateAppointmentDetails(value);
            displayAppointmentDetails();


        }
        else{
            //populate text view and image view with Error Message
        }
    }

//This method tags xml to java

    public void populateAppointmentDetails(int value){
        //test item, to add Json Object code when working
        String i = Integer.toString(value); // just to see if APpointmentID passes in
        appointmentDetails = new AppointmentDetailsItem(R.drawable.women_ic,"Women's Clinic" + i, "Breast Scan", "Gibson Gynaecology","89 Feb 2035","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment");

    }


    public void displayAppointmentDetails() {
        AppointmentCatIcon = (ImageView) findViewById(R.id.appointment_details_cat_icon);
        AppointmentDetailsNote = (TextView) findViewById(R.id.appointment_details_notes);
        AppointmentDetailsCatName = (TextView) findViewById(R.id.appointment_details_cat_name);
        AppointmentDetailsSubCat = (TextView) findViewById(R.id.appointment_details_subcat);
        AppointmentDetailsTime = (TextView) findViewById(R.id.appointment_details_time);
        AppointmentDetailsDate = (TextView) findViewById(R.id.appointment_details_date);
        AppointmentDetailsClinic = (TextView) findViewById(R.id.appointment_details_clinic);

        if (appointmentDetails != null) {
            AppointmentCatIcon.setImageResource(appointmentDetails.getApptCatID());
            AppointmentDetailsNote.setText(appointmentDetails.getApptNote());
            AppointmentDetailsCatName.setText(appointmentDetails.getApptCategoryName());
            AppointmentDetailsSubCat.setText(appointmentDetails.getApptSubCategory());
            AppointmentDetailsTime.setText(appointmentDetails.getApptTime());
            AppointmentDetailsDate.setText(appointmentDetails.getApptDate());
            AppointmentDetailsClinic.setText(appointmentDetails.getApptClinic());

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_appointment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void deleteAppointment(View view) {
        //TODO Delete Appointment JSON
        ImageButton deleteButton = (ImageButton)findViewById(R.id.delete_appointment_button);
        deleteButton.setEnabled(false);
        if (true) {
            Toast.makeText(this, "Deleted Appointment Object", Toast.LENGTH_SHORT).show();
            finish();

        }
        else {
            deleteButton.setEnabled(true);
        }
    }
}
