package com.example.medicalreminder.views.medication_drug_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditMedicationDrugScreen extends AppCompatActivity {
    private TextView doneEditTx;
    private EditText medNameEt, medStrengthEt;
    private Button medEditBt;
    private ImageView closeImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication_drug_screen);


        Medicine medicine = (Medicine) getIntent().getSerializableExtra("obj");
        doneEditTx = findViewById(R.id.done_tx_id);
        medNameEt = findViewById(R.id.med_name_edit_et);
        medStrengthEt = findViewById(R.id.med_strength_edit_et);
        medEditBt = findViewById(R.id.med_edit_bt);
        closeImg = findViewById(R.id.close_img_id);

        medNameEt.setText(medicine.getMedName());
        medStrengthEt.setText(String.valueOf(medicine.getMedStrength()));

        doneEditTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMedicine(medicine);
            }
        });

        medEditBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMedicine(medicine);
            }
        });

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void editMedicine(Medicine medicine) {
        Map<String, Object> map = new HashMap<>();
        map.put("medName", medNameEt.getText().toString());
        map.put("medStrength", Double.parseDouble(medStrengthEt.getText().toString()));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medicine.getMedName())
                .update(map);
        MedicationDrugScreen.finishFlag = 1;
        finish();
    }
}