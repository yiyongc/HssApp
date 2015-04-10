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

    EditText usernameBox, passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

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

        if (username.length() < 8 || password.length() < 8) {
            Toast.makeText(LoginActivity.this, "Please input valid Username/Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginManager loginManager = new LoginManager();
        //loginManager.execute("url", loginManager.NETWORK_STATE_LOGIN);

        if (loginManager.verify()) {
            Intent loggedInIntent = new Intent(this, ViewAppointmentActivity.class);
            startActivity(loggedInIntent);
            finish();
        }
        else
            Toast.makeText(LoginActivity.this, "Wrong NRIC/Password", Toast.LENGTH_SHORT).show();
    }

    public void forgotPassword(View view) {
    }
}
