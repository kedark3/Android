package com.example.root.foodber;

import android.app.Activity;
import android.content.Intent;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReviewOrder extends AppCompatActivity {

    private String orderSummery="",orderCosts="";
    private ArrayList<MenuItems> orderList;
    private float totalCost;
    private TableLayout tableOrderSummary;
    private TableRow newRow;
    private static int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        //TextView textViewSummary=(TextView) findViewById(R.id.textViewOrderSummary);
        //TextView textViewTotalCost=(TextView)findViewById(R.id.textViewTotalCost);
        tableOrderSummary=(TableLayout)findViewById(R.id.tableSummary);
        newRow=new TableRow(this);

        orderList= new ArrayList<>();


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
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIyYWE0NTk1YjkyYjY3NTY3NjAwODQ2MTJhOWJmMzhiMDlhZmUyMzY1ZmRjYmE4MjFkYWQ3OGZmOTFhYjk2ZTA0fGNyZWF0ZWRfYXQ9MjAxNi0wNC0yOVQwNDo1NzowMi45OTI3Mjk5NDMrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
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


                loggedInUserEmail = loggedInUserEmail.replace('.', ',');

                final String date = DateFormat.getDateTimeInstance().format(new Date());
                OrderClass orderObject=new OrderClass(loggedInUserEmail, ""+totalCost,"pending","none",date,"false",orderList);
                MainActivity.myFirebaseRef.child("Orders").child(loggedInUserEmail+"").push().setValue(orderObject);
                Intent intent=new Intent(ReviewOrder.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
