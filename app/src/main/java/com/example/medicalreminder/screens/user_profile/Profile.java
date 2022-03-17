package com.example.medicalreminder.screens.user_profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.TabBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class Profile extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText date_of_birth;
    TextView save_button;
    ImageView cancel_button;
    public static String username;
    //              Firebase database object to access firebase's realtime database.
//    databaseReference;
    SharedPreferences userData;
    String email;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference.keepSynced(true);
        userData = getSharedPreferences("user_file", MODE_PRIVATE);
        initUI();

        email = userData.getString("email", "no email");
        setUserInfo();

        //          Handling buttons
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //      To save data in shared preferences
//                        userData.edit().putString("name", name.getText().toString()).commit();
//                        userData.edit().putString("phone", phone.getText().toString()).commit();
//                        userData.edit().putString("dateOfBirth", date_of_birth.getText().toString()).commit();

                        //      To save updates in firebase realtime database
                        databaseReference.child(email).child("name").setValue(name.getText().toString());
                        databaseReference.child(email).child("phone").setValue(phone.getText().toString());
                        databaseReference.child(email).child("date of birth").setValue(date_of_birth.getText().toString());
                        setUserInfo();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                System.out.println("name: " + name.getText().toString() + ", Phone: " + phone.getText().toString() + ", date of birth: " + date_of_birth.getText().toString());
                System.out.println("User Data: " + userData.getString("name","have") + "\n " + userData.getString("phone","have") + " \n " + userData.getString("dateOfBirth","have"));
                Toast.makeText(Profile.this, "Your information updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                startActivity(new Intent(Profile.this, TabBar.class));
            }
        });

    }

    private void initUI(){
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        date_of_birth = findViewById(R.id.date_of_birth);
        save_button = findViewById(R.id.save_profile_textview);
        cancel_button = findViewById(R.id.close_img_id);
    }

    private void setUserInfo(){
        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue(String.class));
                username = snapshot.child("name").getValue(String.class);
                phone.setText(snapshot.child("phone").getValue(String.class));
                date_of_birth.setText(snapshot.child("date of birth").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}