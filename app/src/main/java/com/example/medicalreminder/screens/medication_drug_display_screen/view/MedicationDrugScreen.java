package com.example.medicalreminder.screens.medication_drug_display_screen.view;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.room_database.DatabaseAccess;
import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.medication_drug_display_screen.presenter.MedicationDrugDisplayPresenter;
import com.example.medicalreminder.screens.medication_drug_display_screen.presenter.MedicationDrugDisplayPresenterInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugScreen;
import com.example.medicalreminder.work_manager.medication_notification.WorkManagerAccess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MedicationDrugScreen extends AppCompatActivity implements MedicationDrugDispalyViewInterface {
    private ImageView backImage, deleteImage, editImage;
    private Button refillButton, cancelBtn, deleteBtn;
    private TextView medName, medStrength, medNameDialog;

    private Dialog dialog;
    public static int finishFlag = 0;

    private MedicationDrugDisplayPresenterInterface medicationDrugDisplayPresenterInterface;
    private Medicine medicine;

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

        medicine = (Medicine) getIntent().getSerializableExtra("obj");
        medName.setText(medicine.getMedName());
        medStrength.setText(medicine.getMedStrength() + " " + medicine.getMedStrengthUnit());

        medicationDrugDisplayPresenterInterface = new MedicationDrugDisplayPresenter(this, MedicineRepo.getInstance(this, FirebaseAccess.getInstance(), DatabaseAccess.getInstance(this)));

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
                finishFlag = 0;
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
        if (finishFlag == 1) {
            finishFlag = 0;
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
                MedicineNotification medicineNotification = makeNotificationObject(medicine);
                medicationDrugDisplayPresenterInterface.deleteThisMedicine(medName, medicineNotification);
            }
        });
    }

    public MedicineNotification makeNotificationObject(Medicine medicine) {
        MedicineNotification data = new MedicineNotification();
        data.setDate(medicine.getStartDate());
        data.setTime("");
        data.setMedicineName(medicine.getMedName());
        data.setUserName("Marwan");
        data.setInstruction("Before");
        data.setMedLastTakenDate("");
        data.setMedLastTakenTime("");
        data.setStrength(medicine.getMedStrength());
        data.setStrenthUnit(medicine.getMedStrengthUnit());


        return data;
    }

    public String dateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String timeToString(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    @Override
    public void updateUiOnSuccess() {
        dialog.cancel();
        medicationDrugDisplayPresenterInterface.getTodayNotification(dateToString(new Date()), this);
        Toast.makeText(MedicationDrugScreen.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUiOnFailure() {
        dialog.cancel();
        Toast.makeText(MedicationDrugScreen.this, "Deleted Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyAddFromDatabase(List<MedicineNotification> medicineNotifications) {
        WorkManagerAccess workManagerAccess = WorkManagerAccess.getInstance(this);
        workManagerAccess.setWorkManager(medicineNotifications);
        finish();
    }
}