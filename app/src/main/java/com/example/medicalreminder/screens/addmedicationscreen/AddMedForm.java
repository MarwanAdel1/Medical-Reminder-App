package com.example.medicalreminder.screens.addmedicationscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.AdapterClickListener;
import com.example.medicalreminder.adapters.MedAdatpter;
import com.example.medicalreminder.pojo.Medicine;

public class AddMedForm extends AppCompatActivity implements AdapterClickListener {
    private final String[] medForms = {"Pill", "Solution", "Injection", "Powder", "Drops", "Inhaler", "Other"};
    private RecyclerView recyclerView;

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_form);

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        MedAdatpter medAdatpter = new MedAdatpter(this, medForms,this);
        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);
    }

    @Override
    public void updateUi(String medData) {
        medicine.setMedForm(medData);
        Intent intent = new Intent(AddMedForm.this,AddMedStrength.class);
        intent.putExtra("obj",medicine);
        startActivity(intent);
    }
}