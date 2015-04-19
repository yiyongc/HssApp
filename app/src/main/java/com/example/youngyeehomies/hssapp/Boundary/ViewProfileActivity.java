package com.example.youngyeehomies.hssapp.Boundary;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.AlertDialogManager;
import com.example.youngyeehomies.hssapp.Control.SessionManager;
import com.example.youngyeehomies.hssapp.Control.WebServiceClass;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.Entity.SecurePreferences;
import com.example.youngyeehomies.hssapp.R;

import org.json.JSONObject;

/*
** This is the activity which incorporates all the fragments related to the user's profile
** It includes, viewing, editing and changing of password
 */

public class ViewProfileActivity extends DrawerActivity {

    SessionManager session;
    FragmentTransaction fTrans;
    FragmentManager fragmentManager;
    EditText currentPasswordBox, newPasswordBox, newPasswordBox2;
    TextView title;
    String firstname, lastname, fullname;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_layout);
        set();
        Globals.drawerPosition = 3;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);

        session = new SessionManager(this);

        // This is the title which will be changed by the different fragment calls
        title = (TextView) findViewById(R.id.viewProfileTitle);

        args = new Bundle();
        getUserDetails();

        fragmentManager = getFragmentManager();
        fTrans = fragmentManager.beginTransaction();

    }


    public void getUserDetails(){

        String accountToken = session.getUserToken();

        // Creates a JSON object to interact with php & database
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {
            Log.e("HSS", "problem in view profile");
        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server.
                Globals.pdia1 = new ProgressDialog(ViewProfileActivity.this);
                Globals.pdia1.setMessage("Obtaining user profile..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                // Call async method to receive data from server
                getUserDetailsAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("viewProfile.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void getUserDetailsAsyncReturn(String webResponse){
        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);

            // Obtain first and last name and combine them
            firstname = jsonobj.getString("First Name");
            lastname = jsonobj.getString("Last Name");
            fullname = firstname +" "+ lastname;

            // Put details into arguments to be passed
            args.putString("name", fullname);
            args.putString("address", jsonobj.getString("Address"));
            args.putString("email", jsonobj.getString("Email"));
            args.putString("phone", jsonobj.getString("HPno"));
            args.putInt("notifypush", jsonobj.getInt("notifySMS"));
            args.putInt("notifyemail", jsonobj.getInt("notifyEmail"));

            // Set arguments into the View Profile Fragment before calling
            Fragment fragment1 = new ViewProfileFragment();
            fragment1.setArguments(args);
            fTrans.add(R.id.editProfileSpace, fragment1);
            fTrans.commit();

            Globals.pdia1.dismiss();
        } catch (Exception e){
            Globals.pdia1.dismiss();
            Toast.makeText(ViewProfileActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
            finish();
        }
    }

    // Return to previous activity
    public void btnReturn(View view) {
        finish();
    }

    // Method to call the Change Password Fragment
    public void toChangePassword(View view) {

        Fragment fragment = new ChangePasswordFragment();
        FragmentTransaction fTrans3 = fragmentManager.beginTransaction();
        fTrans3.replace(R.id.editProfileSpace, fragment);
        fTrans3.addToBackStack(null);
        fTrans3.commit();
        title.setText("Change Password");

    }

    // Initiates the updating of password process when button is pressed
    public void btnUpdatePassword(View view) {

        currentPasswordBox = (EditText) findViewById(R.id.change_current);
        newPasswordBox = (EditText) findViewById(R.id.change_new);
        newPasswordBox2 = (EditText) findViewById(R.id.change_new2);

        final String currentPassword = currentPasswordBox.getText()+"";
        final String newPassword = newPasswordBox.getText()+"";
        final String newPassword2 = newPasswordBox2.getText()+"";

        // Some pre-checking before allowing password changes
        if (currentPassword.length() == 0 || newPassword.length() == 0 || newPassword2.length() == 0){
            Toast.makeText(ViewProfileActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 8 || newPassword2.length() < 8 || currentPassword.length() < 8) {
            Toast.makeText(ViewProfileActivity.this, "Password must contain a minimum of 8 characters!", Toast.LENGTH_SHORT).show();
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


        // Creates a dialog to get confirmation from user
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage("Are you sure you wish to change your password?");
        confirmDialog.setCancelable(false);
        confirmDialog.setTitle("Confirm Password Change");
        confirmDialog.setIcon(R.drawable.warning);
        confirmDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirmDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                // Creates a JSON object to interact with php & database
                JSONObject obj = new JSONObject();
                try {
                    obj.put("accountToken", session.getUserToken());
                    obj.put("mode", "2");
                    obj.put("Password", newPassword);
                    obj.put("Password2", newPassword2);
                    obj.put("PasswordOld", currentPassword);
                } catch (Exception e) {

                }

                // Initiate web service
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
                        // Call async method to receive data from server
                        btnUpdatePasswordReturn(o.toString());
                    }
                };
                svc.setServiceLink("editProfile.php");
                svc.execute(obj.toString());

            }
        });

        confirmDialog.create().show();

    }

    // Async method to receive data from server
    public void btnUpdatePasswordReturn(String webResponse) {

        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            // No errors in changing password
            if(jsonobj.getInt("errorCode")==0){
                Globals.pdia2.dismiss();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Password Changed");
                alertDialog.setMessage("Your password has been updated! Please login with your new password.");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        session.logoutUser();
                        SecurePreferences preferences = new SecurePreferences(ViewProfileActivity.this, "user-info",  "youngyeehomies", true);
                        preferences.removeValue("password");
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
            Log.e("Web Service Error",webResponse);
        }

    }

    // Initiates the updating of profile process when button is pressed
    public void btnUpdateProfile(View view) {
        EditText newEmail, newAddress, newPhone;
        CheckBox newPushNotif, newEmailNotif;
        String theEmail, theAddress, thePhone, thePushNotif, theEmailNotif;

        newEmail = (EditText) findViewById(R.id.editTextEmail);
        newAddress = (EditText) findViewById(R.id.editTextAddress);
        newPhone = (EditText) findViewById(R.id.editTextPhone);
        newPushNotif = (CheckBox) findViewById(R.id.editPushCheckBox);
        newEmailNotif = (CheckBox) findViewById(R.id.editEmailCheckBox);

        // Gets the values of the new inputs to be entered into server
        theEmail = newEmail.getText().toString();
        theAddress = newAddress.getText().toString();
        thePhone = newPhone.getText().toString();
        thePushNotif = ((newPushNotif.isChecked())? "1":"0");
        theEmailNotif = ((newEmailNotif.isChecked())? "1":"0");

        // Field checking
        if (thePhone.length() == 0 || theAddress.length() == 0) {
            Toast.makeText(ViewProfileActivity.this, "Phone and Address cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (theEmail.length() == 0 && theEmailNotif.equals("1")) {
            Toast.makeText(ViewProfileActivity.this, "Email is blank! Email Notifications can not be set!", Toast.LENGTH_SHORT).show();
            newEmailNotif.setChecked(false);
            return;
        }

        // Creates a JSON object to interact with php & database
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

        // Initiate web service
        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server
                Globals.pdia2 = new ProgressDialog(ViewProfileActivity.this);
                Globals.pdia2.setMessage("Processing update..");
                Globals.pdia2.show();
                Globals.pdia2.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o){
                // Call async method to receive data from server
                btnUpdateProfileReturn(o.toString());
            }
        };
        svc.setServiceLink("editProfile.php");
        svc.execute(obj.toString());
    }


    // Async method to receive data from server
    public void btnUpdateProfileReturn(String webResponse) {
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            // No errors in updating profile
            if(jsonobj.getInt("errorCode")==0){
                Toast.makeText(ViewProfileActivity.this, "Profile details are updated!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            Globals.pdia2.dismiss();
        } catch (Exception e){
            Globals.pdia2.dismiss();
            new AlertDialogManager().showAlertDialog(ViewProfileActivity.this, "Update Failed", "Failed to update profile.", false);
            Log.e("Web Service Error",webResponse);
        }
    }

    // Returns to View Profile if in Edit Profile or Change Password
    // If already in view profile, returns to view appointments
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
}
