package com.example.youngyeehomies.hssapp.Entity;

import android.app.ProgressDialog;

/*
* Created by Cheng Gibson.
* This class stores the global variables we would need for the application. This includes the server URL.
*/

public class Globals {
    public static int drawerPosition=1;

    //replace IP with relevant server hosting web services
    public static final String serverURL = "http://youngyeeshomies.dynamic-dns.net/xampp/hsswebservice/";

    public static String[] clinicsInSpinner;

    public static ProgressDialog pdia1, pdia2;
}
