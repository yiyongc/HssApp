package com.example.youngyeehomies.hssapp;

import android.os.Bundle;


public class EditProfileActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        set();
        //mDrawerList.setItemChecked(0, true);
        //mDrawerList.setSelection(0);
    }

}
