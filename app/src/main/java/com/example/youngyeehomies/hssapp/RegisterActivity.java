package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    //private LinearLayout linearLayout;
    //private RadioGroup rg;
    EditText usernameBox, passwordBox, passwordBox2, tokenBox;
    CheckBox email, sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        usernameBox = (EditText) findViewById(R.id.reg_nric);
        passwordBox = (EditText) findViewById(R.id.reg_password);
        passwordBox2 = (EditText) findViewById(R.id.reg_password2);
        tokenBox = (EditText) findViewById(R.id.reg_token);

        /*Populate RadioGroup
        linearLayout = (LinearLayout) findViewById(R.id.defaultClinicLayout);
        PopulateClinicManager pcm = new PopulateClinicManager(this);
        rg = pcm.addRadioGroup(linearLayout);*/
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

    public void onSendRegData(View view) {

        String username = usernameBox.getText()+"";
        String password = passwordBox.getText()+"";
        String password2 = passwordBox2.getText()+"";
        String token = tokenBox.getText()+"";
        //String defaultClinic = rg.getCheckedRadioButtonId()+"";

        //Field checking
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

        //Server checking
        /*
        if (!nric.equals("S9292929Z")nric is not in database) {
            alert.showAlertDialog(ForgotPasswordActivity.this,"Reset Password Failed!", "Your NRIC is not in our database. Please register at a clinic before using the app!", false);
            return;
        }
        if (!token.equals("12345")check token fail) {
            alert.showAlertDialog(ForgotPasswordActivity.this,"Reset Password Failed!", "You have provided an incorrect security token.", false);
            return;
        }
        */

        RegistrationManager regManager = new RegistrationManager(username, password, token);
        //regManager.execute("url", regManager.NETWORK_STATE_REGISTER);
        Intent intent = new Intent(this,LoginActivity.class);

        //Send json object
        //if accepted
        Toast.makeText(RegisterActivity.this, "Account has been registered successfully!", Toast.LENGTH_SHORT).show();
        finish();
        //else
        //Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

    }


    public void btnReturn(View view) {
        finish();
    }
}
