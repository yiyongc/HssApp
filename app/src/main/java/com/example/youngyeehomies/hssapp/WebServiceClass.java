package com.example.youngyeehomies.hssapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Gibson on 4/8/2015.
 */
public class WebServiceClass extends AsyncTask<String, Void, Object> {
    private String serviceLink = "";

    protected Object doInBackground(String... params) {
        try {
            //Allow timeout while connecting
            HttpParams httpParams = new BasicHttpParams();
            //time out for connection try is 10s
            int timeOutConnection = 10000;
            HttpConnectionParams.setConnectionTimeout(httpParams, timeOutConnection);
            //time out for waiting for data is 10s
            int timeOutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParams, timeOutSocket);

            DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppostreq = new HttpPost(Globals.serverURL+serviceLink);
            StringEntity se = new StringEntity(params[0]);
            se.setContentType("application/json;charset=UTF-8");
            httppostreq.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppostreq);

            //get json object from server
            HttpEntity resultentity = httpresponse.getEntity();
            InputStream inputstream = resultentity.getContent();
            String resultstring = convertStreamToString(inputstream);
            inputstream.close();
            Log.e("Web Service Output",resultstring);
            //JSONObject recvdjson = new JSONObject(resultstring);
            return resultstring;
        } catch (Exception e) {
            Log.e("tagto", "excep");
            return e;
        }
    }

    protected void onPostExecute(String webResponse){
        //To Override
    }

    public void setServiceLink(String serviceLink){
        this.serviceLink = serviceLink;
    }

    private String convertStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }
}
