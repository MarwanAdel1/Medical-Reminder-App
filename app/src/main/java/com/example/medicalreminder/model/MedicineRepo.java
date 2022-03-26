package com.example.medicalreminder.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.medicalreminder.local_data.room_database.DatabaseAccessInterface;
import com.example.medicalreminder.network_data.FirebaseAccessInterface;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineViewInterface;
import com.example.medicalreminder.screens.home_screen.presenter.MedicationPresenterInterface;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;

import java.util.List;
import java.util.Map;

public class MedicineRepo implements MedicineRepoInterface {
    private Context context;
    private FirebaseAccessInterface firebaseAccessInterface;
    private DatabaseAccessInterface databaseAccessInterface;

    private static MedicineRepo medicineRepo = null;


    private MedicineRepo(Context context, FirebaseAccessInterface firebaseAccessInterface, DatabaseAccessInterface databaseAccessInterface) {
        this.context = context;
        this.firebaseAccessInterface = firebaseAccessInterface;
        this.databaseAccessInterface = databaseAccessInterface;
    }

    public static MedicineRepo getInstance(Context context, FirebaseAccessInterface firebaseAccessInterface, DatabaseAccessInterface databaseAccessInterface) {
        if (medicineRepo == null) {
            medicineRepo = new MedicineRepo(context, firebaseAccessInterface, databaseAccessInterface);
        }
        return medicineRepo;
    }

    @Override
    public void addMedicine(Medicine medicine, MedicineNotification medicineNotification, AddMedicineViewInterface addMedicineViewInterface) {
        firebaseAccessInterface.addMedicine(medicine, addMedicineViewInterface);
    }

    @Override
    public void addMedicineToDatabase(MedicineNotification medicineNotification, AddMedicineViewInterface addMedicineViewInterface) {
        databaseAccessInterface.insertMedicineNotification(medicineNotification);
    }

    @Override
    public void deleteMedicine(String medName, MedicineNotification medicineNotification, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface) {
        firebaseAccessInterface.deleteMedicine(medName, medicationDrugDispalyViewInterface);
        databaseAccessInterface.deleteMedicine(medicineNotification);
    }

    @Override
    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface) {
        firebaseAccessInterface.editMedicine(medName, medMap, editMedicationDrugViewInterface);
    }


    @Override
    public LiveData<List<MedicineNotification>> getTodayNotificationFromRoom(String date) {
        return databaseAccessInterface.getTodayNotification(date);
    }


    @Override
    public void getMedicens(String email,MedicationPresenterInterface medicationPresenterInterface) {
        firebaseAccessInterface.getMedication(email,this, medicationPresenterInterface);
    }

    @Override
    public void returnMedicines(List<Medicine> active, List<Medicine> inactive, MedicationPresenterInterface medicationPresenterInterface) {
        medicationPresenterInterface.returnMedicine(active,inactive);
    }


}
