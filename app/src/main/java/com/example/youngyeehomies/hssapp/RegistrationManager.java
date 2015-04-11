package com.example.youngyeehomies.hssapp;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrationManager extends AsyncTask {

    public static final int NETWORK_STATE_REGISTER = 1;
    String username, password, token, defaultClinic;

    public RegistrationManager(String user, String pass, String t, String defaultclinic) {
        username = user;
        password = pass;
        token = t;
        defaultClinic = defaultclinic;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        getJSON((String) params[0], (Integer) params[1]);

        return null;
    }

    private void getJSON(String url, int state) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

        boolean valid = false;

        switch (state) {
            case RegistrationManager.NETWORK_STATE_REGISTER:
                postParameters.add(new BasicNameValuePair("userName", username));
                postParameters.add(new BasicNameValuePair("password", password));
                postParameters.add(new BasicNameValuePair("token", token));
                postParameters.add(new BasicNameValuePair("defaultClinic", defaultClinic));
                valid = true;
                break;
            default:
                break;
        }

        if (valid) {

            BufferedReader bufferedReader = null;
            StringBuffer stringBuffer = new StringBuffer("");

            try {

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
                request.setEntity(entity);
                HttpResponse response = httpClient.execute(request);

                bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                String lineSeparator = System.getProperty("line.separator");

                while ((line = bufferedReader.readLine())!= null) {
                    stringBuffer.append(line + lineSeparator);
                }
                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
