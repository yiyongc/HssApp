package com.example.youngyeehomies.hssapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ViewProfileActivity extends DrawerActivity {

    SessionManager session;
    FragmentTransaction fTrans;
    FragmentManager fragmentManager;
    EditText currentPasswordBox, newPasswordBox, newPasswordBox2;
    TextView title;
    //TODO EDITPROFILE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_layout);
        set();
        Globals.drawerPosition = 3;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);

        session = new SessionManager(this);

        title = (TextView) findViewById(R.id.viewProfileTitle);

        fragmentManager = getFragmentManager();
        fTrans = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {
            Fragment fragment1 = new ViewProfileFragment();
            fTrans.add(R.id.editProfileSpace, fragment1);
            fTrans.commit();
        }

        currentPasswordBox = (EditText) findViewById(R.id.change_current);
        newPasswordBox = (EditText) findViewById(R.id.change_new);
        newPasswordBox2 = (EditText) findViewById(R.id.change_new2);

    }





    public void btnReturn(View view) {
        finish();
    }

    public void toEditProfile(View view) {
        Fragment fragment = new EditProfileFragment();
        FragmentTransaction fTrans2 = fragmentManager.beginTransaction();
        fTrans2.replace(R.id.editProfileSpace, fragment);
        fTrans2.addToBackStack(null);
        fTrans2.commit();
        title.setText("Edit Profile");
    }

    public void toChangePassword(View view) {
        Fragment fragment = new ChangePasswordFragment();
        FragmentTransaction fTrans3 = fragmentManager.beginTransaction();
        fTrans3.replace(R.id.editProfileSpace, fragment);
        fTrans3.addToBackStack(null);
        fTrans3.commit();
        title.setText("Change Password");
    }

    public void btnUpdatePassword(View view) {

        String currentPassword = currentPasswordBox.getText()+"";
        String newPassword = newPasswordBox.getText()+"";
        String newPassword2 = newPasswordBox2.getText()+"";

        if (currentPassword.length() == 0 || newPassword.length() == 0 || newPassword2.length() == 0){
            Toast.makeText(ViewProfileActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        if (!currentPassword.equals(password)){
            Toast.makeText(EditProfileActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
            return;
        } */
        if (!newPassword.equals(newPassword2)) {
            Toast.makeText(ViewProfileActivity.this, "New passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentPassword.equals(newPassword)) {
            Toast.makeText(ViewProfileActivity.this, "New password cannot be the same as the current password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 8) {
            Toast.makeText(ViewProfileActivity.this, "Password must contain a minimum of 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Insert into server
        Toast.makeText(ViewProfileActivity.this, "Password has been changed! Please login with your new password!", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0)
            super.onBackPressed();
        else
            fragmentManager.popBackStack();
        title.setText("View Profile");
    }
}
