package com.example.youngyeehomies.hssapp;


import android.app.ProgressDialog;

import org.json.JSONObject;

public class RegistrationManager{

    private RegisterActivity ref;

    public RegistrationManager(RegisterActivity ref){
        this.ref = ref;
    }

    public void tryRegister(String nric, String password, String password2, String token) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", password);
            obj.put("password2", password2);
            obj.put("token", token);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Globals.pdia1 = new ProgressDialog(ref);
                Globals.pdia1.setMessage("Registration in Progress..");
                Globals.pdia1.show();
                Globals.pdia1.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Object o) {
                //To Override
                ref.onSendRegDataAsyncReturn(o.toString());
            }
        };
        svc.setServiceLink("activateAccount.php");
        svc.execute(obj.toString());

    }
}
