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

public class AddMedication extends AppCompatActivity {
    private AutoCompleteTextView medAutoCompleteTextView;
    private Button toFormBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        medAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.med_name_act_id);
        ArrayAdapter<String> medArrayAdapter = new ArrayAdapter<String>(this, R.layout.auto_complete_list_items, SplashScreen.medicines);
        medAutoCompleteTextView.setThreshold(1);
        medAutoCompleteTextView.setAdapter(medArrayAdapter);


        toFormBt=findViewById(R.id.toform_bt);
        toFormBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medicine medicine = new Medicine();
                medicine.setMedName(medAutoCompleteTextView.getText().toString());
                Intent nextIntent = new Intent(AddMedication.this,AddMedForm.class);
                nextIntent.putExtra("obj",medicine);
                startActivity(nextIntent);
            }
        });

    }


}