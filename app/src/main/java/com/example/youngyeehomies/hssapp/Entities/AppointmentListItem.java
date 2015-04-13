package com.example.youngyeehomies.hssapp.Entities;


import android.graphics.drawable.Drawable;

/**
 * Created by Er Young Yee on 11/4/2015.
 *
 * This is a class to store the converted Appointment JSON object, to be displayed
 */
public class AppointmentListItem {

    //probably have to add more variables?
    private Drawable ApptCatIcon;
    private String ApptCategoryName;
    private String ApptDate;
    private String ApptTime;
    private String ApptNote;

    public AppointmentListItem(Drawable ApptSubcatID, String ApptCategoryName, String ApptDate, String ApptTime, String ApptNote){
        this.ApptCatIcon = ApptSubcatID;
        this.ApptCategoryName = ApptCategoryName;
        this.ApptDate = ApptDate;
        this.ApptTime = ApptTime;
        this.ApptNote = ApptNote;

    }

    public Drawable getApptCatIcon() {      return ApptCatIcon;}

    public String getApptCategoryName() {
        return ApptCategoryName;
    }

    public String getApptDate() {
        return ApptDate;
    }

    public String getApptNote() {
        return ApptNote;
    }

    public String getApptTime() {
        return ApptTime;
    }

}


