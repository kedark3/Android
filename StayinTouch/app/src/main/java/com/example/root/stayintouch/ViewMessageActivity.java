package com.example.root.stayintouch;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class ViewMessageActivity extends AppCompatActivity {
    EditText etMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        final Firebase myFirebaseRef= new Firebase(MainActivity.URL_PATH+"/Messages");
        final Firebase myUserRef= new Firebase(MainActivity.URL_PATH+"/users");
        etMessage=(EditText) findViewById(R.id.editTextMssage);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            String sender,receiver,message_text;

            @Override
            public void onClick(View v) {
                Firebase userRef=myUserRef.child(myFirebaseRef.getAuth().getProviderData().get("email").toString().split("@")[0]);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sender=dataSnapshot.getValue(User.class).getName();
                        receiver=getIntent().getStringExtra("name");
                        if(!etMessage.getText().toString().isEmpty()) {
                            message_text = etMessage.getText().toString();
                            Messages message = new Messages("", false, message_text, receiver, sender);
                            myFirebaseRef.push().setValue(message);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_for_view_msg,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.call_contact:
                break;
            case R.id.view_contact:
                break;
        }
        return true;
    }

}
