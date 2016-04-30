package com.example.root.foodber;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    public static LocationManager locationManager;
    public static LocationListener locationListener;
    public static String lastKnownLatitude,lastKnownLongitude;
    private TextView textViewWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        textViewWelcome=(TextView) findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome "+MainActivity.myFirebaseRef.getAuth().getProviderData().get("email")+",");
    /*****************************************************************************************************************
        //****adding restaurant's manually though code... did not explore other options since it was not required*********
        RestaurantObjects object=new RestaurantObjects("Starbucks","35.3069989","-80.7334355");
        MainActivity.myFirebaseRef.child("Restaurant").child(object.getRestaurantName()).setValue(object);

        object=new RestaurantObjects("Panda Express","35.3059351","-80.7337307");
        MainActivity.myFirebaseRef.child("Restaurant").child(object.getRestaurantName()).setValue(object);

        object=new RestaurantObjects("Subway","35.3059351","-80.7337307");
        MainActivity.myFirebaseRef.child("Restaurant").child(object.getRestaurantName()).setValue(object);

        object=new RestaurantObjects("Salsarita's Fresh Cantina","35.3059351","-80.7337307");
        MainActivity.myFirebaseRef.child("Restaurant").child(object.getRestaurantName()).setValue(object);
        *****************************************************************************************************************/

        findViewById(R.id.buttonPlaceOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Restaurants.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.buttonSeeOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, SeeOrders.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.gpsSwitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Welcome.this,"Wait for this feature to be enabled.",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.welcome_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:

                MainActivity.myFirebaseRef.unauth();
                Intent intent=new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return true;
    }
}
