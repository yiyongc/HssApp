package com.example.youngyeehomies.hssapp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class EditProfileFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.edit_profile_fragment_layout, container, false);

        TextView title = (TextView) getActivity().findViewById(R.id.viewProfileTitle);
        title.setText("Edit Profile");

        Log.e("tagyo", getArguments().toString());
        TextView phone = (TextView) v.findViewById(R.id.editTextPhone);
        TextView address = (TextView) v.findViewById(R.id.editTextAddress);
        TextView email = (TextView) v.findViewById(R.id.editTextEmail);
        CheckBox pushNotif = (CheckBox) v.findViewById(R.id.editPushCheckBox);
        CheckBox emailNotif = (CheckBox) v.findViewById(R.id.editEmailCheckBox);

        email.setText(getArguments().getString("email"));
        phone.setText(getArguments().getString("phone"));
        address.setText(getArguments().getString("address"));

        if(getArguments().getInt("notifypush") == 1) pushNotif.setChecked(true);
        if(getArguments().getInt("notifyemail") == 1) emailNotif.setChecked(true);

        return v;
    }




}