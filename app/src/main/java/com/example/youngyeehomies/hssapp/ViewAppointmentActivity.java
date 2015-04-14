package com.example.youngyeehomies.hssapp;


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewAppointmentActivity extends DrawerActivity implements AppointmentListAdapter.OnItemClickListener{

    SessionManager session;
    RecyclerView rv;
    View clickedItem;
    List<AppointmentListItem> AppointmentList;

    @Override
    protected void onResume() {
        super.onResume();
        Globals.drawerPosition = 1;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
        if (clickedItem!=null)
         clickedItem.setEnabled(true);
        getAppointments();
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

        Resources res = getResources();
        TypedArray icons = res.obtainTypedArray(R.array.cat_icons);
        Drawable catIcon = icons.getDrawable(3);

        AppointmentList = new ArrayList<>();
        //AppointmentList.add(new AppointmentListItem(catIcon,"Dental clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentListAdapter adapter = new AppointmentListAdapter(AppointmentList);
        adapter.SetOnItemClickListener(this);
        rv.setAdapter(adapter);

        session = new SessionManager(getApplicationContext());

        getAppointments();

    }

    public void getAppointments(){
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(String webResponse){
                //To Override
                getAppointmentsAysncReturn(webResponse);
            }
        };
        svc.setServiceLink("viewAppts.php");
        svc.execute(obj.toString());
    }

    public void getAppointmentsAysncReturn(String webResponse){
        AppointmentList = new ArrayList<>();

        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Resources res = getResources();
            TypedArray icons = res.obtainTypedArray(R.array.cat_icons);

            JSONArray jArray = jsonobj.getJSONArray("list");
            for(int i=0;i<jArray.length();i++){
                JSONObject intO = jArray.getJSONObject(i);

                //datetime formatter
                String dateTime = intO.getString("DateTime");
                Date date = dateParser.parse(dateTime);

                String day = new SimpleDateFormat("dd MMM yyyy").format(date);
                String time = new SimpleDateFormat("hh:mm a").format(date);

                Drawable catIcon = icons.getDrawable(intO.getInt("Category ID") - 20);

                AppointmentList.add(new AppointmentListItem(
                        catIcon,
                        intO.getString("Appointment Subcategory"),
                        day,
                        time,
                        intO.getString("Instructions"),
                        intO.getInt("ID")
                ));
            }
        } catch (Exception e){
            Toast.makeText(ViewAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }

        AppointmentListAdapter adapter = new AppointmentListAdapter(AppointmentList);
        adapter.SetOnItemClickListener(this);
        rv.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        clickedItem = view;
        view.setEnabled(false);
        //Toast.makeText(ViewAppointmentActivity.this, "You clicked Item No. " + position, Toast.LENGTH_SHORT).show();
        Intent viewDetailsIntent = new Intent(this, ViewAppointmentDetailsActivity.class);

        viewDetailsIntent.putExtra("AppointmentID", AppointmentList.get(position).getApptID()); //Replace weird integer with Appointment ID from json object.  AppointmentList.get(position).getApptID()
        startActivity(viewDetailsIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }


}