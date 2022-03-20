package com.example.medicalreminder.screens.add_medication_screen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;

public interface AddMedicinePresenterInterface {
    public void addMedicine(Medicine medicine, MedicineNotification medicineNotification);

    public void getTodayNotification(String date, LifecycleOwner lifecycleOwner);

    public void addMedicineToDatabase(Medicine medicine, MedicineNotification medicineNotification);
}
