package com.example.youngyeehomies.hssapp.Control;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.example.youngyeehomies.hssapp.Boundary.LoginActivity;
/*
** This class allows for checking of the sessions and maintaining connection at all times
** It stores a token which is used when interacting with the database
 */
public class SessionManager {

    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;

    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref file name
    private static final String PREF_NAME = "HSSAppPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static String KEY_TOKEN = "notLoggedIn";

    public SessionManager(Context c){
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createLoginSession(String token, String name) {
        KEY_TOKEN = token;
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    //Check login method will check user login status
    //If false it will redirect user to login page
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Toast.makeText(context, "Please Login To Proceed! Redirecting..", Toast.LENGTH_LONG).show();
            // Redirect user to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }

    }

    //Get stored session data
    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        // User's Name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        return user;
    }

    //Get stored session token
    public String getUserToken(){
        return KEY_TOKEN;
    }

    //Clear session details
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        context.startActivity(i);
    }

    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
