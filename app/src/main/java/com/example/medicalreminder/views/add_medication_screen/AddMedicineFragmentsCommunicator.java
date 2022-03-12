package com.example.medicalreminder.views.add_medication_screen;

import com.example.medicalreminder.pojo.Medicine;

public interface AddMedicineFragmentsCommunicator {

    public void nextFragment(int nextFragment);

    public void setMedName(String name);

    public void setMedForm(String form);

    public void setMedStrength(int strength,String unit);

    public void setMedRepeatingFrequency(String repeatingFrequency);

    public void saveMedicine(Medicine medicine);

    public void setMedReason(String reason);

    public void setMedNumberOfRepeatingPerday();

    public void setMedRepeatingPeriod(int choice, Medicine medicine);
}