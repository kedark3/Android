package com.example.root.foodber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeeOrders extends AppCompatActivity implements SeeOrderAdapter.OrderModification {

    private ListView listViewOrder;
    private ArrayList<OrderClass> orders;
    private SeeOrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_orders);

        listViewOrder=(ListView) findViewById(R.id.listViewOrders);
        orders=new ArrayList<>();
        adapter= new SeeOrderAdapter(this,R.layout.view_orders_row_layout,orders);
        adapter.setNotifyOnChange(true);
        listViewOrder.setAdapter(adapter);


        MainActivity.myFirebaseRef.child("Orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot d: dataSnapshot.getChildren()) {

                    OrderClass orderClass= d.getValue(OrderClass.class);
                    if(orderClass.getStatus().equals("pending") &&
                            orderClass.getAssignedTo().equals("none")) {
                        Log.d("Demo-Key",orderClass.toString());
                        orders.add(orderClass);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //throws exception when last order object is removed from firebase
                try{
                    Log.d("Demo",dataSnapshot.getValue(OrderClass.class).toString());
                    orders.remove(dataSnapshot.getValue(OrderClass.class));
                }
                catch (Exception e){e.printStackTrace();}
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void assignToUser(int position, OrderClass o) {

        MainActivity.myFirebaseRef.child("Orders").child(o.getUser().replace(".",",")).child(o.getKey())
                .child("assignedTo").setValue(MainActivity.myFirebaseRef.getAuth().getProviderData().get("email"));
    }
}
