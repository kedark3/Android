package com.example.root.foodber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        final Firebase myFirebaseRef = new Firebase(URL_PATH);

        findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        AuthData authData = myFirebaseRef.getAuth();

        if (authData != null) {
            //Intent intent = new Intent(MainActivity.this, ExpensesListActivity.class);
            //startActivity(intent);
        }
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            EditText etEmail= (EditText) findViewById(R.id.etEmail);
            EditText etPassword= (EditText) findViewById(R.id.etPassword);

            @Override
            public void onClick(View v) {
                System.out.println(etEmail.getText().toString() + etPassword.getText().toString());

                myFirebaseRef.authWithPassword(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(MainActivity.this, ExpensesListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(MainActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                        ;
                    }
                });

            }
        });


    }
}
