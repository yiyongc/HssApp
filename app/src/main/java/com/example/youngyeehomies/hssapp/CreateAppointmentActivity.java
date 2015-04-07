package com.example.youngyeehomies.hssapp;

import android.os.Bundle;


public class CreateAppointmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment_layout);
        set();
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
    }

}