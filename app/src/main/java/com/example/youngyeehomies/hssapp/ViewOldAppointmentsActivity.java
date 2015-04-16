package com.example.youngyeehomies.hssapp;


import android.app.ProgressDialog;
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
import com.example.youngyeehomies.hssapp.Entities.AppointmentListItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewOldAppointmentsActivity extends DrawerActivity implements AppointmentListAdapter.OnItemClickListener {

    SessionManager session;
    RecyclerView rv;
    View clickedItem;
    List<AppointmentListItem> appointmentList;

    @Override
    protected void onResume() {
        super.onResume();
        Globals.drawerPosition = 2;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
        if (clickedItem != null)
            clickedItem.setEnabled(true);

        Globals.pdia1.show();
        Log.e("tagyo", "fourth");

        getAppointments();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        set();
        Globals.drawerPosition = 2;
        mDrawerList.setItemChecked(Globals.drawerPosition, true);
        mDrawerList.setSelection(Globals.drawerPosition);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_appointment_layout);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        //  Resources res = getResources();
        //  TypedArray icons = res.obtainTypedArray(R.array.cat_icons);
        //  Drawable catIcon = icons.getDrawable(3);

        //  appointmentList = new ArrayList<>();
        Globals.pdia1 = new ProgressDialog(ViewOldAppointmentsActivity.this);
        Globals.pdia1.setMessage("Obtaining appointment history..");
        Globals.pdia1.show();
        Globals.pdia1.setCancelable(false);
        Log.e("tagyo", "third");

        session = new SessionManager(getApplicationContext());

        getAppointments();

    }

    public void getAppointments() {
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPostExecute(Object o) {
                //To Override
                getAppointmentsAysncReturn(o.toString());
            }
        };
        svc.setServiceLink("viewAppts.php");
        svc.execute(obj.toString());
    }


    public void getAppointmentsAysncReturn(String webResponse) {
        appointmentList = new ArrayList<>();

        try {
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


                    if (date.compareTo(nowDate) < 0) { //date is before currentDate
                        appointmentList.add(new AppointmentListItem(
                                catIcon,
                                intO.getString("Appointment Subcategory"),
                                day,
                                time,
                                intO.getString("Instructions"),
                                intO.getInt("ID")
                        ));
                    }
                }

            if (appointmentList.size() == 0) {
                AppointmentListAdapter adapter = new AppointmentListAdapter(appointmentList);
                Drawable catIcon = icons.getDrawable(5);
                appointmentList.add(new AppointmentListItem(catIcon, "No appointments available", "", "", "You have no appointments.", 0));
                rv.setAdapter(adapter);

            } else {
                AppointmentListAdapter adapter;
                adapter = new AppointmentListAdapter(appointmentList);
                adapter.SetOnItemClickListener(this);
                rv.setAdapter(adapter);

            }
            Globals.pdia1.dismiss();
            Log.e("tagyo", "dismiss2");
        } catch (Exception e) {
            Globals.pdia1.dismiss();
            Toast.makeText(ViewOldAppointmentsActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error", webResponse);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        clickedItem = view;
        view.setEnabled(false);
        Intent viewOldDetailsIntent = new Intent(ViewOldAppointmentsActivity.this, ViewAppointmentDetailsActivity.class);
        viewOldDetailsIntent.putExtra("AppointmentID", appointmentList.get(position).getApptID());
        viewOldDetailsIntent.putExtra("isUpcoming", false);

        startActivity(viewOldDetailsIntent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }

}
