package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
        push.setChecked(true);
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

        RegistrationManager regManager = new RegistrationManager(this);
        regManager.tryRegister(username, password, password2, token);

    }

    public void onSendRegDataAsyncReturn(String webResponse){
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0) {
                Toast.makeText(RegisterActivity.this, "Account has been registered successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(this, "Activation Failed.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
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
