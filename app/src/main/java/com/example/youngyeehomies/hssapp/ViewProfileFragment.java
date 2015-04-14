package com.example.youngyeehomies.hssapp;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewProfileFragment extends Fragment {

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


        //Set the details
        name.setText("Elton");
        email.setText("myEmail");
        phone.setText("123333");
        address.setText("Pulau NTU");
        notifyemail.setChecked(true);
        notifypush.setChecked(true);

        return v;
    }



}