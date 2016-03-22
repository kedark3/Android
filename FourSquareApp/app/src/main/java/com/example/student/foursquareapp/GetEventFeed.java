package com.example.student.foursquareapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by student on 3/21/16.
 */
public class GetEventFeed extends AsyncTask<String,Void,ArrayList<Venue>> {
    contextInterface activity;
    ProgressDialog progressDialog;



    static public interface contextInterface{
        public Context getcontext();
        public void setupData(ArrayList<Venue> venue);
    }

    public GetEventFeed(contextInterface activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Venue> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }

                try {
                    return VenueUtils.VenueParser.VenueJSONParser(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(activity.getcontext());
        progressDialog.setMessage("Loading Venues...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Venue> venues) {
        super.onPostExecute(venues);
        progressDialog.dismiss();
        activity.setupData(venues);


    }
}
