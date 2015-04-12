package com.example.youngyeehomies.hssapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAppointmentActivity extends DrawerActivity {

    SessionManager session;
    RecyclerView rv;

    @Override
    protected void onResume() {
        super.onResume();
        Globals.drawerPosition = 1;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        set();
        Globals.drawerPosition = 1;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_appointment_layout);
        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        List<AppointmentListItem> AppointmentList = new ArrayList<>();
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentListAdapter adapter = new AppointmentListAdapter(AppointmentList);
        //rv.setAdapter(adapter);

        session = new SessionManager(getApplicationContext());

        getAppointments(session.getUserToken());

    }

    public void getAppointments(String accountToken){
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                JSONObject jsonobj = (JSONObject)o;
                getAppointmentsAysncReturn(jsonobj);
            }
        };
        svc.setServiceLink("viewAppointments.php");
        svc.execute(obj.toString());
    }

    public void getAppointmentsAysncReturn(JSONObject jsonobj){
        List<AppointmentListItem> AppointmentList = new ArrayList<>();

        //dummy
        //AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));

        try{
            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){

                JSONObject intO = jArray.getJSONObject(i);

                //datetime formatter
                String dateTime = intO.getString("DateTime");
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date date = dateParser.parse(dateTime);

                String day = new SimpleDateFormat("dd MMM yyyy").format(date);
                String time = new SimpleDateFormat("hh:mm a").format(date);

                //stores symbol id
                int symbolInt = R.drawable.gastro_ic;

                AppointmentList.add(new AppointmentListItem(
                        symbolInt,
                        intO.getString("Appointment Subcategory"),
                        day,
                        time,
                        intO.getString("Instructions")
                ));
            }
        } catch (Exception e){

        }

        AppointmentListAdapter adapter = new AppointmentListAdapter(AppointmentList);
        rv.setAdapter(adapter);

    }

}