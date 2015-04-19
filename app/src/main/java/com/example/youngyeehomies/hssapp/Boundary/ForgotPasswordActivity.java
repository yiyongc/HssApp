package com.example.youngyeehomies.hssapp.Boundary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.AlertDialogManager;
import com.example.youngyeehomies.hssapp.Control.WebServiceClass;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.Entity.SecurePreferences;
import com.example.youngyeehomies.hssapp.R;

import org.json.JSONObject;

/*
** This activity allows a user to reset his password by providing the same security token
** used during registration. He may request for a new one at the nearby clinic.
*
* Created by Elton Quek
*
*
 */

public class ForgotPasswordActivity extends Activity {

    EditText nricBox, passwordBox, passwordBox2, tokenBox;
    AlertDialogManager alert = new AlertDialogManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_layout);

        nricBox = (EditText) findViewById(R.id.forgot_nric);
        passwordBox = (EditText) findViewById(R.id.forgot_password);
        passwordBox2 = (EditText) findViewById(R.id.forgot_password2);
        tokenBox = (EditText) findViewById(R.id.forgot_token);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnResetData(View view) {

        String nric = nricBox.getText()+"";
        String newPass1 = passwordBox.getText()+"";
        String newPass2 = passwordBox2.getText()+"";
        String token = tokenBox.getText()+"";

        //Field Checking
        if (nric.length() == 0 || newPass1.length() == 0 || newPass2.length() == 0 || token.length() == 0) {
            Toast.makeText(ForgotPasswordActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nric.length() != 9) {
            Toast.makeText(ForgotPasswordActivity.this, "Invalid NRIC!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPass1.length() < 8 || newPass2.length() < 8) {
            Toast.makeText(ForgotPasswordActivity.this, "Passwords must contain a minimum of 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPass1.equals(newPass2)) {
            alert.showAlertDialog(ForgotPasswordActivity.this,"Reset Password Failed!", "The new passwords do not match!", false);
            return;
        }

        // Create a JSON object to communicate with php and database
        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", newPass1);
            obj.put("password2", newPass2);
            obj.put("token", token);
        } catch (Exception e) {

        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server.
                Globals.pdia1 = new ProgressDialog(ForgotPasswordActivity.this);
                Globals.pdia1.setMessage("Attempting to reset password..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o) {
                // Call async method to receive data from server
                btnResetDataAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("resetAccountPw.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void btnResetDataAsyncReturn(String webResponse){
        try{
            Log.e("taggy", webResponse);
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);

            // No errors on resetting password for account
            if(jsonobj.getInt("errorCode")==0) {
                // Lets user know that reset succeeded
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Reset Success!");
                alertDialog.setMessage("Please proceed to login.");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //Remove any previous stored password
                        SecurePreferences preferences = new SecurePreferences(ForgotPasswordActivity.this, "user-info",  "youngyeehomies", true);
                        preferences.removeValue("password");
                        finish();
                    }
                });
                alertDialog.setCancelable(false);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
            else {
                Toast.makeText(this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
            }
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Globals.pdia1.dismiss();
            e.printStackTrace();
            Toast.makeText(ForgotPasswordActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error", webResponse);
        }
    }



    public void btnReturn(View view) {
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}
