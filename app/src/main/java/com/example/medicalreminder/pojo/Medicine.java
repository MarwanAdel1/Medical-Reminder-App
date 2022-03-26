package com.example.medicalreminder.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Medicine implements Serializable {
    private String medName;
    private String medForm;
    private double medStrength;
    private String medStrengthUnit;
    private String medReason;
    private int medRepeatingFrequency;  /// 0 for only as needed , 1 for yes ,2 for no /// repeated
    private int medRepeatingPerDay;
    private int medRepeatingPerWeek;
    private int medNumberOfPillsPerDose;
    private String startDate;
    private int refillRemaindarNumber;//
    private int totalMedsNumber;//
    private int active;//
    private Map<String,List<DoseTime>> medTimeDosesPerDay;
    private String medInstruction;



    public  Medicine(){}

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

    public int getMedRepeatingFrequency() {
        return medRepeatingFrequency;
    }

    public void setMedRepeatingFrequency(int medRepeatingFrequency) {
        this.medRepeatingFrequency = medRepeatingFrequency;
    }

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

    public int getMedNumberOfPillsPerDose() {
        return medNumberOfPillsPerDose;
    }

    public void setMedNumberOfPillsPerDose(int medNumberOfPillsPerDose) {
        this.medNumberOfPillsPerDose = medNumberOfPillsPerDose;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getRefillRemaindarNumber() {
        return refillRemaindarNumber;
    }

    public void setRefillRemaindarNumber(int refillRemaindarNumber) {
        this.refillRemaindarNumber = refillRemaindarNumber;
    }

    public int getTotalMedsNumber() {
        return totalMedsNumber;
    }

    public void setTotalMedsNumber(int totalMedsNumber) {
        this.totalMedsNumber = totalMedsNumber;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Map<String, List<DoseTime>> getMedTimeDosesPerDay() {
        return medTimeDosesPerDay;
    }

    public void setMedTimeDosesPerDay(Map<String, List<DoseTime>> medTimeDosesPerDay) {
        this.medTimeDosesPerDay = medTimeDosesPerDay;
    }

    public String getMedInstruction() {
        return medInstruction;
    }

    public void setMedInstruction(String medInstruction) {
        this.medInstruction = medInstruction;
    }
}
