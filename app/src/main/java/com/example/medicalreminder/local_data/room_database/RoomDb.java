package com.example.medicalreminder.local_data.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.medicalreminder.pojo.MedicineNotification;

@Database(entities = {MedicineNotification.class}, version = 1)
public abstract class RoomDb extends RoomDatabase {
    private static RoomDb roomDb = null;

    public abstract MedicineDao movieDao();

    public static synchronized RoomDb getInstance(Context context) {
        if (roomDb == null) {
            roomDb = Room.databaseBuilder(context, RoomDb.class, "Medications_Database").build();
        }
        return roomDb;
    }
}
