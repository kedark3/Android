package com.example.root.foodber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;

public class Restaurants extends AppCompatActivity {
    ListView listViewRestaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        listViewRestaurants=(ListView) findViewById(R.id.listView);



        FirebaseListAdapter<RestaurantObjects> listAdapter=new FirebaseListAdapter<RestaurantObjects>(
                this,
                RestaurantObjects.class,
                android.R.layout.simple_list_item_1,
                MainActivity.myFirebaseRef.child("Restaurant")
        ) {
            @Override
            protected void populateView(View view, final RestaurantObjects restaurantName, int i) {

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setText(restaurantName.getRestaurantName());


            }

        };

        listViewRestaurants.setAdapter(listAdapter);


        listViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(android.R.id.text1);

                Intent intent=new Intent(Restaurants.this,Menu.class);
                intent.putExtra("restaurantName",textView.getText().toString());

                startActivity(intent);
            }
        });
    }
}
