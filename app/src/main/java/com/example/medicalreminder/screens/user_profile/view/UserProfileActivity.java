package com.example.medicalreminder.screens.user_profile.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.model.RegisterationRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.screens.user_profile.presenter.UserProfilePresenter;
import com.example.medicalreminder.screens.user_profile.presenter.UserProfilePresenterInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity implements UserProfileActivityInterface{
    EditText name;
    EditText phone;
    EditText date_of_birth;
    TextView save_button;
    ImageView cancel_button;
    static String username;
    String email;

    UserProfilePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        presenterInterface = new UserProfilePresenter(this, RegisterationRepo.getInstance(this, FirebaseAccess.getInstance(), LocalLoginUserData.getInstance(this)));
        initUI();

        email = presenterInterface.getUserEmail();
        presenterInterface.getUserdata(email,this);

        //          Handling buttons
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEdited = name.getText().toString();
                String phoneEdited = phone.getText().toString();
                String dateOfBirthEdited = date_of_birth.getText().toString();

                presenterInterface.saveUserdataAfterEditing(email,nameEdited,phoneEdited, dateOfBirthEdited, UserProfileActivity.this);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    @Override
    public void setUserData(JSONObject data) {
        try {
            name.setText(data.get("name").toString());
            phone.setText(data.get("phone").toString());
            date_of_birth.setText(data.get("dateOfBirth").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(JSONObject dataObject) {
        setUserData(dataObject);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
