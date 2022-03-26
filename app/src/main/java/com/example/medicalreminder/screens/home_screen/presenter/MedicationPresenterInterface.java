package com.example.medicalreminder.screens.home_screen.presenter;

import com.example.medicalreminder.pojo.Medicine;

import java.util.List;

public interface MedicationPresenterInterface {
    public void returnMedicine(List<Medicine> active,List<Medicine> inactive);

    public void getMedicens(String email);
}
