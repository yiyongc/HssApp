package com.example.youngyeehomies.hssapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;


public class EditProfileActivity extends DrawerActivity {

    SessionManager session;
    private LinearLayout linearLayout;
    private RadioGroup rg;
    EditText currentPasswordBox, newPasswordBox, newPasswordBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        set();
        session = new SessionManager(this);


        mDrawerList.setItemChecked(2, true);
        mDrawerList.setSelection(2);

        currentPasswordBox = (EditText) findViewById(R.id.change_current);
        newPasswordBox = (EditText) findViewById(R.id.change_new);
        newPasswordBox2 = (EditText) findViewById(R.id.change_new2);

        //Populate RadioGroup
        linearLayout = (LinearLayout) findViewById(R.id.changelinear);
        PopulateClinicManager pcm = new PopulateClinicManager(this);
        rg = pcm.addRadioGroup(linearLayout);
    }

    public void btnUpdatePassword(View view) {

        String currentPassword = currentPasswordBox.getText()+"";
        String newPassword = newPasswordBox.getText()+"";
        String newPassword2 = newPasswordBox2.getText()+"";
        HashMap<String, String> user = session.getUserDetails();
        final String password = user.get(SessionManager.KEY_PASSWORD);

        if (currentPassword.length() == 0 || newPassword.length() == 0 || newPassword2.length() == 0){
            Toast.makeText(EditProfileActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!currentPassword.equals(password)){
            Toast.makeText(EditProfileActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(newPassword2)) {
            Toast.makeText(EditProfileActivity.this, "New passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 8) {
            Toast.makeText(EditProfileActivity.this, "Password must contain a minimum of 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Insert into server
        Toast.makeText(EditProfileActivity.this, "Password has been changed! Please login with your new password!", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    public void btnUpdateClinic(View view) {
        int newDefaultClinicID = rg.getCheckedRadioButtonId();
        //update new clinic
        Toast.makeText(EditProfileActivity.this, "New default clinic updated!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void btnReturn(View view) {
        finish();
    }
}
