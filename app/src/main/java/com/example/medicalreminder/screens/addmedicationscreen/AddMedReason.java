package com.example.medicalreminder.screens.addmedicationscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.SplashScreen;
import com.example.medicalreminder.pojo.Medicine;

public class AddMedReason extends AppCompatActivity {
    private AutoCompleteTextView medAutoCompleteTextView;
    private Button toRepeatingBt;

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_reason);

        medAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.med_reason_act_id);
        ArrayAdapter<String> medArrayAdapter = new ArrayAdapter<String>(this, R.layout.auto_complete_list_items, SplashScreen.diseases);
        medAutoCompleteTextView.setThreshold(1);
        medAutoCompleteTextView.setAdapter(medArrayAdapter);

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        toRepeatingBt=findViewById(R.id.torepeat_bt);
        toRepeatingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setMedReason(medAutoCompleteTextView.getText().toString());
                Intent nextIntent = new Intent(AddMedReason.this,AddMedRepeatFrequency.class);
                nextIntent.putExtra("obj",medicine);
                startActivity(nextIntent);
            }
        });
    }
}