package com.example.youngyeehomies.hssapp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


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

    public void createAppointment(View view) {

        String dateTimeObject, selectedDate, selectedTime;
        TextView selectedDateTV = (TextView) findViewById(R.id.dateSelected);
        Spinner selectedTimeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);
        selectedDate = selectedDateTV.getText().toString();
        selectedTime = selectedTimeSpinner.getSelectedItem().toString();

        dateTimeObject = new CustomStringConverter().convertDateAndTime(selectedDate, selectedTime);
        
        TextView test = (TextView) findViewById(R.id.textView33);
        test.setText(dateTimeObject);

        Intent completedCreationIntent = new Intent(this, ViewAppointmentActivity.class);
        CheckBox referralCheckBox = (CheckBox) findViewById(R.id.referralCheckBox);
        int referralValue = (referralCheckBox.isChecked() ? 1:0);
        //Declare and trigger web service
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            //YIYONG PUT YOUR ITEMS HEREEEEE
            obj.put("accountToken", accountToken);
            obj.put("clinicID", ((clinicIDPair.get(clinic))));
            obj.put("apptSubcategoryID", typeID);
            obj.put("dateTime", dateTimeObject);
            obj.put("isReferral", referralValue);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(String webResponse){
                //To Override
                createAppointmentAsyncReturn(webResponse);
            }
        };
        svc.setServiceLink("createAppt.php");
        svc.execute(obj.toString());


    }

    public void createAppointmentAsyncReturn(String webResponse){
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0) {
                Toast.makeText(CreateAppointmentActivity.this, "Appointment has been created! A reminder notification will be sent one day before the day of the appointment!", Toast.LENGTH_LONG).show();
                //startActivity(completedCreationIntent);
                //finish();
            }
            else {
                Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
    }


    public void btnNext1(View view) {

        typeSpinner = (Spinner) findViewById(R.id.apptTypeSpinner);
        typeID = typeSpinner.getSelectedItemPosition()+1;
        apptType = typeSpinner.getSelectedItem().toString();

        Bundle args = new Bundle();
        args.putInt("typeID", typeID);
        Fragment fragment2 = new CreateAppointmentFragment2();
        fragment2.setArguments(args);

        FragmentTransaction fTrans2 = fragmentManager.beginTransaction();
        fTrans2.replace(R.id.createApptSpace, fragment2);
        fTrans2.addToBackStack(null);
        fTrans2.commit();

        //Web Service to retrieve Clinics
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            //YIYONG PUT YOUR ITEMS HEREEEEE
            obj.put("accountToken", accountToken);
            obj.put("ID", typeID);
            //this id is the apptsubcategoryid
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(String webResponse){
                //To Override
                getWebSvcClinicsAsyncReturn(webResponse);
            }
        };
        svc.setServiceLink("getApptClinics.php");
        svc.execute(obj.toString());
    }

    public void getWebSvcClinicsAsyncReturn(String webResponse){

        String clinicItem;
        int clinicID;

        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")!=0){
               Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
               return;
            }

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

            Globals.clinicsInSpinner = clinicArrayList.toArray(new String[clinicArrayList.size()]);
            clinicSpinner = (Spinner) findViewById(R.id.clinicSelection);
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_layout, Globals.clinicsInSpinner);
            clinicSpinner.setAdapter(customAdapter);

        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
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

        //Web Service to retrieve Clinics
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            //YIYONG PUT YOUR ITEMS HEREEEEE
            obj.put("accountToken", accountToken);
            obj.put("ApptSubcategoryID" , typeID);
            obj.put("ClinicID" , (clinicIDPair.get(clinic)));
            obj.put("Date" , new CustomStringConverter().convertDate(date));
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(String webResponse){
                //To Override
                getWebSvcTimeslotsAsyncReturn(webResponse);
            }
        };
        svc.setServiceLink("getApptTimeSlots.php");
        svc.execute(obj.toString());
    }

    public void getWebSvcTimeslotsAsyncReturn(String webResponse){

        String time;

        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")!=0){
                Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
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
            //set spinner contents
            //Populate Spinner
            timeSpinner = (Spinner) findViewById(R.id.timeSlotSelection);
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_layout, timeSlotsForSpinner);
            timeSpinner.setAdapter(customAdapter);

        } catch (Exception e){
            Toast.makeText(CreateAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
    }

    public void btnPrev(View view) {
        fragmentManager.popBackStackImmediate();
    }
}