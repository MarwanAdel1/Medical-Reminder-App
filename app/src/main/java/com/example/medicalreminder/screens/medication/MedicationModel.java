package com.example.medicalreminder.screens.medication;

public class MedicationModel {
    private int image ;
    private String drugName ;
    private String drugsNumber ;

    public MedicationModel(int image , String drugName, String drugsNumber) {
        this.image = image;
        this.drugName = drugName;
        this.drugsNumber = drugsNumber;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugsNumber() {
        return drugsNumber;
    }

    public void setDrugsNumber(String drugsNumber) {
        this.drugsNumber = drugsNumber;
    }
}
