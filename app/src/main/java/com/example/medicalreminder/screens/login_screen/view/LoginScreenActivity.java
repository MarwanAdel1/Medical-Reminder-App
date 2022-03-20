package com.example.medicalreminder.screens.login_screen.view;

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

import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.model.RegisterationRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.screens.login_screen.presenter.LoginScreenPresenter;
import com.example.medicalreminder.screens.login_screen.presenter.LoginScreenPresenterInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.SplashScreen;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenActivityInterface {
    TextView email_field;
    TextView password_field;
    TextView signup;
    Button login_button;
    ImageView twitter_img;
    ImageView google_img;
    ImageView fb_img;

    private LoginScreenPresenterInterface presenterInterface;
    final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();

        presenterInterface = new LoginScreenPresenter(this, RegisterationRepo.getInstance(this, FirebaseAccess.getInstance(), LocalLoginUserData.getInstance(this)));

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
                startActivity(new Intent(LoginScreenActivity.this, RegisterationActivity.class));
            }
        });

        twitter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreenActivity.this, "Available soon ", Toast.LENGTH_SHORT).show();
            }
        });

        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreenActivity.this, "Login using Gmail", Toast.LENGTH_SHORT).show();
                GoogleSignInClient googleSignInClient = presenterInterface.googleSignInClient(LoginScreenActivity.this);
                googleLogin(googleSignInClient);
            }
        });

        fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreenActivity.this, "Login using facebook", Toast.LENGTH_SHORT).show();
                //          for login with facebook
                startActivity(new Intent(LoginScreenActivity.this, Login_fb.class));
            }
        });

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

    @Override
    public void loginUser() {
        String email = email_field.getText().toString().replace('.','*');
        String password = password_field.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_field.setError("Email can not be empty");
            email_field.requestFocus();
        } else {
            if (TextUtils.isEmpty(password)) {
                password_field.setError("Password can not be empty");
                password_field.requestFocus();
            } else {
                presenterInterface.normalLogin(email, password,this);
            }
        }
    }

    @Override
    public void loginOnSuccess() {
        Toast.makeText(LoginScreenActivity.this, " Successfully Logged in", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginScreenActivity.this, SplashScreen.class));
    }

    @Override
    public void loginOnFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void googleLoginOnSuccess() {
        // Sign in success, update UI with the signed-in user's information
        Toast.makeText(LoginScreenActivity.this, "Logged in SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginScreenActivity.this, SplashScreen.class);
        startActivity(intent);
    }

    @Override
    public void googleLoginOnFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //---------------------------------- Start of login with Google -------------------------------------------------
    private void googleLogin(GoogleSignInClient mGoogleSignInClient){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (google_img.isClickable()) {
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                presenterInterface.googleLogin(data, this, this);
            }
        }
    }

    //-------------------------------- End of login with Google ------------------------------------------------------
}
