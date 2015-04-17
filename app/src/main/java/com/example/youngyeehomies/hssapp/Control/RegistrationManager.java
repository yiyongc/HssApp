package com.example.youngyeehomies.hssapp.Control;


import android.app.ProgressDialog;

import com.example.youngyeehomies.hssapp.Entity.Globals;
import com.example.youngyeehomies.hssapp.Boundary.RegisterActivity;

import org.json.JSONObject;

/*
** This is the registration logic to communicate with database when registering(activating) account
 */

public class RegistrationManager{

    private RegisterActivity ref;

    public RegistrationManager(RegisterActivity ref){
        this.ref = ref;
    }

    public void tryRegister(String nric, String password, String password2, String token) {

        // Create a JSON object to communicate with php and database
        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", password);
            obj.put("password2", password2);
            obj.put("token", token);
        } catch (Exception e) {

        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Prevent user from interacting with the app while communicating to server.
                Globals.pdia1 = new ProgressDialog(ref);
                Globals.pdia1.setMessage("Registration in Progress..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o) {
                // Call async method to receive data from server
                ref.onSendRegDataAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("activateAccount.php");
        svc.execute(obj.toString());

    }
}
