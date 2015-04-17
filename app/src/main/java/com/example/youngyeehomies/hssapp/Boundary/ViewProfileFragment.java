package com.example.youngyeehomies.hssapp.Boundary;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.youngyeehomies.hssapp.R;

/*
** This is the fragment class which allows for the user to view his current profile.
 */

public class ViewProfileFragment extends Fragment {

    String theName, theEmail, thePhone, theAddress;
    int theNotifyEmail, theNotifyPush;
    TextView address, email, name, phone;
    CheckBox notifyemail, notifypush;
    Bundle args;
    Button toEditProfileButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_profile_fragment_layout, container, false);

        //Prevent editing in 'view' mode
        notifyemail = (CheckBox) v.findViewById(R.id.emailCheckBox);
        notifyemail.setClickable(false);
        notifypush = (CheckBox) v.findViewById(R.id.pushCheckBox);
        notifypush.setClickable(false);

        name = (TextView) v.findViewById(R.id.userName);
        email = (TextView) v.findViewById(R.id.userEmail);
        phone = (TextView) v.findViewById(R.id.userPhone);
        address = (TextView) v.findViewById(R.id.userAddress);

        // The arguments include the details of the user obtained from the database
        args = getArguments();

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

        //Set up the on click listener when user selects to Edit Profile
        toEditProfileButton = (Button) v.findViewById(R.id.btnEditProfile);

        toEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfileFragment();
                // Passes current arguments so that user need not key in all fields for editing
                fragment.setArguments(args);
                FragmentTransaction fTrans2 = getFragmentManager().beginTransaction();
                fTrans2.replace(R.id.editProfileSpace, fragment);
                fTrans2.addToBackStack(null);
                fTrans2.commit();

            }
        });

        return v;
    }

}