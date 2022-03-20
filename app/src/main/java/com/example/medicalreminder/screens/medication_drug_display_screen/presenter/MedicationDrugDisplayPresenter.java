package com.example.medicalreminder.screens.medication_drug_display_screen.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;

import java.util.List;

public class MedicationDrugDisplayPresenter implements MedicationDrugDisplayPresenterInterface {
    private MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface;
    private MedicineRepoInterface medicineRepoInterface;

    public MedicationDrugDisplayPresenter(MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface, MedicineRepoInterface medicineRepoInterface) {
        this.medicationDrugDispalyViewInterface = medicationDrugDispalyViewInterface;
        this.medicineRepoInterface = medicineRepoInterface;
    }

    @Override
    public void deleteThisMedicine(String medName, MedicineNotification medicineNotification) {
        medicineRepoInterface.deleteMedicine(medName, medicineNotification, medicationDrugDispalyViewInterface);
    }

    @Override
    public void getTodayNotification(String date, LifecycleOwner lifecycleOwner) {
        medicineRepoInterface.getTodayNotificationFromRoom(date).observe(lifecycleOwner, new Observer<List<MedicineNotification>>() {
            @Override
            public void onChanged(List<MedicineNotification> medicineNotifications) {
                medicationDrugDispalyViewInterface.notifyAddFromDatabase(medicineNotifications);
            }
        });
    }
}
