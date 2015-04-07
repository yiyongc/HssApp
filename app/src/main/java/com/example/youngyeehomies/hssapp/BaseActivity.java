package com.example.youngyeehomies.hssapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BaseActivity extends ActionBarActivity {

    ListView mDrawerList;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    String mTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment_layout);

    }

    public void set() {

        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



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

                        selectDrawerItem(position);

                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                });


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.left_drawer));
        //DrawerListAdapter adapter = new DrawerListAdapter(getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.left_drawer));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.left_drawer));
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
                finish();
                break;
            case 3:
                Intent viewNotificationsIntent = new Intent(this,ViewNotificationsActivity.class);
                startActivity(viewNotificationsIntent);
                finish();
                break;
            case 4:
                Intent logOutIntent = new Intent(this, LoginActivity.class);
                //process log out
                startActivity(logOutIntent);
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
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
