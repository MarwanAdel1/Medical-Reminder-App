package com.example.medicalreminder.screens.addmedicationscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;

public class AddMedTakingTimeForDay extends AppCompatActivity {
    private Medicine medicine;

    private Button toSaveBt;
    private TextView tx;

    private static int counter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med_taking_time_for_day);

        medicine = (Medicine) getIntent().getSerializableExtra("obj");

        tx=findViewById(R.id.med_repeat_question_id);
        tx.setText("What time of day for Period Number "+counter);

        toSaveBt = findViewById(R.id.tosave_bt);
        toSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter<medicine.getMedRepeatingPerDay()){
                    counter++;
                    recreate();
                }else{
                    Intent nextIntent = new Intent(AddMedTakingTimeForDay.this, AddMedOnSave.class);
                    nextIntent.putExtra("obj",medicine);
                    startActivity(nextIntent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        counter=1;
    }
}