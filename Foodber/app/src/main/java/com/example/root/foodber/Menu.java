package com.example.root.foodber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MenuItems menuItem;

        menuItem=new MenuItems("Iced Coffee (with or without Milk)","Grande","2.65");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);

        menuItem=new MenuItems("Caffe Latte","Tall","2.95");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);

        menuItem=new MenuItems("Caramel Macchiato","Venti","475");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);
    }
}
