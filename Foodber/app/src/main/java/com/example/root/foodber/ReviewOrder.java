package com.example.root.foodber;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReviewOrder extends AppCompatActivity {

    private String orderSummery = "", orderCosts = "";
    private ArrayList<MenuItems> orderList;
    private float totalCost;
    private TableLayout tableOrderSummary;
    private TableRow newRow;
    private static int REQUEST_CODE = 1;

    private void locationEnablePrompt(){

            AlertDialog.Builder locationEnablingPrompt = new AlertDialog.Builder(ReviewOrder.this);
            locationEnablingPrompt.setTitle("GPS Not Enabled").setMessage("To get your food to you," +
                    "we need to know your location, please enable location services!").setCancelable(false)
                    .setPositiveButton("Okay, take me to the settings!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (!Welcome.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationEnablePrompt();
        } else {
            Welcome.locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
//                    Toast.makeText(ReviewOrder.this, "Your location " + location.getProvider() +
//                            location.getAccuracy() +"-->"+ location.getLatitude() + location.getLongitude()
//                            , Toast.LENGTH_LONG).show();

                    Welcome.lastKnownLatitude=location.getLatitude()+"";
                    Welcome.lastKnownLongitude=location.getLongitude()+"";
                    Welcome.locationManager.removeUpdates(Welcome.locationListener);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    locationEnablePrompt();
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Welcome.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, Welcome.locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        //TextView textViewSummary=(TextView) findViewById(R.id.textViewOrderSummary);
        //TextView textViewTotalCost=(TextView)findViewById(R.id.textViewTotalCost);
        tableOrderSummary=(TableLayout)findViewById(R.id.tableSummary);
        newRow=new TableRow(this);

        orderList= new ArrayList<>();

        Welcome.locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        orderList=getIntent().getParcelableArrayListExtra("orderCart");
        for(MenuItems m:orderList) {
            newRow=new TableRow(this);
            TextView textViewSummary=new TextView(ReviewOrder.this);
            TextView textViewCost=new TextView(ReviewOrder.this);


            textViewSummary.setText(m.getItemName() + "->" + m.getQuantity() + " x $" + m.getCost() + " =   ");
            textViewCost.setText((m.getQuantity()*Float.parseFloat(m.getCost()))+"");
            totalCost+=(m.getQuantity()*Float.parseFloat(m.getCost()));
            newRow.addView(textViewSummary);
            newRow.addView(textViewCost);

            tableOrderSummary.addView(newRow);
        }
        newRow=new TableRow(this);
        newRow.setMinimumWidth(tableOrderSummary.getWidth());
        TextView textViewSummary=new TextView(ReviewOrder.this);
        TextView textViewCost=new TextView(ReviewOrder.this);

        textViewSummary.setText("Total $");
        textViewCost.setText(totalCost+"");
        newRow.addView(textViewSummary);
        newRow.addView(textViewCost);

        tableOrderSummary.addView(newRow);


    }

    public void onBraintreeSubmit(View v) {
        PaymentRequest paymentRequest = new PaymentRequest().amount(totalCost+"")
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIy" +
                        "YWE0NTk1YjkyYjY3NTY3NjAwODQ2MTJhOWJmMzhiMDlhZmUyMzY1ZmRjYmE4Mj" +
                        "FkYWQ3OGZmOTFhYjk2ZTA0fGNyZWF0ZWRfYXQ9MjAxNi0wNC0yOVQwNDo1NzowMi" +
                        "45OTI3Mjk5NDMrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJc" +
                        "dTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodH" +
                        "RwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaG" +
                        "FudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb2" +
                        "4iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW5" +
                        "0QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNv" +
                        "bTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2" +
                        "V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0" +
                        "aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXk" +
                        "uY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5" +
                        "zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGh" +
                        "yZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFs" +
                        "Ijp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsa" +
                        "WVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidX" +
                        "NlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoi" +
                        "aHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodH" +
                        "RwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxv" +
                        "d0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudC" +
                        "I6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50" +
                        "SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJt" +
                        "ZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNv" +
                        "Q29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4" +
                        "cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
        startActivityForResult(paymentRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                        BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
                );
                Toast.makeText(ReviewOrder.this,"Payment success,Order Placed!",Toast.LENGTH_LONG).show();
                //MainActivity.myFirebaseRef.child("Orders").push().setValue(orderList);
                String loggedInUserEmail=MainActivity.myFirebaseRef.getAuth().getProviderData().get("email").toString();


                //loggedInUserEmail = loggedInUserEmail.replace('.', ',');

                final String date = DateFormat.getDateTimeInstance().format(new Date());
                OrderClass orderObject=new OrderClass(loggedInUserEmail, ""+totalCost,"pending","none",date,
                        "false",Welcome.lastKnownLatitude,Welcome.lastKnownLongitude,getIntent()
                        .getStringExtra("restaurantName"),orderList);
                Firebase orderRef=MainActivity.myFirebaseRef.child("Orders").child(loggedInUserEmail.replace('.', ',')+"").push();
                orderRef.setValue(orderObject);
                orderRef.child("key").setValue(orderRef.getKey());
                Intent intent=new Intent(ReviewOrder.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
