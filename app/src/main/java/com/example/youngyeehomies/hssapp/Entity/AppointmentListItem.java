package com.example.youngyeehomies.hssapp.Entity;


import android.graphics.drawable.Drawable;

 /*
 ** This is a class to store the converted Appointment JSON object, to be displayed
 */

public class AppointmentListItem {

    private Drawable ApptCatIcon;
    private String ApptCategoryName;
    private String ApptDate;
    private String ApptTime;
    private String ApptNote;
    private int ApptID;

    public AppointmentListItem(Drawable ApptSubcatID, String ApptCategoryName, String ApptDate, String ApptTime, String ApptNote, int ApptID){
        this.ApptCatIcon = ApptSubcatID;
        this.ApptCategoryName = ApptCategoryName;
        this.ApptDate = ApptDate;
        this.ApptTime = ApptTime;
        this.ApptNote = ApptNote;
        this.ApptID = ApptID;
    }

    public Drawable getApptCatIcon() { return ApptCatIcon;}

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

    public int getApptID() {
        return ApptID;
    }
}


