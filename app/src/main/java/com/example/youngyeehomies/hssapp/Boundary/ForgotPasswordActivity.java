package com.example.youngyeehomies.hssapp.Boundary;

import android.app.Activity;
import android.content.Context;
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
import com.example.youngyeehomies.hssapp.R;

public class ForgotPasswordActivity extends Activity {

    EditText nricBox, passwordBox, passwordBox2, tokenBox;
    AlertDialogManager alert = new AlertDialogManager();

    //TODO FORGOTPASSWORD

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
        //Server Checking
        if (!nric.equals("S9292929Z")/*nric is not in database*/) {
            alert.showAlertDialog(ForgotPasswordActivity.this,"Reset Password Failed!", "Your NRIC is not in our database. Please register at a clinic before using the app!", false);
            return;
        }
        if (!token.equals("12345")/*check token fail*/) {
            alert.showAlertDialog(ForgotPasswordActivity.this,"Reset Password Failed!", "You have provided an incorrect security token.", false);
            return;
        }

        //Send data to server to change password
        btnReturn(view);
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
