package com.example.medicalreminder.screens.signup_screen.view;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import com.example.medicalreminder.R;
import com.example.medicalreminder.model.RegisterationRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivity;
import com.example.medicalreminder.screens.signup_screen.presenter.RegisterationPresenter;
import com.example.medicalreminder.screens.signup_screen.presenter.RegisterationPresenterInterface;

public class RegisterationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RegisterationActivityInterface{
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

    private RegisterationPresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        initUI();
        month_spinner.setOnItemSelectedListener(this);
        ArrayAdapter monthAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, months);
        month_spinner.setAdapter(monthAdapter);

        presenterInterface = new RegisterationPresenter(this, RegisterationRepo.getInstance(this, FirebaseAccess.getInstance()));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterationActivity.this, LoginScreenActivity.class));
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

     public void createUser(){
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

                                                presenterInterface.sendUserData(email,name,phone,password,year,month,day,this);
                                            };
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    @Override
    public void registerationOnSuccess() {
        Toast.makeText(RegisterationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterationActivity.this, LoginScreenActivity.class));
    }

    @Override
    public void registerationOnFailure(String message) {
        Toast.makeText(RegisterationActivity.this, message, Toast.LENGTH_SHORT).show();
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
