package com.example.medicalreminder.screens.medication_drug_display_screen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medicalreminder.pojo.MedicineNotification;

public interface MedicationDrugDisplayPresenterInterface {
    public void deleteThisMedicine(String medName, MedicineNotification medicineNotification);
    public void getTodayNotification(String date, LifecycleOwner lifecycleOwner);
}
