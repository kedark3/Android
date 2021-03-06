package com.example.student.foursquareapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] cities={"Chicago,IL","Atlanta,GA","Charlotte,NC","San Francisco,CA","Seattle,WA","Orlando,FL"};

        final Spinner spinnerCities= (Spinner) findViewById(R.id.spinnerCities);

        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,
                cities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(citiesAdapter);


        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,VenueActivity.class);

                intent.putExtra("cityState",cities[spinnerCities.getSelectedItemPosition()]);
                startActivity(intent);
            }
        });

        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            String countryName= gpsTracker.getCountryName(this);
            String city= gpsTracker.getLocality(this);
            String postalCode =gpsTracker.getPostalCode(this);
            String addressLine = gpsTracker.getAddressLine(this);
            Log.d("Location","Latitude:  " + stringLatitude + "Longitude:  " + stringLongitude);

        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }


    }
}
