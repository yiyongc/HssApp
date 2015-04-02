package com.example.youngyeehomies.hssapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;


public class ViewApptsActivity extends ActionBarActivity {

    ListView listView, mDrawerList;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    String mTitle = "";

    //Temporary
    private void selectItem(int position) {
        switch(position) {
            case 1:
                Intent a = new Intent(ViewApptsActivity.this, LoginActivity.class);
                startActivity(a);
                break;
            case 2:
                Intent b = new Intent(ViewApptsActivity.this, ViewApptsActivity.class);
                startActivity(b);
                break;
            case 3:
                Intent c = new Intent(ViewApptsActivity.this, RegisterActivity.class);
                startActivity(c);
                break;
            default:
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Drawer config starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appts_layout);

        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Select Option");
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.left_drawer));

        mDrawerList.setAdapter(adapter);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] drawerItems = getResources().getStringArray(R.array.left_drawer);

                mTitle = drawerItems[position];

                //Temporary
                //selectItem(position);

                MenuFragment mFragment = new MenuFragment();

                Bundle data = new Bundle();

                data.putInt("position", position);

                mFragment.setArguments(data);

                android.app.FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fTrans = fragmentManager.beginTransaction();

                fTrans.replace(R.id.mainContent, mFragment);

                fTrans.commit();



                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        listView = (ListView) findViewById(R.id.AllAppointmentList);


        String[] appointments = { "Dermatology clinic appointment","Gastroenterology clinic appointment",
                "General Consultation appointment","Cardiology clinic appointment",
                "Physiotherapy appointment","Annual Checkup", "Nasal Surgery"};
        ArrayAdapter<String> apptListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, appointments);

        listView.setAdapter(apptListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //This is where the onCLick action goes.
                Toast.makeText(ViewApptsActivity.this, "You clicked Item No. " + i, Toast.LENGTH_SHORT).show();
            }

        });

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
