package com.example.youngyeehomies.hssapp;

import android.content.Context;
import android.widget.ArrayAdapter;


public class DrawerListAdapter extends ArrayAdapter<String> {

    boolean ignoreDisabled = false;
    int p;


    public DrawerListAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public DrawerListAdapter(Context context, int position) {
        super(context, position);
        p = position;
    }
    public boolean areAllItemsEnabled() {
        return ignoreDisabled;
    }

    public boolean isEnabled(int position) {
        if (areAllItemsEnabled()) {
            return true;
        }
        else if (p == position)
            return false;
        else
            return true;
    }



}
