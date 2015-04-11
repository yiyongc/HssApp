package com.example.youngyeehomies.hssapp.Entities;



/**
 * Created by Er Young Yee on 11/4/2015.
 *
 * This is a class to store the converted Appointment JSON object, to be displayed
 */
public class AppointmentListItem {

    //probably have to add more variables?
    private int ApptSubcategoryID;
    private String ApptCategoryName;
    private String ApptDate;
    private String ApptTime;
    private String ApptNote;

    public AppointmentListItem(int ApptSubcatID, String ApptCategoryName, String ApptDate, String ApptTime, String ApptNote){
        this.ApptSubcategoryID = ApptSubcatID;
        this.ApptCategoryName = ApptCategoryName;
        this.ApptDate = ApptDate;
        this.ApptTime = ApptTime;
        this.ApptNote = ApptNote;

    }

    public int getApptSubcategoryID() {
        return ApptSubcategoryID;
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

    /* If the above code works, remove this
    private int DoctorID;
    private int PatientID;
    private int ClinicID;
    private int ApptSubcategoryID;
    private Time dateTime;
    private String noteString;
    private boolean isReferral;




    public Appointment(int docID, int patID, int clinID, int ApptSubcatID, Time apptTime,boolean isRef,String noteString){
        this.DoctorID = docID;
        this.PatientID = patID;
        this.ClinicID = clinID;
        this.ApptSubcategoryID = ApptSubcatID;
        this.dateTime = apptTime;
        this.isReferral = isRef;
    }


    public Appointment(int docID, int patID, int clinID, int ApptSubcatID, Time apptTime,boolean isRef){
        this(docID, patID, clinID, ApptSubcatID, apptTime, isRef,"");
    }

    public int getClinicID() {
        return ClinicID;
    }

    public int getApptSubcategoryID() {
        return ApptSubcategoryID;
    }

    public Time getDateTime() {
        return dateTime;
    }

    public String getNoteString() {
        return noteString;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public int getDoctorID() {

        return DoctorID;
    }
*/
}


