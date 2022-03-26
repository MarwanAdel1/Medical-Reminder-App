package com.example.medicalreminder.screens.home_screen.view;

import com.example.medicalreminder.pojo.Medicine;

import java.util.List;

public interface MedicationFragmentInterface {
    void retuenMedicines(List<Medicine> active,List<Medicine> inactive);
}
