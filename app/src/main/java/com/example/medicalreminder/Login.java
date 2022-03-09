package com.example.medicalreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView email_field;
    TextView password_field;
    Button login_button;
    ImageView twitter_img;
    ImageView gmail_img;
    ImageView fb_img;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = email_field.getText().toString();
                password = password_field.getText().toString();
                Toast.makeText(Login.this, "Email: " + email + ", Password: " + password, Toast.LENGTH_SHORT).show();
            }
        });

        twitter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Login using Twitter", Toast.LENGTH_SHORT).show();
            }
        });

        gmail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Login using Gmail", Toast.LENGTH_SHORT).show();
            }
        });

        fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Login using facebook", Toast.LENGTH_SHORT).show();
            }
        });



    }

    void initUi(){
        email_field = findViewById(R.id.name_field);
        password_field = findViewById(R.id.password_field);
        login_button = findViewById(R.id.login_btn);
        twitter_img = findViewById(R.id.twitter_login);
        fb_img = findViewById(R.id.fb_login);
        gmail_img = findViewById(R.id.gmail_login);
    }
}