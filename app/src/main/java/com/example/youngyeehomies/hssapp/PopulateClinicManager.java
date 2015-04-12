package com.example.youngyeehomies.hssapp;


import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PopulateClinicManager {

    String[] radioItems;
    Context context;

    private RadioGroup rg;

    public PopulateClinicManager(Context context) {
        this.context = context;
        this.radioItems = context.getResources().getStringArray(R.array.clinics);
    }

    public RadioGroup addRadioGroup(LinearLayout linearLayout) {
        //Populating and adding RadioGroup
        rg = new RadioGroup(context); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL); //layout of RadioGroup
        rg.setMinimumWidth(180);

        createRadioButton(rg);
        linearLayout.addView(rg);

        return rg;
    }

    private void createRadioButton(RadioGroup rg) {

        final RadioButton[] rb = new RadioButton[6];

        for(int i=0; i<6; i++){
            rb[i]  = new RadioButton(context);
            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup
            rb[i].setText(radioItems[i]);
            rb[i].setTextSize(14);
            rb[i].setTextColor(Color.parseColor("#325b56"));
            rb[i].setPadding(10, 0, 26, 0);
        }

        rb[0].setChecked(true);

    }
}
