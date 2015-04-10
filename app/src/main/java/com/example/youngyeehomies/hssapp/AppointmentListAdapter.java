package com.example.youngyeehomies.hssapp;

import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * Created by Young Yee on 11/4/2015.
 *
 * This class allows CardViews to be populated into a ListView
 */
public class AppointmentListAdapter extends ArrayAdapter<Appointment>{


    public AppointmentListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}
