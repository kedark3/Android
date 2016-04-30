package com.example.root.foodber;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kedar on 4/29/2016.
 */
public class SeeOrderAdapter extends ArrayAdapter<OrderClass> {


    List<OrderClass> mData;
    Context mContext;
    int mResourceId;
    OrderModification orderModification;

    public SeeOrderAdapter(Context context, int resource, List<OrderClass> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
        orderModification=new SeeOrders();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResourceId, parent, false);

        final TextView textViewRestaurant= (TextView) convertView.findViewById(R.id.textViewRestaurantName);
        TextView textViewOrderTime= (TextView) convertView.findViewById(R.id.textViewOrderTime);
        TextView textViewOrderingUser= (TextView) convertView.findViewById(R.id.textViewOrderingUser);
        final RadioButton radioButton= (RadioButton) convertView.findViewById(R.id.radioButtonAcceptOrder);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mData.get(position).getUser().equals(MainActivity.myFirebaseRef.getAuth().getProviderData().get("email")))
                    orderModification.assignToUser(position,mData.get(position));
                else {
                    Toast.makeText(mContext, "Can't deliver order to yourself! Long press to delete it!", Toast.LENGTH_SHORT).show();
                    radioButton.setChecked(false);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final String loggedInUserEmail=MainActivity.myFirebaseRef.getAuth().getProviderData().get("email").toString();
                AlertDialog.Builder alertDelete=new AlertDialog.Builder(mContext);

                if(mData.get(position).getUser().equals(loggedInUserEmail))
                    alertDelete.setTitle("Delete / Cancel Order").setMessage("Do you want to Delete this Order?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.myFirebaseRef.child("Orders").child(loggedInUserEmail.replace(".",","))
                                            .child(mData.get(position).getKey()).removeValue();
                                }
                            }).show();
                return true;
            }
        });
        textViewRestaurant.setText(mData.get(position).getRestaurantName()+ "@"+
                mData.get(position).getLatitude()+","+mData.get(position).getLongitude());
        textViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<"+mData.get(position).getLatitude()
                        +">,<"+mData.get(position).getLongitude()+"?q=<"+mData.get(position).getLatitude()+">,<"+
                        mData.get(position).getLongitude()+">(Delivery Location)"));
                mContext.startActivity(intent);
            }
        });
        textViewOrderTime.setText(mData.get(position).getTimestamp());
        textViewOrderingUser.setText("By "+mData.get(position).getUser());

        return convertView;
    }


    public interface OrderModification{
        void assignToUser(int position, OrderClass order);
    }
}
