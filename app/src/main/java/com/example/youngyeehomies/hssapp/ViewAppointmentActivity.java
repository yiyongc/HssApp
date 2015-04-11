package com.example.youngyeehomies.hssapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewAppointmentActivity extends DrawerActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment_layout);
        set();



        mDrawerList.setItemChecked(1, true);
        mDrawerList.setSelection(1);


        listView = (ListView) findViewById(R.id.AllAppointmentList);


        String[] appointments = {
                "Dermatology clinic appointment",
                "Gastroenterology clinic appointment",
                "General Consultation appointment",
                "Cardiology clinic appointment",
                "Physiotherapy appointment",
                "Annual Checkup",
                "Nasal Surgery"};

        ArrayAdapter<String> apptListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, appointments);

        listView.setAdapter(apptListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //This is where the onCLick action goes.
                Toast.makeText(ViewAppointmentActivity.this, "You clicked Item No. " + i, Toast.LENGTH_SHORT).show();
            }

        });

    }

}