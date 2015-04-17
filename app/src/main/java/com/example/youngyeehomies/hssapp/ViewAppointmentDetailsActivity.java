package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    boolean isUpcoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_view_appointment_details);

        isUpcoming = extras.getBoolean("isUpcoming");
        if(!isUpcoming) {
            ImageButton btn = (ImageButton) findViewById(R.id.delete_appointment_button);
            btn.setVisibility(View.GONE);
        }

        session = new SessionManager(getApplicationContext());

        if(extras !=null) {
            AppointmentID = extras.getInt("AppointmentID");
            Log.i("HSS", "ViwAppointmentDetails onCreate appointmentID: "+ AppointmentID);

            getAppointment(session.getUserToken());
        }
        else{
            //populate text view and image view with Error Message
            Log.e("HSS", "problem in viewappointmentdetails oncreate else");
        }
    }

//This method tags xml to java

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
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia1 = new ProgressDialog(ViewAppointmentDetailsActivity.this);
                Globals.pdia1.setMessage("Obtaining Appointment Details..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                //To Override
                getAppointmentAsyncReturn(o.toString());
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
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Globals.pdia1.dismiss();
            Toast.makeText(ViewAppointmentDetailsActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_appointment, menu);
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

    public void deleteAppointment() {
        //TODO Delete Appointment JSON


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
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia2 = new ProgressDialog(ViewAppointmentDetailsActivity.this);
                Globals.pdia2.setMessage("Deleting Appointment..");
                Globals.pdia2.show();
                Globals.pdia2.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                //To Override
                deleteAppointmentAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("deleteAppt.php");
        svc.execute(obj.toString());

    }

    public void deleteAppointmentConfirm(View view){
        ImageButton deleteButton = (ImageButton)findViewById(R.id.delete_appointment_button);
        deleteButton.setEnabled(false);

        createConfirmationDialog();
        deleteButton.setEnabled(true);

    }

    public void createConfirmationDialog(){
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage("Do you really want to delete this appointment?");
        confirmDialog.setCancelable(false);
        confirmDialog.setTitle("Confirm Delete?");
        confirmDialog.setIcon(R.drawable.warning);
        confirmDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAppointment();
            }
        });

        confirmDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        confirmDialog.create().show();

    }



    public void deleteAppointmentAsyncReturn(String webResponse){
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if (jsonobj.getInt("errorCode")==0) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Appointment Deleted");
                alertDialog.setMessage("Your appointment has been deleted!");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
                alertDialog.setCancelable(false);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
            else {
                ImageButton deleteButton = (ImageButton)findViewById(R.id.delete_appointment_button);
                deleteButton.setEnabled(true);
            }
            Globals.pdia2.dismiss();
        } catch (Exception e) {
            Globals.pdia2.dismiss();
            Toast.makeText(ViewAppointmentDetailsActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }


    }
}
