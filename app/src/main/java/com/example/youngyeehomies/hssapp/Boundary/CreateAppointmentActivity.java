package com.example.youngyeehomies.hssapp.Boundary;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.AlertDialogManager;
import com.example.youngyeehomies.hssapp.Control.CustomSpinnerAdapter;
import com.example.youngyeehomies.hssapp.Control.CustomStringConverter;
import com.example.youngyeehomies.hssapp.Control.SessionManager;
import com.example.youngyeehomies.hssapp.Control.WebServiceClass;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/*
** This is the activity which incorporates all the fragments related to the appointment creation
** It includes selecting type of appointment, date and clinic, followed by time slot of appointment
 */


public class CreateAppointmentActivity extends DrawerActivity {

    SessionManager session;

    FragmentTransaction fTrans;
    android.app.FragmentManager fragmentManager;

    Spinner typeSpinner, clinicSpinner, timeSpinner;
    int typeID;
    String apptType, clinic, date;
    String[] timeSlotsForSpinner;
    ArrayList<String> clinicArrayList = new ArrayList<String>();
    ArrayList<String> timeSlotArrayList = new ArrayList<String> ();
    HashMap<String, Integer> clinicIDPair = new HashMap<String, Integer>();

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

        session = new SessionManager(getApplicationContext());

        fragmentManager = getFragmentManager();

        fTrans = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {

            Fragment fragment1 = new CreateAppointmentFragment();
            fTrans.add(R.id.createApptSpace, fragment1);
            fTrans.commit();

        }

}

    // Select the appointment type and proceeds to find the
    // available clinics for that specific appt type
    public void btnNext1(View view) {

        typeSpinner = (Spinner) findViewById(R.id.apptTypeSpinner);
        typeID = typeSpinner.getSelectedItemPosition()+1;
        apptType = typeSpinner.getSelectedItem().toString();

        Bundle args = new Bundle();
        args.putInt("typeID", typeID);

        // Alert the user that he/she requires a pre-requisite for MRI Ear Check
        if(typeID == 2)
            new AlertDialogManager().showAlertDialog(this, "Pre-requisite Required", "You require a preliminary ear check up for creation of this appointment.", null);
        Fragment fragment2 = new CreateAppointmentFragment2();
        fragment2.setArguments(args);

        FragmentTransaction fTrans2 = fragmentManager.beginTransaction();
        fTrans2.replace(R.id.createApptSpace, fragment2);
        fTrans2.addToBackStack(null);
        fTrans2.commit();

        String accountToken = session.getUserToken();

        // Creates a JSON object to interact with php & database
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("ID", typeID);
        } catch (Exception e) {

        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server
                Globals.pdia1 = new ProgressDialog(CreateAppointmentActivity.this);
                Globals.pdia1.setMessage("Obtaining Available Clinics..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                // Call async method to receive data from server
                getWebSvcClinicsAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("getApptClinics.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void getWebSvcClinicsAsyncReturn(String webResponse){

        String clinicItem;
        int clinicID;

        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            // If any error occurs returns to selecting appointment type
            if(jsonobj.getInt("errorCode")!=0){
               Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                Globals.pdia1.dismiss();
                fragmentManager.popBackStack();
               return;
            }

            // Start with a empty list
            if(!clinicArrayList.isEmpty())
                clinicArrayList.clear();

            //Set spinner content with return results
            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){
                JSONObject intObj = jArray.getJSONObject(i);

                clinicID = intObj.getInt("ID");
                clinicItem = intObj.getString("Name");

                //Populate list
                clinicIDPair.put(clinicItem, clinicID);
                clinicArrayList.add(clinicItem);
            }

            // Set a reference to the available clinics
            Globals.clinicsInSpinner = clinicArrayList.toArray(new String[clinicArrayList.size()]);

            // Populate Spinner
            clinicSpinner = (Spinner) findViewById(R.id.clinicSelection);
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_layout, Globals.clinicsInSpinner);
            clinicSpinner.setAdapter(customAdapter);
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
            Globals.pdia1.dismiss();
            fragmentManager.popBackStack();
        }
    }

    // Proceed to find the available time slot for specific clinic appointment type and date
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

        String accountToken = session.getUserToken();
        // Creates a JSON object to interact with php & database
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("ApptSubcategoryID" , typeID);
            obj.put("ClinicID" , (clinicIDPair.get(clinic)));
            obj.put("Date" , new CustomStringConverter().convertDate(date));
        } catch (Exception e) {

        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server
                Globals.pdia1 = new ProgressDialog(CreateAppointmentActivity.this);
                Globals.pdia1.setMessage("Obtaining Available Time Slots..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                // Call async method to receive data from server
                getWebSvcTimeslotsAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("getApptTimeSlots.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void getWebSvcTimeslotsAsyncReturn(String webResponse){

        String time;

        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            // If any error occurs returns to selecting appointment type
            if(jsonobj.getInt("errorCode")!=0){
                Globals.pdia1.dismiss();
                new AlertDialogManager().showAlertDialog(this, "Error in Obtaining Time Slots", jsonobj.getString("errorMsg"), false);
                fragmentManager.popBackStack();
                return;
            }
            if (!timeSlotArrayList.isEmpty())
                timeSlotArrayList.clear();

            //Set spinner content with return results
            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){
                JSONObject intObj = jArray.getJSONObject(i);

                time = intObj.getString("timeslot");

                //Populate list
                timeSlotArrayList.add(new CustomStringConverter().convertTimeForSpinner(time));
            }

            timeSlotsForSpinner = timeSlotArrayList.toArray(new String[timeSlotArrayList.size()]);

            //Populate Spinner
            timeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_layout, timeSlotsForSpinner);
            timeSpinner.setAdapter(customAdapter);
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
            fragmentManager.popBackStack();
            Globals.pdia1.dismiss();
        }
    }

    // Method to create appointment into database
    public void createAppointment(View view) {

        String dateTimeObject, selectedDate, selectedTime;
        TextView selectedDateTV = (TextView) findViewById(R.id.dateSelected);
        Spinner selectedTimeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);
        selectedDate = selectedDateTV.getText().toString();
        selectedTime = selectedTimeSpinner.getSelectedItem().toString();

        dateTimeObject = new CustomStringConverter().convertDateAndTime(selectedDate, selectedTime);

        CheckBox referralCheckBox = (CheckBox) findViewById(R.id.referralCheckBox);
        int referralValue = (referralCheckBox.isChecked() ? 1:0);

        String accountToken = session.getUserToken();
        // Creates a JSON object to interact with php & database
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("clinicID", ((clinicIDPair.get(clinic))));
            obj.put("apptSubcategoryID", typeID);
            obj.put("dateTime", dateTimeObject);
            obj.put("isReferral", referralValue);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                // Prevent user from interacting with the app while communicating to server
                super.onPreExecute();
                Globals.pdia1 = new ProgressDialog(CreateAppointmentActivity.this);
                Globals.pdia1.setMessage("Creating appointment..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                // Call async method to receive data from server
                createAppointmentAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("createAppt.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void createAppointmentAsyncReturn(String webResponse){
        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            // No errors in creating appointment
            if(jsonobj.getInt("errorCode")==0) {
                // Alert the user that the appointment has been created
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Appointment Created");
                alertDialog.setMessage("Your appointment has been created!");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent completedCreationIntent = new Intent(getApplicationContext(), ViewAppointmentActivity.class);
                        startActivity(completedCreationIntent);
                        dialog.cancel();
                        finish();
                    }
                });
                alertDialog.setCancelable(false);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
            else {
                Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
            }
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
            Globals.pdia1.dismiss();
        }
    }

    public void btnPrev(View view) {
        fragmentManager.popBackStackImmediate();
    }

    // Returns to View Profile if in Edit Profile or Change Password
    // If already in view profile, returns to view appointments
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0){
            Intent home = new Intent(this, ViewAppointmentActivity.class);
            startActivity(home);
            finish();
        }
        else
            fragmentManager.popBackStack();
    }


}