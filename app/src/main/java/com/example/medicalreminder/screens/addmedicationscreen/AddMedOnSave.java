package com.example.medicalreminder.screens.addmedicationscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;

public class AddMedOnSave extends AppCompatActivity {
    private Medicine medicine;

    private TextView medDetailsTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_on_save);

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        medDetailsTx = findViewById(R.id.med_details_tx_id);
        medDetailsTx.setText(medicine.getMedName()+", "
                +medicine.getMedForm()+", "
                +medicine.getMedStrength()+" "
                +medicine.getMedStrengthUnit());

    }
}