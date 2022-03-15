package com.example.medicalreminder.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.login_screen.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartSplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
//    Button logout_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_splash_screen);
//        logout_button = findViewById(R.id.logout_btn);

        //      Initialize firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(StartSplashScreen.this, LoginScreen.class));
        }
        else {
            startActivity(new Intent(StartSplashScreen.this, TabBar.class));
        }
        
    }
}