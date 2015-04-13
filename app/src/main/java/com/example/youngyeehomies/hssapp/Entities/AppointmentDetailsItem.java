package com.example.youngyeehomies.hssapp.Entities;



/**
 * Created by Er Young Yee on 13/4/2015.
 *
 * This is a class to store the converted Appointment Details JSON object, to be displayed
 */
public class AppointmentDetailsItem{

    private int ApptCatID;
    private String ApptCategoryName;
    private String ApptSubCategory;
    private String ApptClinic;
    private String ApptDate;
    private String ApptTime;
    private String ApptNote;

    public AppointmentDetailsItem(int ApptCatID, String ApptCategoryName, String ApptSubCategory,String ApptClinic, String ApptDate, String ApptTime, String ApptNote){
        this.ApptCatID = ApptCatID;
        this.ApptCategoryName = ApptCategoryName;
        this.ApptSubCategory = ApptSubCategory;
        this.ApptClinic = ApptClinic;
        this.ApptDate = ApptDate;
        this.ApptTime = ApptTime;
        this.ApptNote = ApptNote;

    }

    public String getApptClinic() { return ApptClinic;    }

    public String getApptSubCategory() {  return ApptSubCategory;   }
    public int getApptCatID() {
        return ApptCatID;
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


