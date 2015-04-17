package com.example.youngyeehomies.hssapp.Boundary;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.youngyeehomies.hssapp.R;

/*
** This is the fragment class which allows for the user to change his password
 */

public class ChangePasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.change_password_fragment_layout, container, false);

        return v;
    }

}