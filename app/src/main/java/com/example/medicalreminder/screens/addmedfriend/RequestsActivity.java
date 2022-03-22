package com.example.medicalreminder.screens.addmedfriend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.medicalreminder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RequestsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);


        bottomNavigationView = findViewById(R.id.request_bottomNavigationView);
        NavController navController = Navigation.findNavController(this ,R.id.request_fragmentContainerView4);
        NavigationUI.setupWithNavController(bottomNavigationView , navController);

    }
}