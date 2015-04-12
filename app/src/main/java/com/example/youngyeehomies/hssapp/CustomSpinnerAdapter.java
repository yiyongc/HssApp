package com.example.youngyeehomies.hssapp;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



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

