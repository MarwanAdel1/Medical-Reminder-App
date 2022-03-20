package com.example.medicalreminder.screens.add_medication_screen.view;

import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;

import java.util.List;

public interface AddMedicineViewInterface {
    public void addMedicineToFirebaseFirestore(Medicine medicine);
    public void updateUiAfterAddingSuccess();
    public void notifyAddFromDatabase(List<MedicineNotification> medicineNotifications);
}
