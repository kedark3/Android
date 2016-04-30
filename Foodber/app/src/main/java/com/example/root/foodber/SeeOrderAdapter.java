package com.example.root.foodber;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kedar on 4/29/2016.
 */
public class SeeOrderAdapter extends ArrayAdapter<OrderClass> {


    List<OrderClass> mData;
    Context mContext;
    int mResourceId;

    public SeeOrderAdapter(Context context, int resource, List<OrderClass> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResourceId, parent, false);

        TextView textViewRestaurant= (TextView) convertView.findViewById(R.id.textViewRestaurantName);
        TextView textViewOrderTime= (TextView) convertView.findViewById(R.id.textViewOrderTime);
        TextView textViewOrderingUser= (TextView) convertView.findViewById(R.id.textViewOrderingUser);



        textViewRestaurant.setText(mData.get(position).getRestaurantName()+ "@"+
                mData.get(position).getLatitude()+","+mData.get(position).getLongitude());
        textViewOrderTime.setText(mData.get(position).getTimestamp());
        textViewOrderingUser.setText(mData.get(position).getUser());

        return convertView;
    }
}
