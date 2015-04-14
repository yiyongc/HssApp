package com.example.youngyeehomies.hssapp;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewProfileFragment extends Fragment {
    String theName, theEmail, thePhone, theAddress;
    int theNotifyEmail, theNotifyPush;
    TextView address, email, name, phone;
    CheckBox notifyemail, notifypush;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.view_profile_fragment_layout, container, false);

        notifyemail = (CheckBox) v.findViewById(R.id.emailCheckBox);
        notifyemail.setClickable(false);
        notifypush = (CheckBox) v.findViewById(R.id.pushCheckBox);
        notifypush.setClickable(false);

        name = (TextView) v.findViewById(R.id.userName);
        email = (TextView) v.findViewById(R.id.userEmail);
        phone = (TextView) v.findViewById(R.id.userPhone);
        address = (TextView) v.findViewById(R.id.userAddress);

        //Log.e("tagyo", getArguments().getString("name"));

        theName = getArguments().getString("name");
        theEmail = getArguments().getString("email");
        thePhone = getArguments().getString("phone");
        theAddress = getArguments().getString("address");
        theNotifyEmail = getArguments().getInt("notifyemail");
        theNotifyPush = getArguments().getInt("notifypush");

        //Set the details
        name.setText(theName);
        email.setText(theEmail);
        phone.setText(thePhone);
        address.setText(theAddress);
        if (theNotifyEmail == 1)
            notifyemail.setChecked(true);
        if (theNotifyPush == 1)
            notifypush.setChecked(true);

        return v;
    }



}