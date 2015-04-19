package com.example.youngyeehomies.hssapp.Boundary;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youngyeehomies.hssapp.R;

/*
** This is the fragment of the drawer menu
*
* Created by Elton Quek
*
 */

public class MenuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int position = getArguments().getInt("position");

        String[] menuItems = getResources().getStringArray(R.array.left_drawer);

        View v = inflater.inflate(R.layout.fragment_layout, container, false);

        TextView textView = (TextView) v.findViewById(R.id.textView_content);


        textView.setText(menuItems[position]);

        try {
            getActivity().getActionBar().setTitle(menuItems[position]);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return v;
    }
}
