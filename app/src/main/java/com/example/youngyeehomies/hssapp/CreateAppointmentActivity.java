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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


public class CreateAppointmentActivity extends DrawerActivity {

    SessionManager session;

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

        dateTimeObject = new DateTimeConverter().convertDateAndTime(selectedDate, selectedTime);
        
        TextView test = (TextView) findViewById(R.id.textView33);
        test.setText(dateTimeObject);

        Intent completedCreationIntent = new Intent(this, ViewAppointmentActivity.class);

        //Declare and trigger web serice
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            //YIYONG PUT YOUR ITEMS HEREEEEE
            obj.put("accountToken", accountToken);
            obj.put("clinicID", );
            obj.put("apptSubcategoryID", );
            obj.put("dateTime", );
            obj.put("isReferral", );
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                JSONObject jsonobj = (JSONObject)o;
                createAppointmentAsyncReturn(jsonobj);
            }
        };
        svc.setServiceLink("createAppt.php");
        svc.execute(obj.toString());


    }

    public void createAppointmentAsyncReturn(JSONObject jsonobj){
        try{
            if(jsonobj.getInt("errorCode")==0) {
                Toast.makeText(CreateAppointmentActivity.this, "Appointment has been created! A reminder notification will be sent one day before the day of the appointment!", Toast.LENGTH_LONG).show();
                //startActivity(completedCreationIntent);
                //finish();
            }
            else {
                Toast.makeText(CreateAppointmentActivity.this, "Appointment Creation Failed.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){

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

        //Web Service to retrieve Clinics
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            //YIYONG PUT YOUR ITEMS HEREEEEE
            obj.put("accountToken", accountToken);
            obj.put("ID", );
            //this id is the apptsubcategoryid
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                JSONObject jsonobj = (JSONObject)o;
                createAppointmentAsyncReturn(jsonobj);
            }
        };
        svc.setServiceLink("createAppt.php");
        svc.execute(obj.toString());
    }

    public void getWebSvcClinicsAsyncReturn(JSONObject jsonobj){
        try{
            if(jsonobj.getInt("errorCode")!=0){
               Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
               return;
            }

            //Set spinner content with return results
            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){
                JSONObject intObj = jArray.getJSONObject(i);

                intObj.getString("ID");
                intObj.getString("Name");
                //Populate list
            }

            //set spinner contents

        } catch (Exception e){

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
            obj.put("ApptSubcategoryID" , );
            obj.put("ClinicID" , );
            obj.put("Date" , );
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                JSONObject jsonobj = (JSONObject)o;
                createAppointmentAsyncReturn(jsonobj);
            }
        };
        svc.setServiceLink("createAppt.php");
        svc.execute(obj.toString());
    }

    public void getWebSvcTimeslotsAsyncReturn(JSONObject jsonobj){
        try{
            if(jsonobj.getInt("errorCode")!=0){
                Toast.makeText(CreateAppointmentActivity.this, jsonobj.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                return;
            }

            //Set spinner content with return results
            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){
                JSONObject intObj = jArray.getJSONObject(i);

                intObj.getString("timeslot");
                //Populate list
            }

            //set spinner contents

        } catch (Exception e){

        }
    }

    public void btnPrev(View view) {
        fragmentManager.popBackStackImmediate();
    }
}