package com.example.medicalreminder.model;

import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.Map;

public interface MedicineRepoInterface {
    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface);

    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface);
}
