package com.example.youngyeehomies.hssapp;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrationManager{

    private RegisterActivity ref;

    public RegistrationManager(RegisterActivity ref){
        this.ref = ref;
    }

    public void tryRegister(String nric, String password, String token) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("nric", nric);
            obj.put("password", password);
            obj.put("token", token);
        } catch (Exception e) {

        }

        WebServiceClass svc = new WebServiceClass() {
            @Override
            protected void onPostExecute(Object o) {
                //To Override
                ref.onSendRegDataAsyncReturn((String)o);
            }
        };
        svc.setServiceLink("activateAccount.php");
        svc.execute(obj.toString());

    }
}
