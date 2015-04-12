package com.example.youngyeehomies.hssapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


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
        final String nric = user.get(SessionManager.KEY_NRIC);


        sessionTextView.setText(Html.fromHtml("Hello <b>" + nric + "</b>!"));

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


    private void selectDrawerItem(int position) {

        //Fragment fragment = null;

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
                Intent editProfileIntent = new Intent(this,EditProfileActivity.class);
                startActivity(editProfileIntent);
                break;
            case 3:
                Intent viewNotificationsIntent = new Intent(this,ViewNotificationsActivity.class);
                startActivity(viewNotificationsIntent);
                finish();
                break;
            case 4:
                session.logoutUser();
                Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }

        /*if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction fTrans = fragmentManager.beginTransaction();

            fTrans.replace(R.id.mainContent, fragment);

            fTrans.commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);

        }*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(linearDrawer);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
