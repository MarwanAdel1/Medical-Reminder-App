package com.example.medicalreminder.screens.medicine_list_home;

public class MedicineInfoHome {
    String medicinetime;
    String medicineName;
    String medicineInfo;
    String missed;

    public MedicineInfoHome(String date, String name, String info, String _missed)
    {
        medicinetime = date;
        medicineName = name;
        medicineInfo = info;
        missed = _missed;
    }


    public void setMedicineTime(String medicinetime) {
        this.medicinetime = medicinetime;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setMedicineInfo(String medicineInfo) {
        this.medicineInfo = medicineInfo;
    }

    public void setMissed(String missed) {
        this.missed = missed;
    }

    public String getMedicineTime() {
        return medicinetime;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getMedicineInfo() {
        return medicineInfo;
    }

    public String getMissed() {
        return missed;
    }
}
