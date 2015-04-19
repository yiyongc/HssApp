package com.example.youngyeehomies.hssapp.Boundary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.RegistrationManager;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.R;

import org.json.JSONObject;

/*
** This activity allows the user to register/activate his account with the token received
** from the clinic on his/her first visit
*
* Created by Tan Jun Qiu.
*
 */
public class RegisterActivity extends Activity {

    EditText usernameBox, passwordBox, passwordBox2, tokenBox;
    CheckBox email, push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        usernameBox = (EditText) findViewById(R.id.reg_nric);
        passwordBox = (EditText) findViewById(R.id.reg_password);
        passwordBox2 = (EditText) findViewById(R.id.reg_password2);
        tokenBox = (EditText) findViewById(R.id.reg_token);

        push = (CheckBox) findViewById(R.id.pushCheckBox);
        // By default allow users to receive push notifications
        push.setChecked(true);
        push.setEnabled(false);
        email = (CheckBox) findViewById(R.id.emailCheckBox);
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

    // Method called when button is pressed to register
    public void onSendRegData(View view) {

        String username = usernameBox.getText()+"";
        String password = passwordBox.getText()+"";
        String password2 = passwordBox2.getText()+"";
        String token = tokenBox.getText()+"";

        // Pre-checking before allowing registration
        if (username.length() == 0 || password.length() == 0 || password2.length() == 0 || token.length() == 0) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8 || password2.length() < 8) {
            Toast.makeText(RegisterActivity.this, "Password must contain a minimum of 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(password2)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        RegistrationManager regManager = new RegistrationManager(this);
        regManager.tryRegister(username, password, password2, token);

    }

    // Async method to receive data from server
    public void onSendRegDataAsyncReturn(String webResponse){
        try{
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            // No errors on registering account
            if(jsonobj.getInt("errorCode")==0) {
                // Lets user know that registration succeeded
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Registration Success!");
                alertDialog.setMessage("Please proceed to login.");
                alertDialog.setIcon(R.drawable.success);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
                alertDialog.setCancelable(false);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
            else {
                Toast.makeText(this, "Registration/Activation Failed.", Toast.LENGTH_SHORT).show();
            }
            Globals.pdia1.dismiss();
        } catch (Exception e){
            Globals.pdia1.dismiss();
            Toast.makeText(RegisterActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error", webResponse);
        }
    }


    public void btnReturn(View view) {
        finish();
    }


    //Tap screen to close keyboard
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
