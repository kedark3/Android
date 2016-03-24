package com.example.student.foursquareapp;

/**
 * Created by student on 3/21/16.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VenueAdapter extends ArrayAdapter<Venue> {
    List<Venue> mData;
    Context mContext;
    int mResourceId;
    SharedPreferences preferences;


    public VenueAdapter(Context context, int resource, List<Venue> objects, SharedPreferences preferences) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
        this.preferences=preferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, parent, false);
        //}

        TextView tvVenueName=(TextView) convertView.findViewById(R.id.textViewVenueName);
        TextView tvCatName= (TextView) convertView.findViewById(R.id.textViewCatName);
        ImageView imageViewCatIcon=(ImageView) convertView.findViewById(R.id.imageViewCatIcon);
        ImageView imageViewBadge=(ImageView) convertView.findViewById(R.id.imageViewBadge);
        ImageView imageViewMark=(ImageView) convertView.findViewById(R.id.imageViewMark);

        tvVenueName.setText(mData.get(position).getVenueName());
        tvCatName.setText(mData.get(position).getCategoryName());
        try {
            Picasso.with(mContext).load(mData.get(position).getCategoryIcon()).into(imageViewCatIcon);
        }
        catch (IllegalArgumentException e){ imageViewCatIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.logo));}
        if(Integer.parseInt(mData.get(position).checkInCount)<101)
            imageViewBadge.setImageDrawable(convertView.getResources().getDrawable(R.drawable.bronze));
        else if(Integer.parseInt(mData.get(position).checkInCount)<501)
            imageViewBadge.setImageDrawable(convertView.getResources().getDrawable(R.drawable.silver));
        else if(Integer.parseInt(mData.get(position).checkInCount)>500)
            imageViewBadge.setImageDrawable(convertView.getResources().getDrawable(R.drawable.gold));

        Log.d("checkvisited",mData.get(position).isVisited()+"and***"+preferences.getString(mData.get(position).venueId,null));
        if(mData.get(position).isVisited() || preferences.getString(mData.get(position).venueId,null)!=null ){
            imageViewMark.setImageDrawable(convertView.getResources().getDrawable(R.drawable.visited));

        }
        else
            imageViewMark.setImageDrawable(convertView.getResources().getDrawable(R.drawable.unvisited));

        return convertView;
    }
}