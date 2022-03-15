package com.example.medicalreminder.model;

import android.content.Context;

import com.example.medicalreminder.network_data.FirebaseInterface;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.Map;

public class MedicineRepo implements MedicineRepoInterface {
    private Context context;
    private FirebaseInterface firebaseInterface;

    private static MedicineRepo medicineRepo = null;


    private MedicineRepo(Context context, FirebaseInterface firebaseInterface) {
        this.context = context;
        this.firebaseInterface = firebaseInterface;
    }

    public static MedicineRepo getInstance(Context context, FirebaseInterface firebaseInterface) {
        if (medicineRepo == null) {
            medicineRepo = new MedicineRepo(context, firebaseInterface);
        }
        return medicineRepo;
    }

    @Override
    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface) {
        firebaseInterface.deleteMedicine(medName, medicationDrugDispalyViewInterface);
    }

    @Override
    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface) {
        firebaseInterface.editMedicine(medName, medMap, editMedicationDrugViewInterface);
    }
}
