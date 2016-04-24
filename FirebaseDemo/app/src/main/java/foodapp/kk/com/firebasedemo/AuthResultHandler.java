package foodapp.kk.com.firebasedemo;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Sairam on 4/11/2016.
 */
public class AuthResultHandler implements Firebase.AuthResultHandler {
    private final String provider;
    public AuthResultHandler(String provider){
        this.provider =provider;
    }

    @Override
    public void onAuthenticated(AuthData authData) {
        setAuthenticatedUser(authData);
    }

    private void setAuthenticatedUser(AuthData authData) {
        if(authData!=null){
            System.out.println(authData.toString());

        }else{
            System.out.println("User not authenticated");
        }
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        System.out.println(firebaseError.getMessage());
    }
}
