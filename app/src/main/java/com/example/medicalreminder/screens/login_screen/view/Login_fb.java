package com.example.medicalreminder.screens.login_screen.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicalreminder.screens.SplashScreen;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Login_fb extends LoginScreenActivity {

    //              Login with facebook
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authListener;
    private AccessTokenTracker accessTokenTracker;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //              Firebase database object to access firebase's realtime database.
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medical-reminder-62cc4-default-rtdb.firebaseio.com/");
    SharedPreferences userData ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//          for login with facebook
        // shared preference file
        userData = getSharedPreferences("user_file", MODE_PRIVATE);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Toast.makeText(Login_fb.this, "facebook:onError: " + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login_fb.this, "facebook:onCancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(firebaseAuth.getCurrentUser() == null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Toast.makeText(this, "You already logged in", Toast.LENGTH_SHORT).show();
        }
    }


    //--------------------------------------------------- Start of Login using Facebook ------------------------------------------------
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //  to check if the email already existed in firebase realtime database
                            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.hasChild(user.getEmail().replace('.','*'))) {
                                        //  sending data to firebase realtime database
                                        databaseReference.child("users").child(user.getEmail().replace('.','*')).child("name").setValue(user.getDisplayName());
                                        databaseReference.child("users").child(user.getEmail().replace('.','*')).child("phone").setValue(user.getPhoneNumber());
                                        databaseReference.child("users").child(user.getEmail().replace('.','*')).child("password").setValue("");
                                        databaseReference.child("users").child(user.getEmail().replace('.','*')).child("date of birth").setValue("");
                                    }
                                    SharedPreferences.Editor editor = userData.edit();
                                    editor.putString("email",user.getEmail().replace('.','*'));
                                    editor.commit();
                                    startActivity(new Intent(Login_fb.this, SplashScreen.class));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_fb.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
