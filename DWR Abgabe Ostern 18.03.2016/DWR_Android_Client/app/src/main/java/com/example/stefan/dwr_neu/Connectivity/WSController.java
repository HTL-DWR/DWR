package com.example.stefan.dwr_neu.Connectivity;

import android.os.AsyncTask;

import com.example.stefan.dwr_neu.Model.Credentials;
import com.example.stefan.dwr_neu.Model.ResponseObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Stefan on 26.02.2016.
 */
public class WSController extends AsyncTask<String,Void,String> {

    private static final String URI_FIX = "http://10.211.55.4:8080/DWR_WebServer/rest/";

    //Get done with Thread
    @Override
    protected String doInBackground(String... command) {


        String ro = null;


        try {
             ro = RestHelper.restCall(URI_FIX + command[0], command[1], null, ResponseObject.class, "application/x-www-form-urlencoded");



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ro;
    }


}
