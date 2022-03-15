package com.example.medicalreminder.screens.medication_drug_display_screen.presenter;

import androidx.annotation.NonNull;

import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MedicationDrugDisplayPresenter implements MedicationDrugDisplayPresenterInterface {
    private MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface;
    private MedicineRepoInterface medicineRepoInterface;

    public MedicationDrugDisplayPresenter(MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface, MedicineRepoInterface medicineRepoInterface) {
        this.medicationDrugDispalyViewInterface = medicationDrugDispalyViewInterface;
        this.medicineRepoInterface = medicineRepoInterface;
    }

    @Override
    public void deleteThisMedicine(String medName) {
        medicineRepoInterface.deleteMedicine(medName,medicationDrugDispalyViewInterface);
    }
}
