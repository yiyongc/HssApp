package com.example.youngyeehomies.hssapp.Entity;


import android.graphics.drawable.Drawable;

/*
 * This is a class to store the converted Appointment Details JSON object, to be displayed
 */

public class AppointmentDetailsItem{

    private Drawable ApptCatIcon;
    private String ApptCategoryName;
    private String ApptSubCategory;
    private String ApptClinic;
    private String ApptDate;
    private String ApptTime;
    private String ApptNote;

    public AppointmentDetailsItem(Drawable ApptCatIcon, String ApptCategoryName, String ApptSubCategory,String ApptClinic, String ApptDate, String ApptTime, String ApptNote){
        this.ApptCatIcon = ApptCatIcon;
        this.ApptCategoryName = ApptCategoryName;
        this.ApptSubCategory = ApptSubCategory;
        this.ApptClinic = ApptClinic;
        this.ApptDate = ApptDate;
        this.ApptTime = ApptTime;
        this.ApptNote = ApptNote;

    }

    public String getApptClinic() { return ApptClinic; }

    public String getApptSubCategory() {  return ApptSubCategory; }

    public Drawable getApptCatIcon() {
        return ApptCatIcon;
    }

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


