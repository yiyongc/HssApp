package com.example.youngyeehomies.hssapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 GCM

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
*/

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

// GCM END

public class LoginActivity extends Activity{

    EditText usernameBox, passwordBox;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button loginButton;
    SecurePreferences preferences;
    CheckBox rememberMeCheckBox;
    String username, password;
    ProgressDialog pdia;

    // GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "237685802774";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "HSS";

    //TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;

    String regid;
    int toDetails = -1;
    // GCM END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        session = new SessionManager(getApplicationContext());

        usernameBox = (EditText) findViewById(R.id.usernameBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        loginButton = (Button) findViewById(R.id.btnLogin);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);

        preferences = new SecurePreferences(this, "user-info",  "youngyeehomies", true);
        String storedusername = preferences.getString("username");
        String storedpassword = preferences.getString("password");

        if (storedusername != null && storedpassword != null) {
            usernameBox.setText(storedusername);
            passwordBox.setText(storedpassword);
            rememberMeCheckBox.setChecked(true);
        }

        // GCM REDIRECT
        toDetails = -1;
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String AppointmentID = extras.getString("AppointmentID", "none");
            Log.i("HSS", "Login onCreate appointmentID: "+ AppointmentID);
            if(!AppointmentID.equals("none")) {
                toDetails = Integer.parseInt(AppointmentID);
            }
        }
        // END GCM

        Log.e(TAG, "created login activity.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnReg(View view) {

        Intent getRegScreenIntent = new Intent(this, RegisterActivity.class);


        startActivity(getRegScreenIntent);
    }

    public void btnLogin(View view) {
        loginButton.setEnabled(false);


        pdia = new ProgressDialog(LoginActivity.this);
        pdia.setMessage("Logging In..");
        pdia.show();
        pdia.setCancelable(false);


        username = usernameBox.getText()+"";
        password = passwordBox.getText()+"";

        Log.e(TAG, "login button pressed user: " + username + " pass: " + password);

        if (username.length() == 0 || password.length() == 0) {
            alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please enter NRIC and Password.", false);
            loginButton.setEnabled(true);
            return;
        }
        if (username.length() < 9 || password.length() < 8) {
            alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please input valid Username/Password.", false);
            loginButton.setEnabled(true);
            return;
        }

        LoginManager loginManager = new LoginManager(this);


        //loginManager.execute("url", loginManager.NETWORK_STATE_LOGIN);

        //AUTO SUCCESS LOGIN; DELETE WHEN SUBMITTING
        /*
        Intent loggedInIntent = new Intent(this, ViewAppointmentActivity.class);
        session.createLoginSession("12f2956febd4b53d0e13b3d8c7d11597deb01acdb04cc02d691be55337b93b5a"); //This should be in loginManager when logic is done
        startActivity(loggedInIntent);
        finish();
        */
        //AUTO SUCCESS LOGIN; DELETE WHEN SUBMITTING

        //Attempts to login
        //UNCOMMENT WHEN SUBMITTING
        Log.e(TAG, "trying to login.");
        loginManager.tryLogin(username,password);

    }

    public void btnLoginReturn(String webResponse){

        Log.e(TAG, "btnLoginReturn callback called.");
        try{
            JSONObject obj = new JSONObject(webResponse);
            if(obj.getInt("errorCode")==0){
                Log.e(TAG, "btnLoginReturn errorCode is 0.");

                Intent loggedInIntent = new Intent(this, ViewAppointmentActivity.class);
                if(toDetails != -1) {
                    Log.i("HSS", "Login Redirecting to details of ID: " + toDetails);
                    loggedInIntent.putExtra("AppointmentID", Integer.toString(toDetails));
                }
                /*
                Intent viewAppts = new Intent(this, ViewAppointmentActivity.class);
                if(toDetails != -1) {
                    Log.i("HSS", "Redirecting to details of ID: " + toDetails);
                    loggedInIntent = new Intent(this, ViewAppointmentDetailsActivity.class);
                    loggedInIntent.putExtra("AppointmentID", toDetails);
                }
                else
                    loggedInIntent = new Intent(this, ViewAppointmentActivity.class);*/


                session.createLoginSession(obj.getString("accountToken"), obj.getString("firstName")); //This should be in loginManager when logic is done
                //session.createLoginSession("",""); //This should be in loginManager when logic is done

                if (rememberMeCheckBox.isChecked()) {
                    preferences.put("username", username);
                    preferences.put("password", password);
                }

                startActivity(loggedInIntent);
                finish();


                // GCM
                context = getApplicationContext();

                // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
                if (checkPlayServices()) {
                    gcm = GoogleCloudMessaging.getInstance(this);
                    regid = getRegistrationId(context);

                    if (regid.isEmpty()) {
                        Log.i(TAG, "Registering in background.");
                        registerInBackground(); // could pass obj.getString("accountToken")
                    } else{
                        Log.e(TAG, "==========================");
                        Log.e(TAG, "already registered regid " + regid);
                        Log.e(TAG, "==========================");
                        sendRegistrationIdToBackend();
                    }
                } else {
                    Log.i(TAG, "No valid Google Play Services APK found.");
                }
                // END GCM



            } else {
                Log.e(TAG, "btnLoginReturn login failed!");
                alert.showAlertDialog(LoginActivity.this, "Login Failed!", "Please input valid Username/Password.", false);
            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error", webResponse);
            Log.e(TAG, "btnLoginReturn exception :( e.message: " + e.getMessage());
        }
        loginButton.setEnabled(true);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    // ----------------  GCM ------------------------
    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId " + regId + " on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        Log.i(TAG, "registrationId: " + registrationId);
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    Log.e("==========================","=========================");
                    Log.e("regid",regid);
                    Log.e("==========================","=========================");

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage() + " regid: " + regid;
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.e("==========================","=========================");
                Log.e("msg",msg);
                Log.e("==========================","=========================");
            }
        }.execute(null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(LoginActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
        //Declare and trigger web service
        String accountToken = session.getUserToken();
        JSONObject obj = new JSONObject();
        try {
            obj.put("accountToken", accountToken);
            obj.put("registrationID", regid);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){

            @Override
            protected void onPostExecute(Object o){

                //To Override
                registerGCMAsyncReturn(o.toString());
                pdia.dismiss();
            }
        };
        svc.setServiceLink("registerGCM.php");
        Log.e(TAG, "sending registration query to web interface, accountToken: " + accountToken + " regid: " + regid);
        svc.execute(obj.toString());
    }

    public void registerGCMAsyncReturn(String webResponse){
        Log.e(TAG, "registerGCMAsyncReturn.");
        try{
            JSONObject jsonobj = new JSONObject(webResponse);
            if(jsonobj.getInt("errorCode")==0) {
                Log.e(TAG, "GCM registered!");
                if(jsonobj.getInt("success")==1) {
                    Toast.makeText(LoginActivity.this, "Subscribed to notifications!", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Log.e(TAG, "GCM not registered :(");
                Log.e(TAG, "errorCode: " + jsonobj.getInt("errorCode") + " errorMsg: " + jsonobj.getString("errorMsg"));
                //Toast.makeText(LoginActivity.this, "GCM not registered :(", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(LoginActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            Log.e("Web Service Error", webResponse);
            Log.e(TAG, "registerGCMAsyncReturn exception");
        }
    }
    // ----------------  END GCM      ------------------

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

}
