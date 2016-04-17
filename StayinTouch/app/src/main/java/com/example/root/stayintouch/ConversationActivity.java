package com.example.root.stayintouch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {
    ListView contactsListView;
    ArrayList<User> contactsList = new ArrayList<>();
    ProgressDialog progressDialog;
    Firebase myFbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH + "/users");
        myFbRef=myFirebaseRef;
        contactsListView = (ListView) findViewById(R.id.listViewContacts);
        final UserListAdapter adapter = new UserListAdapter(this, R.layout.user_list_row, contactsList);
        progressDialog=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Contacts!");
        progressDialog.show();
        progressDialog.setCancelable(false);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (!user.getEmail().equals(myFirebaseRef.getAuth().getProviderData().get("email"))) {
                        contactsList.add(user);
                    }
                }


                contactsListView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Phone number" + myFirebaseRef + contactsList.get(position).getPhone());
                Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:"+contactsList.get(position).getPhone()));
                if (ActivityCompat.checkSelfPermission(ConversationActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit_profile:
                Intent intent = new Intent(ConversationActivity.this,EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                myFbRef.unauth();
                finish();
                break;

        }
        return true;
    }
}
