package com.example.kedar.mapsdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsDemo extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private String placeTypeArray[];
    private int placeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_demo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        placeTypeArray = getResources().getStringArray(R.array.place_types);
        placeType = 0;
        final AlertDialog.Builder placeTypeAlert = new AlertDialog.Builder(MapsDemo.this);
        placeTypeAlert.setTitle(R.string.alert_title).setSingleChoiceItems(R.array.place_types, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsDemo.this, "You selected-" + placeTypeArray[which], Toast.LENGTH_LONG).show();
                placeType = which;


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (ActivityCompat.checkSelfPermission(MapsDemo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                        .getCurrentPlace(googleApiClient, null);
                result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                    @Override
                    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            //Log.i("Demo",""+placeLikelihood.getPlace().getName()+placeLikelihood.getPlace().getPlaceTypes());
                            if((placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_UNIVERSITY) && placeType ==0)||
                                    (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAR_RENTAL) && placeType ==1)||
                                    (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_GAS_STATION) && placeType ==2)
                                    ||(placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_SHOPPING_MALL) && placeType ==3)||
                                    (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_RESTAURANT) && placeType ==4))
                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                        .position(placeLikelihood.getPlace().getLatLng()).title(placeLikelihood.getPlace().getName()+""));
                            else
                                mMap.addMarker(new MarkerOptions().position(placeLikelihood.getPlace().getLatLng()).
                                        title(placeLikelihood.getPlace().getName()+""));
                        }

                        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                        boundsBuilder.include(likelyPlaces.get(0).getPlace().getLatLng());
                        boundsBuilder.include(likelyPlaces.get(likelyPlaces.getCount()-1).getPlace().getLatLng());
                        // pan to see all markers on map:
                        LatLngBounds bounds = boundsBuilder.build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,3));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                        likelyPlaces.release();

                    }
                });


            }
        }).show().setCancelable(false);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        //LatLng charlotte = new LatLng(35, -80);
        //mMap.addMarker(new MarkerOptions().position(charlotte).title("Marker in Charlotte"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(charlotte));


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Demo","Connection failed"+connectionResult.getErrorMessage());
    }
}
