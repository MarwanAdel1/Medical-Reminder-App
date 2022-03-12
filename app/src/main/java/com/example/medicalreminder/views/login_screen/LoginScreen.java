package com.example.medicalreminder.views.login_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.SplashScreen;
import com.example.medicalreminder.views.signup_screen.Registeration;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginScreen extends AppCompatActivity {
    TextView email_field;
    TextView password_field;
    TextView signup;
    Button login_button;
    ImageView twitter_img;
    ImageView google_img;
    ImageView fb_img;

    public static final int RC_SIGN_IN = 123;

    //              Firebase element
    FirebaseAuth firebaseAuth;
    //              Login with google
    private GoogleSignInClient mGoogleSignInClient;
    //              Login with facebook
    private CallbackManager mCallbackManager;
//    private AccessTokenTracker accessTokenTracker;
//    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        //          for login with google
        requestGoogleLogin();

        //          Firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();

        //              Handling buttons
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, Registeration.class));
            }
        });

        twitter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this, "Login using Twitter", Toast.LENGTH_SHORT).show();
            }
        });

        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this, "Login using Gmail", Toast.LENGTH_SHORT).show();
                requestGoogleLogin();
                googleLogin();
            }
        });

        fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this, "Login using facebook", Toast.LENGTH_SHORT).show();
                //          for login with facebook
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    private void initUi() {
        email_field = findViewById(R.id.name_field);
        password_field = findViewById(R.id.password_field);
        signup = findViewById(R.id.signup);
        login_button = findViewById(R.id.login_btn);
        twitter_img = findViewById(R.id.twitter_login);
        fb_img = findViewById(R.id.fb_login);
        google_img = findViewById(R.id.gmail_login);
    }

//------------------------------------------------------ Start of default Login (By email, password) --------------------------------
    private void loginUser() {
        String email = email_field.getText().toString();
        String password = password_field.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_field.setError("Email can not be empty");
            email_field.requestFocus();
        } else {
            if (TextUtils.isEmpty(password)) {
                password_field.setError("Password can not be empty");
                password_field.requestFocus();
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //          if user registration created successfully
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginScreen.this, "Logged in SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, SplashScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
// ------------------------------------------------------End of default Login --------------------------------------------------------


//--------------------------------------------------- Start of Login using Google -------------------------------------------------

    private void requestGoogleLogin(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1042439510582-mlaoe41nvpo6h6g4e4pcr8qnbktvdu9e.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleLogin(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(google_img.isClickable()) {
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                    SharedPreferences.Editor editor = getApplicationContext()
                            .getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            .edit();
                    editor.putString("username", account.getDisplayName());
                    editor.putString("email", account.getEmail());
                    editor.apply();

                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("TAG", "Google sign in failed: " + e.getMessage(), e);
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginScreen.this, "Logged in SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, SplashScreen.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginScreen.this, "Authentication Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//--------------------------------------------------- End of Login using Google ---------------------------------------------------



}