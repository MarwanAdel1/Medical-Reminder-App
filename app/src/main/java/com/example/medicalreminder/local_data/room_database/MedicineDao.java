package com.example.medicalreminder.local_data.room_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.medicalreminder.pojo.MedicineNotification;

import java.util.List;

@Dao
public interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MedicineNotification medicine);

    @Query("Select * from doses_time where date=:dateQuery and userName='Marwan' order by time asc")
    LiveData<List<MedicineNotification>> getTodaysNotifications(String dateQuery);

    @Query("Delete from doses_time where medicineName=:medName")
    void deleteMedicine(String medName);

    @Query("Select * from doses_time")
    LiveData<List<MedicineNotification>> getAll();
}
