package com.example.root.foodber;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kedar on 4/28/2016.
 */
public class OrderInterfaceAdapter extends ArrayAdapter<MenuItems> {


    List<MenuItems> mData;
    Context mContext;
    int mResourceId;


    public OrderInterfaceAdapter(Context context, int resource, List<MenuItems> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResourceId = resource;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //if(convertView == null){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResourceId, parent, false);

        TextView tvItemName= (TextView) convertView.findViewById(R.id.textViewItemName);
        TextView tvItemDesc= (TextView) convertView.findViewById(R.id.textViewItemDescription);
        final TextView tvQuantity= (TextView) convertView.findViewById(R.id.textViewQuantity);
        tvItemName.setText(mData.get(position).getItemName()+"-$"+mData.get(position).getCost());
        tvItemDesc.setText(mData.get(position).getDescription());

        convertView.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty= Integer.parseInt(tvQuantity.getText().toString());
                if(qty<10){
                    qty++;
                    Toast.makeText(mContext,mData.get(position).getItemName()+ " Added",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext,"Cannot add more than 10 items!",Toast.LENGTH_SHORT).show();
                Menu.orderCart.add(mData.get(position));
                Log.d("Demo- Cart",Menu.orderCart.size()+Menu.orderCart.toString());
                tvQuantity.setText(qty+"");
            }
        });

        convertView.findViewById(R.id.buttonRemove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty= Integer.parseInt(tvQuantity.getText().toString());
                if(qty>0){
                    qty--;
                    Toast.makeText(mContext,mData.get(position).getItemName()+ " Removed",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext,"No items to remove!",Toast.LENGTH_SHORT).show();
                Menu.orderCart.remove(mData.get(position));
                tvQuantity.setText(qty+"");
                Log.d("Demo- Cart",Menu.orderCart.size()+Menu.orderCart.toString());
            }
        });
        return convertView;
    }


}
