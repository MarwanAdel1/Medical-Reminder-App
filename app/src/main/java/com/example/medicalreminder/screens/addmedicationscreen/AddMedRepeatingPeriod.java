package com.example.medicalreminder.screens.addmedicationscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.adapters.MedAdatpter;
import com.example.medicalreminder.data.MedRepeating;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.AdapterClickListener;

public class AddMedRepeatingPeriod extends AppCompatActivity implements AdapterClickListener {
    private RecyclerView recyclerView;

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_repeating_period);

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        MedAdatpter medAdatpter = null;
        if (medicine.getMedRepeatingFrequency() == 1) {
            medAdatpter = new MedAdatpter(this, MedRepeating.dailyRepeated, this);
        } else if (medicine.getMedRepeatingFrequency() == 2) {
            medAdatpter = new MedAdatpter(this, MedRepeating.notDailyRepeated, this);
        } else if (medicine.getMedRepeatingFrequency() == 0) {

        }

        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);
    }

    @Override
    public void updateUi(String medData) {
        if (medicine.getMedRepeatingFrequency() == 1) { /// repeated daily
            if (medData.equalsIgnoreCase(MedRepeating.dailyRepeated[0])) { // once
                medicine.setMedRepeatingPerDay(1);
                medicine.setMedRepeatingPerWeek(0);
            } else if (medData.equalsIgnoreCase(MedRepeating.dailyRepeated[1])) { // twice
                medicine.setMedRepeatingPerDay(2);
                medicine.setMedRepeatingPerWeek(0);
            } else if (medData.equalsIgnoreCase(MedRepeating.dailyRepeated[2])) { // 3 times
                medicine.setMedRepeatingPerDay(3);
                medicine.setMedRepeatingPerWeek(0);
            } else if (medData.equalsIgnoreCase(MedRepeating.dailyRepeated[3])) { // 4 times
                medicine.setMedRepeatingPerDay(4);
                medicine.setMedRepeatingPerWeek(0);
            } else if (medData.equalsIgnoreCase(MedRepeating.dailyRepeated[4])) { // 6 times
                medicine.setMedRepeatingPerDay(6);
                medicine.setMedRepeatingPerWeek(0);
            }

            Intent dailyIntent = new Intent(AddMedRepeatingPeriod.this, AddMedTakingTimeForDay.class);
            dailyIntent.putExtra("obj", medicine);
            startActivity(dailyIntent);
        } else if (medicine.getMedRepeatingFrequency() == 2) { /// repeated not daily
            if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[0])) { // once a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(1);
            } else if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[1])) { // 2 days a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(2);
            } else if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[2])) { // 3 days a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(3);
            } else if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[3])) { // 4 days a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(4);
            } else if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[4])) { // 5 days a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(5);
            } else if (medData.equalsIgnoreCase(MedRepeating.notDailyRepeated[5])) { // 6 days a week
                medicine.setMedRepeatingPerDay(0);
                medicine.setMedRepeatingPerWeek(6);
            }
        }
    }
}