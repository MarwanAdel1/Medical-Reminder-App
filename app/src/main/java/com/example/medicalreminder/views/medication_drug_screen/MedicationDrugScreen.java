package com.example.medicalreminder.views.medication_drug_screen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MedicationDrugScreen extends AppCompatActivity {
    private ImageView backImage, deleteImage, editImage;
    private Button refillButton, cancelBtn, deleteBtn;
    private TextView medName, medStrength, medNameDialog;

    private Dialog dialog;
    public static int finishFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_drug_screen);

        backImage = findViewById(R.id.close_img_id);
        deleteImage = findViewById(R.id.delete_image_id);
        editImage = findViewById(R.id.edit_image_id);
        refillButton = findViewById(R.id.refill_bt);
        medName = findViewById(R.id.med_name_id);
        medStrength = findViewById(R.id.med_strength_id);

        Medicine medicine = (Medicine) getIntent().getSerializableExtra("obj");
        medName.setText(medicine.getMedName());
        medStrength.setText(medicine.getMedStrength() + " " + medicine.getMedStrengthUnit());

        createDialog(medicine.getMedName());

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishFlag=0;
                Intent intent = new Intent(MedicationDrugScreen.this, EditMedicationDrugScreen.class);
                intent.putExtra("obj", medicine);
                startActivity(intent);
            }
        });

        refillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(finishFlag==1){
            finishFlag=0;
            finish();
        }
    }

    private void createDialog(String medName) {
        dialog = new Dialog(this);
        //set content
        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        medNameDialog = (TextView) dialog.findViewById(R.id.name_delete_dialog_tx_id);
        cancelBtn = (Button) dialog.findViewById(R.id.cancel_dialog_button);
        deleteBtn = (Button) dialog.findViewById(R.id.delete_dialog_button);

        medNameDialog.setText("Delete " + medName);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMedicine(medName);
            }
        });
    }


    public void deleteMedicine(String medName){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.cancel();
                        Toast.makeText(MedicationDrugScreen.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.cancel();
                        Toast.makeText(MedicationDrugScreen.this, "Deleted Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}