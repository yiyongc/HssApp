package com.example.youngyeehomies.hssapp.Boundary;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Control.AppointmentListAdapter;
import com.example.youngyeehomies.hssapp.Control.SessionManager;
import com.example.youngyeehomies.hssapp.Control.WebServiceClass;
import com.example.youngyeehomies.hssapp.Entity.AppointmentListItem;
import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
** This activity allows the user to view his appointment history
*
* Created by Er Young Yee.
*
 */

public class ViewAppointmentActivity extends DrawerActivity implements AppointmentListAdapter.OnItemClickListener{

    SessionManager session;
    RecyclerView rv;
    View clickedItem;
    List<AppointmentListItem> appointmentList;

    @Override
    protected void onResume() {
        super.onResume();
        Globals.drawerPosition = 1;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
        if (clickedItem!=null)
         clickedItem.setEnabled(true);

        // Prevent user from interacting with the app while communicating to server
        Globals.pdia2.show();

        getAppointments();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        set();
        Globals.drawerPosition = 1;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_appointment_layout);

        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        session = new SessionManager(getApplicationContext());

        // Prevent user from interacting with the app while communicating to server
        Globals.pdia2 = new ProgressDialog(ViewAppointmentActivity.this);
        Globals.pdia2.setMessage("Obtaining upcoming appointment(s)..");
        Globals.pdia2.show();
        Globals.pdia2.setCancelable(false);

        getAppointments();

        // GCM REDIRECT
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String AppointmentID = extras.getString("AppointmentID", "none");
            Log.i("HSS", "ViewAppointment onCreate appointmentID: "+ AppointmentID);
            if(!AppointmentID.equals("none")) {
                Log.i("HSS", "ViewAppointment Redirecting to details of ID: " + AppointmentID);
                Intent viewDetailsIntent = new Intent(this, ViewAppointmentDetailsActivity.class);
                viewDetailsIntent.putExtra("AppointmentID", Integer.parseInt(AppointmentID));
                startActivity(viewDetailsIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        }
        // END GCM

    }

    // Method to get appointments from database
    public void getAppointments(){
        String accountToken = session.getUserToken();

        // Creates a JSON object to interact with php & database
        // User token is sent to receive his/her appointments
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {
            Log.e("HSS", "problem in viewappointment");
        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPostExecute(Object o) {
                // Call async method to receive data from server
                getAppointmentsAysncReturn(o.toString());
            }

        };
        svc.setServiceLink("viewAppts.php");
        svc.execute(obj.toString());
    }

    // Async method to receive data from server
    public void getAppointmentsAysncReturn(String webResponse) {
        appointmentList = new ArrayList<>();

        try {
            // Receive JSON object
            JSONObject jsonobj = new JSONObject(webResponse);
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Resources res = getResources();
            TypedArray icons = res.obtainTypedArray(R.array.cat_icons);

            JSONArray jArray = jsonobj.getJSONArray("list");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject intO = jArray.getJSONObject(i);

                //datetime formatter
                String dateTime = intO.getString("DateTime");
                Date date = dateParser.parse(dateTime);
                Date nowDate = new Date(); // gets current date

                String day = new SimpleDateFormat("dd MMM yyyy").format(date);
                String time = new SimpleDateFormat("hh:mm a").format(date);

                Drawable catIcon = icons.getDrawable(intO.getInt("Category ID") - 20);


                if (date.compareTo(nowDate) >= 0) { //date is after currentDate
                    appointmentList.add(new AppointmentListItem(
                            catIcon,
                            intO.getString("Appointment Subcategory"),
                            day,
                            time,
                            intO.getString("Instructions"),
                            intO.getInt("ID")));
                }
            }
            if (appointmentList.size() == 0) {
                AppointmentListAdapter adapter = new AppointmentListAdapter(appointmentList);
                Drawable catIcon = icons.getDrawable(5);
                appointmentList.add(new AppointmentListItem(catIcon, "No appointments available", "", "", "You have no appointments.", 0));

                rv.setAdapter(adapter);

            }
            else {
                AppointmentListAdapter adapter;
                adapter = new AppointmentListAdapter(appointmentList);
                adapter.SetOnItemClickListener(this);
                rv.setAdapter(adapter);

            }
            Globals.pdia2.dismiss();
        }
         catch (Exception e){
            Globals.pdia2.dismiss();
            Toast.makeText(ViewAppointmentActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error",webResponse);
        }

    }

    // Clicking on the card views will initiate viewing of the appointment details
    @Override
    public void onItemClick(View view, int position) {
        clickedItem = view;
        view.setEnabled(false);
        Intent viewDetailsIntent = new Intent(this, ViewAppointmentDetailsActivity.class);

        viewDetailsIntent.putExtra("AppointmentID", appointmentList.get(position).getApptID()); //Replace weird integer with Appointment ID from json object.  appointmentList.get(position).getApptID()
        viewDetailsIntent.putExtra("isUpcoming", true);
        startActivity(viewDetailsIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }

    // Since view appointments is the "Home" page, pressing back will trigger the user to log out
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
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
    }
}