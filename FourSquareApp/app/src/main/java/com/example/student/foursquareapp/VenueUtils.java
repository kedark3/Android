package com.example.student.foursquareapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by student on 3/21/16.
 */
public class VenueUtils {


    static public class VenueParser{

        static ArrayList<Venue> VenueJSONParser(String in) throws JSONException {

        String venueId, venueName,categoryName,categoryIcon,checkInCount;
        ArrayList<Venue> venueArrayList= new ArrayList<>();
        JSONObject root = new JSONObject(in);
        JSONObject responseObject= root.getJSONObject("response");
        JSONArray venuesJSONArray = responseObject.getJSONArray("venues");


        for(int i=0;i<venuesJSONArray.length();i++){

            JSONObject venueObject=venuesJSONArray.getJSONObject(i);
            venueId=venueObject.getString("id").toString();
            venueName=venueObject.getString("name").toString();
            JSONArray categoriesArray= venueObject.getJSONArray("categories");
            JSONObject categoriesObject=null;
            categoryName="";
            categoryIcon="";
            if(!categoriesArray.isNull(0)){
                categoriesObject=categoriesArray.getJSONObject(0);
                categoryName=categoriesObject.getString("name").toString();

                JSONObject cateIconObject=categoriesObject.getJSONObject("icon");
                categoryIcon=cateIconObject.getString("prefix").replace("\\","")+ "bg_64"+cateIconObject.getString("suffix");
            //    Log.d("DataParsedAObject", cateIconObject.getString("prefix").replace("\\", "") + "bg_64" + cateIconObject.getString("suffix"));
            }

            JSONObject statsObject= venueObject.getJSONObject("stats");
            checkInCount=statsObject.get("checkinsCount")+"";

            venueArrayList.add(new Venue(venueId, venueName,categoryName,categoryIcon,checkInCount));


        }

        return venueArrayList;
        }
    }
}
