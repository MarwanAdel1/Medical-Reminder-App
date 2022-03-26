package com.example.medicalreminder.model;

import androidx.lifecycle.LiveData;

import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineViewInterface;
import com.example.medicalreminder.screens.home_screen.presenter.MedicationPresenterInterface;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.List;
import java.util.Map;

public interface MedicineRepoInterface {
    public void addMedicine(Medicine medicine, MedicineNotification medicineNotification, AddMedicineViewInterface addMedicineViewInterface);

    public void addMedicineToDatabase(MedicineNotification medicineNotification, AddMedicineViewInterface addMedicineViewInterface);

    public LiveData<List<MedicineNotification>> getTodayNotificationFromRoom(String date);

    public void deleteMedicine(String medName, MedicineNotification medicineNotification, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface);

    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface);


    public void getMedicens(String email, MedicationPresenterInterface medicationPresenterInterface);

    public void returnMedicines(List<Medicine> active, List<Medicine> inactive, MedicationPresenterInterface medicationPresenterInterface);

}
