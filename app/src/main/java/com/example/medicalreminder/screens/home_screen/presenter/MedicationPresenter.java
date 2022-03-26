package com.example.medicalreminder.screens.home_screen.presenter;

import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.home_screen.view.MedicationFragmentInterface;

import java.util.List;

public class MedicationPresenter implements MedicationPresenterInterface {
    private MedicineRepoInterface medicineRepoInterface ;
    private MedicationFragmentInterface medicationFragmentInterface;
    public MedicationPresenter(MedicationFragmentInterface medicationFragmentInterface,MedicineRepoInterface medicineRepoInterface){
        this.medicineRepoInterface = medicineRepoInterface ;
        this.medicationFragmentInterface = medicationFragmentInterface;
    }

    @Override
    public void returnMedicine(List<Medicine> active,List<Medicine> inactive) {
        medicationFragmentInterface.retuenMedicines(active,inactive);
    }

    @Override
    public void getMedicens(String email) {
        medicineRepoInterface.getMedicens(email,this);
    }
}
