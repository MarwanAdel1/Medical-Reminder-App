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

public class AddMedRepeatFrequency extends AppCompatActivity implements AdapterClickListener {
    private final String[] medRepeatFrequency = {"Yes", "No", "Only as needed"};
    private RecyclerView recyclerView;

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_repeat_frequency);


        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        MedAdatpter medAdatpter = new MedAdatpter(this, medRepeatFrequency, this);
        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);
    }

    @Override
    public void updateUi(String medData) {
        Intent intent = null;
        if (medData.equalsIgnoreCase(medRepeatFrequency[0])) { /// repeated yes
            medData = "1";
            intent = new Intent(AddMedRepeatFrequency.this, AddMedRepeatingPeriod.class);
        } else if (medData.equalsIgnoreCase(medRepeatFrequency[1])) { /// repeated no
            medData = "2";
            intent = new Intent(AddMedRepeatFrequency.this, AddMedRepeatingPeriod.class);
        } else {
            if (medData.equalsIgnoreCase(medRepeatFrequency[2])) { /// only as needed
                medData = "0";
                //intent = new Intent(AddMedRepeatFrequency.this,);
            }
        }
        medicine.setMedRepeatingFrequency(Integer.parseInt(medData));
        intent.putExtra("obj", medicine);
        startActivity(intent);
    }
}