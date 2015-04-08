package com.example.youngyeehomies.hssapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ViewNotificationsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notifications_layout);
        set();
        mDrawerList.setItemChecked(3, true);
        mDrawerList.setSelection(3);
        mDrawerList.invalidate();

        String[] notifications = {
                "Reminder: Medical Checkup at North Spine",
                "Reminder: Dental Appointment at Canteen A"
        };

        ArrayAdapter<String> notificationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, notifications);

        ListView listViewNotification = (ListView) findViewById(R.id.Notifications);

        listViewNotification.setAdapter(notificationAdapter);

        listViewNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //This is where the onCLick action goes.
                Toast.makeText(ViewNotificationsActivity.this, "You clicked Notification No. " + i, Toast.LENGTH_SHORT).show();
            }

        });
    }

}