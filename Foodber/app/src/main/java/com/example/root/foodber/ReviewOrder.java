package com.example.root.foodber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        TextView textView=(TextView) findViewById(R.id.textView2);

        textView.setText(getIntent().getParcelableArrayListExtra("orderCart").toString());
    }
}
