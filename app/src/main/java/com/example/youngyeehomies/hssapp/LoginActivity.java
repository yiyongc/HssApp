package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }

    public void btnReg(View view) {

        Intent getRegScreenIntent = new Intent(this, RegisterActivity.class);

        final int result = 1;

        startActivity(getRegScreenIntent);
    }

    public void btnLogin(View view) {

        Intent loggedInIntent = new Intent(this, ViewApptsActivity.class);
        Intent activateAccountIntent = new Intent(this, ActivateAccountActivity.class);
        LoginManager loginManager = new LoginManager();

        if (loginManager.verify()) {
            if (!loginManager.activated())
                startActivity(activateAccountIntent);
            else
                startActivity(loggedInIntent);
        }
        else
            Toast.makeText(LoginActivity.this, "Wrong NRIC/Password", Toast.LENGTH_SHORT).show();
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
}
