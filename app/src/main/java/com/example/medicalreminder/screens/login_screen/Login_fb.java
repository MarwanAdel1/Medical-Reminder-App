package com.example.medicalreminder.screens.login_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.ConversationActions;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicalreminder.screens.MainActivity;
import com.example.medicalreminder.screens.SplashScreen;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Request;

import java.util.Arrays;

public class Login_fb extends LoginScreen {

    //              Login with facebook
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authListener;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//          for login with facebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("ON SUCCESSS IN LOGIN MANAGER : ----------------", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d("TAG", "facebook:onError", e);
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if(user != null) {
//            for (UserInfo userInfo : user.getProviderData()) {
//                if (userInfo.getProviderId().equals("facebook.com")) {
//                    Log.d("TAG", "User is signed in with Facebook");
//                }
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "email: " + firebaseAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Toast.makeText(this, "You already logged in", Toast.LENGTH_SHORT).show();
        }
    }


    //--------------------------------------------------- Start of Login using Facebook ------------------------------------------------
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        Request request = Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback();
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

                                        SharedPreferences.Editor editor = userData.edit();
                                        editor.putString("email",user.getEmail().replace('.','*'));
                                        editor.commit();
                                        startActivity(new Intent(Login_fb.this, SplashScreen.class));
                                    }
                                    else{
                                        Toast.makeText(Login_fb.this, "You already have account with this email, please try to login with it ..", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login_fb.this, LoginScreen.class));
                                    }
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
