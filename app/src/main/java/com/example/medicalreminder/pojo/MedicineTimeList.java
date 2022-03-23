package com.example.medicalreminder.pojo;

import java.io.Serializable;

public class MedicineTimeList implements Serializable {
    private Medicine medicine;
    private String time;

    public MedicineTimeList(Medicine medicine, String time){
        this.medicine = medicine;
        this.time = time;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public String getTime() {
        return time;
    }
}
