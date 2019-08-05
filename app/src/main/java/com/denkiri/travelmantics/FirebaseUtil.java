package com.denkiri.travelmantics;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static ArrayList<TravelDeal> mDeals;
    private static final int RC_SIGN_IN =123;
    private static Activity caller;
    private FirebaseUtil(){}
    public static void openFbReference(String ref, final Activity callerActivity){
        if(firebaseUtil==null){
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase =FirebaseDatabase.getInstance();
            caller =callerActivity;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    Toast.makeText(callerActivity.getBaseContext(),"welcome back",Toast.LENGTH_LONG).show();
                }
            };

        }
        mDeals =new ArrayList<TravelDeal>();
        mDatabaseReference =mFirebaseDatabase.getReference().child(ref);
    }

public static void attachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
}
public static void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
}
private static void signIn(){
    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent

    caller.startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN);

}

}
