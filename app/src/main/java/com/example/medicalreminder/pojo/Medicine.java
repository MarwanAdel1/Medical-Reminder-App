package com.example.medicalreminder.pojo;

import java.io.Serializable;

public class Medicine implements Serializable {
    private String medName;
    private String medForm;
    private double medStrength;
    private String medStrengthUnit;
    private String medReason;
    private int medRepeatingFrequency;  /// 0 for only as needed - 1,2 for repeated
    private int medRepeatingPerDay;
    private int medRepeatingPerWeek;

    public int getMedRepeatingPerDay() {
        return medRepeatingPerDay;
    }

    public void setMedRepeatingPerDay(int medRepeatingPerDay) {
        this.medRepeatingPerDay = medRepeatingPerDay;
    }

    public int getMedRepeatingPerWeek() {
        return medRepeatingPerWeek;
    }

    public void setMedRepeatingPerWeek(int medRepeatingPerWeek) {
        this.medRepeatingPerWeek = medRepeatingPerWeek;
    }

    public int getMedRepeatingFrequency() {
        return medRepeatingFrequency;
    }

    public void setMedRepeatingFrequency(int medRepeatingFrequency) {
        this.medRepeatingFrequency = medRepeatingFrequency;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedForm() {
        return medForm;
    }

    public void setMedForm(String medForm) {
        this.medForm = medForm;
    }

    public double getMedStrength() {
        return medStrength;
    }

    public void setMedStrength(double medStrength) {
        this.medStrength = medStrength;
    }

    public String getMedStrengthUnit() {
        return medStrengthUnit;
    }

    public void setMedStrengthUnit(String medStrengthUnit) {
        this.medStrengthUnit = medStrengthUnit;
    }

    public String getMedReason() {
        return medReason;
    }

    public void setMedReason(String medReason) {
        this.medReason = medReason;
    }
}