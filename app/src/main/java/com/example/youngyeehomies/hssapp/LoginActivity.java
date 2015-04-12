package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private static int mCurrentSelectionIndex = 0;
    EditText usernameBox, passwordBox;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        session = new SessionManager(getApplicationContext());

        usernameBox = (EditText) findViewById(R.id.usernameBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
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

    public void btnReg(View view) {

        Intent getRegScreenIntent = new Intent(this, RegisterActivity.class);


        startActivity(getRegScreenIntent);
    }

    public void btnLogin(View view) {

        String username = usernameBox.getText()+"";
        String password = passwordBox.getText()+"";

        if (username.length() == 0 || password.length() == 0) {
            alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please enter NRIC and Password.", false);
            return;
        }
        if (username.length() < 9 || password.length() < 8) {
            alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please input valid Username/Password.", false);
            return;
        }

        LoginManager loginManager = new LoginManager();
        //loginManager.execute("url", loginManager.NETWORK_STATE_LOGIN);

        if (loginManager.verify()) {
            Intent loggedInIntent = new Intent(this, ViewAppointmentActivity.class);
            session.createLoginSession(username, password); //This should be in loginManager when logic is done
            startActivity(loggedInIntent);
            finish();
        }
        else
            alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please input valid Username/Password.", false);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
