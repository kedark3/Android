package com.example.root.foodber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    public static ArrayList<MenuItems> orderCart;
    private ArrayList<MenuItems> menuItemsArrayList;
    private ListView buildOrderListView;
    private OrderInterfaceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuItemsArrayList =new ArrayList<>();
        orderCart= new ArrayList<>();
        buildOrderListView=(ListView) findViewById(R.id.listViewBuildOrder);
        adapter=new OrderInterfaceAdapter(this,R.layout.order_interface_list_row,menuItemsArrayList);
        adapter.setNotifyOnChange(true);

        buildOrderListView.setAdapter(adapter);

        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren()){

                            menuItemsArrayList.add(d.getValue(MenuItems.class));
                        }


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderCart.size()<1) {
                    Toast.makeText(Menu.this,"Cart Empty!",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent= new Intent(Menu.this,ReviewOrder.class);
                intent.putParcelableArrayListExtra("orderCart",orderCart);
                intent.putExtra("restaurantName",getIntent().getStringExtra("restaurantName"));
                startActivity(intent);
            }
        });

    }
}





/*Adding items of menu to Firebase
*
* MenuItems menuItem;

        menuItem=new MenuItems("Veggie Spring Rolls","Vegetable Rolls","1.5");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);

        menuItem=new MenuItems("Lunch box","Make your own","6.55");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);

        menuItem=new MenuItems("Grilled Teriyaki Chicken","Kind of Sweet chicken","3.30");
        MainActivity.myFirebaseRef.child("Menu").child(getIntent().getStringExtra("restaurantName"))
                .child(menuItem.getItemName()).setValue(menuItem);
*
*
* */