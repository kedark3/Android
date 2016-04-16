package com.example.root.stayintouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText etEmail,etPassword,etName,etConfirmPassword,etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail =(EditText)findViewById(R.id.editTextEmail);
        etPassword =(EditText)findViewById(R.id.editTextPassword);
        etName =(EditText)findViewById(R.id.editTextName);
        etConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        etPhone=(EditText) findViewById(R.id.editTextPhone);

        final Firebase myFirebaseRef = new Firebase("http://foodber.firebaseio.com/users");
        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final User user=new User(etEmail.getText().toString(),etPassword.getText().toString(),
    //                    etName.getText().toString(),et800Num.getText().toString(),etPhone.getText().toString(),
  //                      0,0,null);

                if(!etEmail.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&
                        !etName.getText().toString().isEmpty()&&!etConfirmPassword.getText().toString().isEmpty()&&
                        !etPhone.getText().toString().isEmpty() &&(
                        (etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))))
                    myFirebaseRef.createUser(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String,Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringUserMap) {
                            myFirebaseRef.child(etName.getText().toString()).setValue(user);
                            finish();

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(Signup.this,firebaseError.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                else
                    Toast.makeText(Signup.this,"Please fill in all the details correctly!",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
