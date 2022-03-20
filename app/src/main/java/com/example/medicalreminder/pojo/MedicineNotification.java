package com.example.medicalreminder.pojo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.medicalreminder.local_data.room_database.RoomConverter;

@TypeConverters(RoomConverter.class)
@Entity(tableName = "Doses_time", primaryKeys = {"date", "time", "userName", "medicineName"})
public class MedicineNotification {

    @NonNull
    @ColumnInfo(defaultValue = "date")
    private String date;

    @NonNull
    @ColumnInfo(defaultValue = "time")
    private String time;

    @NonNull
    @ColumnInfo(defaultValue = "user")
    private String userName;

    @NonNull
    @ColumnInfo(defaultValue = "medicine_name")
    private String medicineName;

    @ColumnInfo(defaultValue = "medicine_last_taken_date")
    private String medLastTakenDate;

    @ColumnInfo(defaultValue = "medicine_last_taken_time")
    private String medLastTakenTime;

    @ColumnInfo(defaultValue = "medicine_instruction")
    private String instruction;

    @ColumnInfo(defaultValue = "medicine_strength")
    private double strength;

    @ColumnInfo(defaultValue = "medicine_strength_unit")
    private String strenthUnit;

    /*
    @NonNull
    @TypeConverters(RoomConverter.class)
    private Map<String, List<DoseTime>> doses;
*/

    public String getMedLastTakenDate() {
        return medLastTakenDate;
    }

    public void setMedLastTakenDate(String medLastTakenDate) {
        this.medLastTakenDate = medLastTakenDate;
    }

    public String getMedLastTakenTime() {
        return medLastTakenTime;
    }

    public void setMedLastTakenTime(String medLastTakenTime) {
        this.medLastTakenTime = medLastTakenTime;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public String getStrenthUnit() {
        return strenthUnit;
    }

    public void setStrenthUnit(String strenthUnit) {
        this.strenthUnit = strenthUnit;
    }

//
//    @TypeConverters(RoomConverter.class)
//    public Map<String, List<DoseTime>> getDoses() {
//        return doses;
//    }
//
//    @TypeConverters(RoomConverter.class)
//    public void setDoses(Map<String, List<DoseTime>> doses) {
//        this.doses = doses;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }


    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(@NonNull String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
