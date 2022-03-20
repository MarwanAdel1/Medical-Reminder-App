package com.example.medicalreminder.screens.add_medication_screen.view;

import com.example.medicalreminder.pojo.Medicine;

public interface AddMedicineFragmentsCommunicator {

    public void nextFragment(int nextFragment);

    public void setMedName(String name);

    public void setMedForm(String form);

    public void setMedStrength(int strength,String unit);

    public void setMedRepeatingFrequency(String repeatingFrequency);

    public void saveMedicine(Medicine medicine);

    public void setMedReason(String reason);

    public void setMedNumberOfRepeatingPerday(int numberOfPills,int hour, int minutes);

    public void setMedRepeatingPeriod(int choice, Medicine medicine);

    public void backFragment();

    public void backToPreviousActivity();

    public void sendTimePeriod(String timePeriod);

    public void setStartDate(int day,int month,int year);

}
