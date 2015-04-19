package com.example.youngyeehomies.hssapp.Control;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
** This class is the custom adapter for use with all the spinners in the app
** especially in the create appointment function. It is possible to customize
** the look of the spinner objects.
*
* Created by Chee Yi Yong.
*
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {


    public CustomSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);
        ((TextView) v).setTextSize(14);

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setGravity(Gravity.CENTER);

        return v;
    }
}

