package com.example.medicalreminder.screens.medication_drug_edit_screen.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.network_data.Firebase;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugScreen;
import com.example.medicalreminder.screens.medication_drug_edit_screen.presenter.EditMedicationDrugPresenter;
import com.example.medicalreminder.screens.medication_drug_edit_screen.presenter.EditMedicationDrugPresenterInterface;

import java.util.HashMap;
import java.util.Map;

public class EditMedicationDrugScreen extends AppCompatActivity implements EditMedicationDrugViewInterface {
    private TextView doneEditTx;
    private EditText medNameEt, medStrengthEt;
    private Button medDoneEditBt;
    private ImageView closeImg;

    private EditMedicationDrugPresenterInterface editMedicationDrugPresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication_drug_screen);


        Medicine medicine = (Medicine) getIntent().getSerializableExtra("obj");
        doneEditTx = findViewById(R.id.done_tx_id);
        medNameEt = findViewById(R.id.med_name_edit_et);
        medStrengthEt = findViewById(R.id.med_strength_edit_et);
        medDoneEditBt = findViewById(R.id.med_edit_bt);
        closeImg = findViewById(R.id.close_img_id);

        medNameEt.setText(medicine.getMedName());
        medStrengthEt.setText(String.valueOf(medicine.getMedStrength()));


        editMedicationDrugPresenterInterface = new EditMedicationDrugPresenter(this, MedicineRepo.getInstance(this, Firebase.getInstance()));

        doneEditTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMedicine(medicine.getMedName());
            }
        });

        medDoneEditBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMedicine(medicine.getMedName());
            }
        });

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void editMedicine(String medName) {
        Map<String, Object> map = new HashMap<>();
        map.put("medName", medNameEt.getText().toString());
        map.put("medStrength", Double.parseDouble(medStrengthEt.getText().toString()));

        editMedicationDrugPresenterInterface.editThisMedicine(medName,map);
    }

    @Override
    public void updateUi() {
        MedicationDrugScreen.finishFlag = 1;
        finish();
    }
}