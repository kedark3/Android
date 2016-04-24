package foodapp.kk.com.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student.foursquareapp.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        final EditText editTextEmail = (EditText) findViewById(R.id.editText);
        final EditText editTextPassword = (EditText) findViewById(R.id.editText2);

        Firebase ref = new Firebase("https://boiling-fire-5111.firebaseio.com/");

        Firebase alanRef= ref.child("users").child("alanisawesome");

        User alan = new User("Alan Turing",1912);

        alanRef.setValue(alan);

        Firebase postRef = ref.child("posts");

        /*Map<String, String> post1 = new HashMap<String, String>();
        post1.put("author", "gracehop");
        post1.put("title", "Announcing COBOL, a New Programming Language");
        postRef.push().setValue(post1);

        Map<String, String> post2 = new HashMap<String, String>();
        post2.put("author", "alanisawesome");
        post2.put("title", "The Turing Machine");
        postRef.push().setValue(post2);

        String postId = postRef.getKey();

        Firebase upvotesRef = new Firebase("https://boiling-fire-5111.firebaseio.com/posts/-KF2K3musGhoQgyMiNG5/upvotes");

        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });*/

        // Get a reference to our posts
        ref = new Firebase("https://boiling-fire-5111.firebaseio.com/posts");

        // Attach an listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        final Firebase postItRef = new Firebase("https://boiling-fire-5111.firebaseio.com/posts");
        findViewById(R.id.buttonPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextAuthor= (EditText) findViewById(R.id.editText4);
                EditText editTextTitle= (EditText) findViewById(R.id.editText3);
                BlogPost blogPost=new BlogPost(editTextAuthor.getText().toString(),editTextTitle.getText().toString());
                postItRef.push().setValue(blogPost);
            }
        });

        final Firebase authRef = new Firebase("https://boiling-fire-5111.firebaseio.com/");
        final Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler(){
            @Override
            public void onAuthenticated(AuthData authData){
                System.out.println(authData);
                Map <String,String> map= new HashMap<String,String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")){
                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                }
                authRef.child("users").child(authData.getUid()).setValue(map);
            }
            public void onAuthenticationError(FirebaseError firebaseError){
                System.out.println("FirebaseError"+firebaseError);
                if(firebaseError.getCode()== FirebaseError.USER_DOES_NOT_EXIST){
                    authRef.createUser(editTextEmail.getText().toString(),editTextPassword.getText().toString(),new Firebase.ValueResultHandler<Map<String,Object>>(){
                       @Override
                        public void onSuccess(Map<String,Object> result){
                           System.out.println("Successfully created a User:"+result.get("uid"));
                       }

                        @Override
                        public void onError(FirebaseError firebaseError){
                            System.out.println("FirebaseError"+firebaseError);
                        }
                    });
                }
            }
        };

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //authRef.authWithPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString(),authResultHandler);
                authRef.authWithPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString(),new AuthResultHandler("password"));
            }
        });

    }
}