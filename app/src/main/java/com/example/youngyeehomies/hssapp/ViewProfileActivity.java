package com.example.youngyeehomies.hssapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ViewProfileActivity extends DrawerActivity {

    SessionManager session;
    FragmentTransaction fTrans;
    FragmentManager fragmentManager;
    EditText currentPasswordBox, newPasswordBox, newPasswordBox2;
    TextView title;
    static String address, email, firstname, phone, lastname, fullname;
    int notifyemail, notifypush;
    Bundle args;
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

        args = new Bundle();
        getUserDetails();

        fragmentManager = getFragmentManager();
        fTrans = fragmentManager.beginTransaction();

    }


    public void getUserDetails(){
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {
            Log.e("HSS", "problem in viewappointment");
        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia1 = new ProgressDialog(ViewProfileActivity.this);
                Globals.pdia1.setMessage("Obtaining user profile..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                //To Override
                getUserDetailsAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("viewProfile.php");
        svc.execute(obj.toString());
    }

    public void getUserDetailsAsyncReturn(String webResponse){
        try{
            JSONObject jsonobj = new JSONObject(webResponse);

            firstname = jsonobj.getString("First Name");
            lastname = jsonobj.getString("Last Name");
            fullname = firstname +" "+ lastname;
            Log.e("tagyo", fullname);
            args.putString("name", fullname);
            args.putString("address", jsonobj.getString("Address"));
            args.putString("email", jsonobj.getString("Email"));
            args.putString("phone", jsonobj.getString("HPno"));
            args.putInt("notifypush", jsonobj.getInt("notifySMS"));
            args.putInt("notifyemail", jsonobj.getInt("notifyEmail"));



            Fragment fragment1 = new ViewProfileFragment();
            fragment1.setArguments(args);
            fTrans.add(R.id.editProfileSpace, fragment1);
            fTrans.commit();

            Globals.pdia1.dismiss();
        } catch (Exception e){
            Globals.pdia1.dismiss();
            Toast.makeText(ViewProfileActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
    }


    public void btnReturn(View view) {
        finish();
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
        currentPasswordBox = (EditText) findViewById(R.id.change_current);
        newPasswordBox = (EditText) findViewById(R.id.change_new);
        newPasswordBox2 = (EditText) findViewById(R.id.change_new2);

        String currentPassword = currentPasswordBox.getText()+"";
        String newPassword = newPasswordBox.getText()+"";
        String newPassword2 = newPasswordBox2.getText()+"";

        if (currentPassword.length() == 0 || newPassword.length() == 0 || newPassword2.length() == 0){
            Toast.makeText(ViewProfileActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
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


        //Json
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", session.getUserToken());
            obj.put("mode", "2");
            obj.put("Password", newPassword);
            obj.put("Password2", newPassword2);
            obj.put("PasswordOld", currentPassword);
        } catch (Exception e) {

        }



        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia2 = new ProgressDialog(ViewProfileActivity.this);
                Globals.pdia2.setMessage("Processing update..");
                Globals.pdia2.show();
                Globals.pdia2.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                //To Override
                btnUpdatePasswordReturn(o.toString());
            }
        };
        svc.setServiceLink("editProfile.php");
        svc.execute(obj.toString());


    }



    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            Intent home = new Intent(this, ViewAppointmentActivity.class);
            startActivity(home);
            finish();
        }
        else
            fragmentManager.popBackStack();
        title.setText("View Profile");
    }

    public void btnUpdateProfile(View view) {
        EditText newEmail, newAddress, newPhone;
        CheckBox newPushNotif, newEmailNotif;
        String theEmail, theAddress, thePhone, thePushNotif, theEmailNotif;

        newEmail = (EditText) findViewById(R.id.editTextEmail);
        newAddress = (EditText) findViewById(R.id.editTextAddress);
        newPhone = (EditText) findViewById(R.id.editTextPhone);
        newPushNotif = (CheckBox) findViewById(R.id.editPushCheckBox);
        newEmailNotif = (CheckBox) findViewById(R.id.editEmailCheckBox);

        theEmail = newEmail.getText().toString();
        theAddress = newAddress.getText().toString();
        thePhone = newPhone.getText().toString();
        thePushNotif = ((newPushNotif.isChecked())? "1":"0");
        theEmailNotif = ((newEmailNotif.isChecked())? "1":"0");

        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", session.getUserToken());
            obj.put("mode", "1");
            obj.put("address", theAddress);
            obj.put("email", theEmail);
            obj.put("HPno", thePhone);
            obj.put("notifySMS", thePushNotif);
            obj.put("notifyEmail", theEmailNotif);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia2 = new ProgressDialog(ViewProfileActivity.this);
                Globals.pdia2.setMessage("Processing update..");
                Globals.pdia2.show();
                Globals.pdia2.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                //To Override
                btnUpdateProfileReturn(o.toString());
            }
        };
        svc.setServiceLink("editProfile.php");
        svc.execute(obj.toString());
    }

    public void btnUpdateProfileReturn(String webResponse) {
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0){
                Toast.makeText(ViewProfileActivity.this, "Profile details are updated!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            Globals.pdia2.dismiss();
        } catch (Exception e){
            Globals.pdia2.dismiss();
            new AlertDialogManager().showAlertDialog(ViewProfileActivity.this, "Update Failed", "Failed to update profile.", false);
            //Toast.makeText(ViewProfileActivity.this, "Error Updating Profile", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }
    }

    public void btnUpdatePasswordReturn(String webResponse) {

        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0){
                Globals.pdia2.dismiss();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Password Changed");
                alertDialog.setMessage("Your password has been updated! Please login with your new password.");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        session.logoutUser();
                        return;
                    }
                });
                alertDialog.setCancelable(false);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }

        } catch (Exception e){
            Globals.pdia2.dismiss();
            new AlertDialogManager().showAlertDialog(ViewProfileActivity.this, "Update Failed", "Failed to change password.", false);
            //Toast.makeText(ViewProfileActivity.this, "Error Changing Password", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }

    }
}
