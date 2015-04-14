package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

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
}
