package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private LinearLayout linearLayout;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        linearLayout = (LinearLayout) findViewById(R.id.defaultClinicLayout);

        PopulateClinicManager pcm = new PopulateClinicManager(this);

        rg = pcm.addRadioGroup(linearLayout);
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

        Intent intent = new Intent(this,LoginActivity.class);

        //Send json object
        //if accepted
        Toast.makeText(RegisterActivity.this, "Account has been registered successfully!", Toast.LENGTH_SHORT).show();

        //else
        //Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

        startActivity(intent);

    }


}
