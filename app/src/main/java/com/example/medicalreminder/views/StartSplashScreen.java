package com.example.medicalreminder.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.login_screen.LoginScreen;
import com.example.medicalreminder.views.signup_screen.Registeration;
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