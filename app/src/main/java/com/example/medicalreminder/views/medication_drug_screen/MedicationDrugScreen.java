package com.example.medicalreminder.views.medication_drug_screen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;

public class MedicationDrugScreen extends AppCompatActivity {
    private ImageView backImage, deleteImage, editImage;
    private Button refillButton, cancelBtn, deleteBtn;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_drug_screen);


        backImage = findViewById(R.id.close_img_id);
        deleteImage = findViewById(R.id.delete_image_id);
        editImage = findViewById(R.id.edit_image_id);
        refillButton = findViewById(R.id.refill_bt);


        createDialog();


        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                Intent intent = new Intent(MedicationDrugScreen.this, EditMedicationDrugScreen.class);
                startActivity(intent);
            }
        });

        refillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void createDialog() {
        dialog = new Dialog(this);
        //set content
        dialog.setContentView(R.layout.delete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancelBtn = (Button) dialog.findViewById(R.id.cancel_dialog_button);
        deleteBtn = (Button) dialog.findViewById(R.id.delete_dialog_button);
    }
}