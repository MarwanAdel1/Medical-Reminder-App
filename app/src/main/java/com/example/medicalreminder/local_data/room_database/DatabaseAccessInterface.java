package com.example.medicalreminder.local_data.room_database;

import androidx.lifecycle.LiveData;

import com.example.medicalreminder.pojo.MedicineNotification;

import java.util.List;

public interface DatabaseAccessInterface {
    public void insertMedicineNotification(MedicineNotification medicineNotification);

    public void deleteMedicine(MedicineNotification medicineNotification);

    public LiveData<List<MedicineNotification>> getAllMedicine();

    public LiveData<List<MedicineNotification>> getTodayNotification(String date);
}
