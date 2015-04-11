package com.example.youngyeehomies.hssapp;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentActivity extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment_layout2);
        set();
        mDrawerList.setItemChecked(1, true);
        mDrawerList.setSelection(1);


        ListView allAppointmentListView = (ListView) findViewById(R.id.allAppointmentListView);

        String[] appointments = {
                "Dermatology clinic appointment",
                "Gastroenterology clinic appointment",
                "General Consultation appointment",
                "Cardiology clinic appointment",
                "Physiotherapy appointment",
                "Annual Checkup",
                "Nasal Surgery"};

        List<AppointmentListItem> AppointmentList;
        AppointmentList = new ArrayList<>();
        //This appointment list should be populated by the database. Lets assume that effectively the below code is run
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));
        AppointmentList.add(new AppointmentListItem(R.drawable.gastro_ic,"Gastroenterology clinic appointment","26 Apr 2015","1.00 PM","You are required to abstain from drinking water 12 hours before this appointment"));

        //end of appointment list data add

   //     ArrayAdapter<String> apptListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, appointments);

        AppointmentListAdapter apptListAdapter = new AppointmentListAdapter(this,android.R.layout.simple_list_item_1,AppointmentList);
        allAppointmentListView.setAdapter(apptListAdapter);
/*
        allAppointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //This is where the onCLick action goes.
                Toast.makeText(ViewAppointmentActivity.this, "You clicked Item No. " + i, Toast.LENGTH_SHORT).show();
            }

        });*/

    }

}