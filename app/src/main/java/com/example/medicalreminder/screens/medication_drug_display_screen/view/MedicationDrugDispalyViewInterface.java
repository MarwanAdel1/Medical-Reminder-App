package com.example.medicalreminder.screens.medication_drug_display_screen.view;

import com.example.medicalreminder.pojo.MedicineNotification;

import java.util.List;

public interface MedicationDrugDispalyViewInterface {
    public void updateUiOnSuccess();
    public void updateUiOnFailure();
    public void notifyAddFromDatabase(List<MedicineNotification> medicineNotifications);
}
