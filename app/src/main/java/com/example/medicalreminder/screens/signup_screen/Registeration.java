package com.example.medicalreminder.screens.signup_screen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.login_screen.LoginScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registeration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner month_spinner;
    TextView login;
    EditText email_field;
    EditText phone_field;
    EditText name_field;
    EditText password_field;
    EditText password_confirm_field;
    EditText year_field;
    EditText day_field;
    Button signup_button;
    String[] months = {"Month", "January", "February","March", "April","May", "June",
            "July", "August","September", "October","November", "December"};
    String month = months[0];
    //              Firebase database object to access firebase's realtime database.
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medical-reminder-62cc4-default-rtdb.firebaseio.com/");
    //          Firebase elements
    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        initUI();
        month_spinner.setOnItemSelectedListener(this);
        ArrayAdapter monthAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, months);
        month_spinner.setAdapter(monthAdapter);
//        month_spinner.setSelection(0);

        // ----------------------------------------------- Firebase initialization ----------------------------------------------------------------
        firebaseAuth = FirebaseAuth.getInstance();

        // -----------------------------------------------  Handle buttons ------------------------------------------------------------------------

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registeration.this, LoginScreen.class));
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        month = months[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void createUser(){
        String email = email_field.getText().toString().replace('.','*');
        String phone = phone_field.getText().toString();
        String name = name_field.getText().toString();
        String password = password_field.getText().toString();
        String confirm_password = password_confirm_field.getText().toString();
        String year = year_field.getText().toString();
        String day = day_field.getText().toString();

        if(TextUtils.isEmpty(email)){
            email_field.setError("Email can not be empty");
            email_field.requestFocus();
        } else {
            if (TextUtils.isEmpty(phone)) {
                phone_field.setError("Phone can not be empty");
                phone_field.requestFocus();
            } else {
                if (TextUtils.isEmpty(name)) {
                    name_field.setError("Name can not be empty");
                    name_field.requestFocus();
                } else {
                    if (TextUtils.isEmpty(password)) {
                        password_field.setError("Password can not be empty");
                        password_field.requestFocus();
                    } else {
                        if (TextUtils.isEmpty(confirm_password)) {
                            password_confirm_field.setError("Confirm password can not be empty");
                            password_confirm_field.requestFocus();
                        } else {
                            if (TextUtils.isEmpty(year)) {
                                year_field.setError("Year can not be empty");
                                year_field.requestFocus();
                            } else {
                                if (month == "Month") {
                                    Toast.makeText(this, "You have to choose a month", Toast.LENGTH_SHORT).show();
                                    month_spinner.requestFocus();
                                } else {
                                    if (TextUtils.isEmpty(day)) {
                                        day_field.setError("Day can not be empty");
                                        day_field.requestFocus();
                                    } else{
                                        if(!password.equals(confirm_password)){
                                            Toast.makeText(this, "Password and Confirm password must matched", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //  to check if the email already existed in firebase realtie database
                                            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.hasChild(email)) {
                                                        Toast.makeText(Registeration.this, "This email already existed", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //  sending data to firebase realtime database
                                                        databaseReference.child("users").child(email).child("name").setValue(name);
                                                        databaseReference.child("users").child(email).child("phone").setValue(phone);
                                                        databaseReference.child("users").child(email).child("password").setValue(password);
                                                        databaseReference.child("users").child(email).child("date of birth").setValue(year + "/" + month + "/" + day);
                                                        firebaseAuth.createUserWithEmailAndPassword(email.replace('*','.'), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            //          if user registration created successfully
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(Registeration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(Registeration.this,LoginScreen.class));
                                                            } else{
                                                                Toast.makeText(Registeration.this, "Registration Error Ocuured ", Toast.LENGTH_SHORT).show();
                                                                System.out.println("Error Occured in Login: " + task.getException().getMessage());
                                                            }
                                                        }
                                                    });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void initUI(){
        month_spinner = findViewById(R.id.month_field);
        login = findViewById(R.id.login);
        signup_button = findViewById(R.id.signup_btn);
        email_field = findViewById(R.id.email_field);
        phone_field = findViewById(R.id.phone_field);
        name_field = findViewById(R.id.name_field);
        password_field = findViewById(R.id.password_field);
        password_confirm_field = findViewById(R.id.confirm_password_field);
        year_field = findViewById(R.id.year_field);
        day_field = findViewById(R.id.day_field);
    }
}