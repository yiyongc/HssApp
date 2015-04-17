package com.example.youngyeehomies.hssapp.Control;

import com.example.youngyeehomies.hssapp.Boundary.LoginActivity;

import org.json.JSONObject;

/*
** This is the login logic to communicate with database when logging in
 */

public class LoginManager {

    private LoginActivity ref;

    public LoginManager(LoginActivity ref){
        this.ref = ref;
    }

    public boolean tryLogin(String nric, String password) {

        // Create a JSON object to communicate with php logic
        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", password);
        } catch (Exception e) {

        }

        // Initiate web service
        WebServiceClass svc = new WebServiceClass(){
            @Override
            protected void onPostExecute(Object o){
                //To Override
                ref.btnLoginReturn(o.toString());
            }


        };
        svc.setServiceLink("authenticateAccount.php");
        svc.execute(obj.toString());

        return true;
    }

}


