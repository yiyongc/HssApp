package com.example.youngyeehomies.hssapp.Boundary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.SessionManager;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.R;

import java.util.HashMap;

/*
** This is the Drawer Activity which allows the entire app to implement a side drawer menu
*
* Created by Elton Quek.
*
 */

public class DrawerActivity extends ActionBarActivity {

    ListView mDrawerList;
    DrawerLayout mDrawerLayout;
    LinearLayout linearDrawer;
    ActionBarDrawerToggle mDrawerToggle;
    String mTitle = "";

    TextView sessionTextView;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void set() {

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.drawerList);
        linearDrawer = (LinearLayout) findViewById(R.id.leftDrawer);
        sessionTextView = (TextView) findViewById(R.id.session_user);

        mDrawerList.setItemChecked(1, true);
        mDrawerList.setSelection(1);

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);


        sessionTextView.setText(Html.fromHtml("Hello <b>" + name + "</b>!"));

        mDrawerList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String[] drawerItems = getResources().getStringArray(R.array.left_drawer);

                        mTitle = drawerItems[position];

                        MenuFragment mFragment = new MenuFragment();

                        Bundle data = new Bundle();

                        data.putInt("position", position);

                        mFragment.setArguments(data);

                        if(!mDrawerList.isItemChecked(Globals.drawerPosition)) {
                            selectDrawerItem(position);
                            mDrawerLayout.closeDrawer(linearDrawer);
                        }
                    }
                });


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.left_drawer));
        mDrawerList.setAdapter(adapter);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerList.bringToFront();
                getSupportActionBar().setTitle("Select Option");
                invalidateOptionsMenu();
            }
        };
    }

    // This method decides which activity to launch according to the position of drawer clicked
    private void selectDrawerItem(int position) {

        switch (position) {
            case 0:
                Intent createApptIntent = new Intent(this,CreateAppointmentActivity.class);
                startActivity(createApptIntent);
                finish();
                break;
            case 1:
                Intent viewApptIntent = new Intent(this,ViewAppointmentActivity.class);
                startActivity(viewApptIntent);
                finish();
                break;
            case 2:
                Intent viewOldApptIntent = new Intent(this,ViewOldAppointmentsActivity.class);
                startActivity(viewOldApptIntent);
                finish();
                break;
            case 3:
                Intent viewProfileIntent = new Intent(this,ViewProfileActivity.class);
                startActivity(viewProfileIntent);
                break;
            case 4:
                // Creates a dialog to get confirmation from user
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
                confirmDialog.setMessage("Do you wish to log out?");
                confirmDialog.setCancelable(false);
                confirmDialog.setTitle("Confirm Log Out?");
                confirmDialog.setIcon(R.drawable.warning);
                confirmDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                confirmDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        session.logoutUser();
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                confirmDialog.create().show();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(linearDrawer);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
