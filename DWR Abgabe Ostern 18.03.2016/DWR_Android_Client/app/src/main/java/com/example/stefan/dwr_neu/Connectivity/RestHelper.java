package com.example.stefan.dwr_neu.Connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import com.google.gson.Gson;

public class RestHelper {
    //private static CManager cManager = new CManager();

    public static String restCall(String uri, String method, Object data, Class returnClass, String contentType) throws MalformedURLException, IOException, JSONException, InterruptedException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //cManager.setCookies(connection);

        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", "application/json");

        if (contentType == null) {
            connection.setRequestProperty("Content-Type", "application/json");
        } else {
            connection.setRequestProperty("Content-Type", contentType);
        }

        connection.setDoInput(true);

        Gson gson = new Gson();

        String json;

        if (data != null) {
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            if (contentType != null && contentType.equals("application/x-www-form-urlencoded")) {
                out.write(data.toString());
            } else {
                json = gson.toJson(data);
                out.write(json);
            }
            out.flush();
        }

        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        //Object responseObject = gson.fromJson(reader, returnClass);

        //cManager.storeCookies(connection);
        connection.disconnect();

        //return responseObject;

        String content;

        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        content = sb.toString();

        System.out.println("++++++" + content);

        return content;
    }
}
