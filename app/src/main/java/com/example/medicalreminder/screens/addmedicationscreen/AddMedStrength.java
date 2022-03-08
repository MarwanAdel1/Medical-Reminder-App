package com.example.medicalreminder.screens.addmedicationscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.data.MedStrength;
import com.example.medicalreminder.pojo.Medicine;

public class AddMedStrength extends AppCompatActivity {
    private String[] spinnerValue;
    private String spinnerResult;
    private Medicine medicine;

    private Button toReasonBt;
    private EditText strengthValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_strength);

        toReasonBt = findViewById(R.id.toreson_bt);
        strengthValue = findViewById(R.id.med_strength_et_id);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerResult = spinnerValue[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter arrayAdapter = null;

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        if (medicine.getMedForm().equalsIgnoreCase("pill")) {
            spinnerValue = MedStrength.pillStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("solution")) {
            spinnerValue = MedStrength.solutionStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("injection")) {
            spinnerValue = MedStrength.injectionStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("powder")) {
            spinnerValue = MedStrength.powderStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("drops")) {
            spinnerValue = MedStrength.dropsStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("inhaler")) {
            spinnerValue = MedStrength.inhalerStrength;
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerValue);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);


        toReasonBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine.setMedStrength(Integer.parseInt(strengthValue.getText().toString()));
                medicine.setMedStrengthUnit(spinnerResult);
                Intent intent = new Intent(AddMedStrength.this, AddMedReason.class);
                intent.putExtra("obj", medicine);
                startActivity(intent);
            }
        });
    }
}