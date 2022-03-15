package com.example.medicalreminder.network_data;

import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.Map;

public interface FirebaseInterface {
    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface);

    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface);
}
