package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Entities.AppointmentDetailsItem;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ViewAppointmentDetailsActivity extends Activity {

    SessionManager session;

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

        session = new SessionManager(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            AppointmentID = extras.getInt("AppointmentID");
            Log.i("HSS", "ViwAppointmentDetails onCreate appointmentID: "+ AppointmentID);
            //populate text views and image views with data method

            getAppointment(session.getUserToken());

            //populateAppointmentDetails(value);
            //displayAppointmentDetails();

        }
        else{
            //populate text view and image view with Error Message
            Log.e("HSS", "problem in viewappointmentdetails oncreate else");
        }
    }

//This method tags xml to java

    public void populateAppointmentDetails(int value){
        //test item, to add Json Object code when working
     //   appointmentDetails = new AppointmentDetailsItem(R.drawable.women_ic,"Women's Clinic" + i, "Breast Scan", "Gibson Gynaecology","89 Feb 2035","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment");

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
            AppointmentCatIcon.setImageDrawable(appointmentDetails.getApptCatIcon());
            AppointmentDetailsNote.setText(appointmentDetails.getApptNote());
            AppointmentDetailsCatName.setText(appointmentDetails.getApptCategoryName());
            AppointmentDetailsSubCat.setText(appointmentDetails.getApptSubCategory());
            AppointmentDetailsTime.setText(appointmentDetails.getApptTime());
            AppointmentDetailsDate.setText(appointmentDetails.getApptDate());
            AppointmentDetailsClinic.setText(appointmentDetails.getApptClinic());

        }
        Log.i("HSS", "ViwAppointmentDetails displayAppointmentDetails appointmentID: "+ AppointmentID);

    }

    public void getAppointment(String accountToken){
        Log.i("HSS", "ViwAppointmentDetails getAppointment appointmentID: "+ AppointmentID);
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("ID", AppointmentID);
        } catch (Exception e) {
            Log.e("HSS", "problem in viewappointmentdetails");
        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                getAppointmentAsyncReturn((String)o);
            }
        };
        svc.setServiceLink("viewAppt.php");
        svc.execute(obj.toString());
    }

    public void getAppointmentAsyncReturn(String webResponse){

        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0){

                String dateTime = jsonobj.getString("DateTime");
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date date = dateParser.parse(dateTime);
                Resources res = getResources();
                TypedArray icons = res.obtainTypedArray(R.array.cat_icons);

                String day = new SimpleDateFormat("dd MMM yyyy").format(date);
                String time = new SimpleDateFormat("hh:mm a").format(date);

                Drawable catIcon = icons.getDrawable(jsonobj.getInt("Category ID") - 20);

                appointmentDetails = new AppointmentDetailsItem(
                        catIcon,
                        jsonobj.getString("Appointment Category"),
                        jsonobj.getString("Appointment Subcategory"),
                        jsonobj.getString("Clinic Name"),
                        day,
                        time,
                        jsonobj.getString("Instructions")
                );

                displayAppointmentDetails();
            } else {
                Log.e("HSS", "problem in viewappointmentdetails, error from web");
            }
        } catch (Exception e){
            Toast.makeText(ViewAppointmentDetailsActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
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

        //TWeb Service
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("ID", AppointmentID);
        } catch (Exception e) {
            Log.e("HSS", "problem in viewappointmentdetails deleteappointment");
        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                deleteAppointmentAsyncReturn((String)o);
            }
        };
        svc.setServiceLink("deleteAppt.php");
        svc.execute(obj.toString());

    }

    public void deleteAppointmentAsyncReturn(String webResponse){
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if (jsonobj.getInt("errorCode")==0) {
                Toast.makeText(this, "Deleted Appointment Object", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                ImageButton deleteButton = (ImageButton)findViewById(R.id.delete_appointment_button);
                deleteButton.setEnabled(true);
            }
        } catch (Exception e) {
            Toast.makeText(ViewAppointmentDetailsActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }


    }
}
