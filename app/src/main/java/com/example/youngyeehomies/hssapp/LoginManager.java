package com.example.youngyeehomies.hssapp;

import org.json.JSONObject;

public class LoginManager {

    private LoginActivity ref;

    public LoginManager(LoginActivity ref){
        this.ref = ref;
    }

    public boolean tryLogin(String nric, String password) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", password);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                JSONObject jsonobj = (JSONObject)o;
                ref.btnLoginReturn(jsonobj);
            }
        };
        svc.setServiceLink("authenticateAccount.php");
        svc.execute(obj.toString());

        return true;
    }

}


