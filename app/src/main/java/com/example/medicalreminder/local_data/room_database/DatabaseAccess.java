package com.example.medicalreminder.local_data.room_database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.pojo.MedicineNotification;

import java.util.List;

public class DatabaseAccess implements DatabaseAccessInterface {
    private static DatabaseAccess databaseAccess = null;

    private Context context;

    private MedicineDao medicineDao;
    private LiveData<List<MedicineNotification>> mediciListLiveData;

    private DatabaseAccess(Context context) {
        this.context = context;
        RoomDb database = RoomDb.getInstance(context);
        medicineDao = database.movieDao();
    }

    public static DatabaseAccess getInstance(Context context) {
        if (databaseAccess == null) {
            databaseAccess = new DatabaseAccess(context);
        }
        return databaseAccess;
    }


    @Override
    public synchronized void insertMedicineNotification(MedicineNotification medicineNotification) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(MedicineRepo.class.getSimpleName(),"Bye : "+medicineNotification.getDate());
                Log.e(MedicineRepo.class.getSimpleName(),"Bye : "+medicineNotification.getTime());
                medicineDao.insertMovie(medicineNotification);
            }
        }).start();
    }

    @Override
    public void deleteMedicine(MedicineNotification medicineNotification) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                medicineDao.deleteMedicine(medicineNotification.getMedicineName());
            }
        }).start();
    }

    @Override
    public LiveData<List<MedicineNotification>> getAllMedicine() {
        return medicineDao.getAll();
    }

    @Override
    public LiveData<List<MedicineNotification>> getTodayNotification(String date) {
        mediciListLiveData = medicineDao.getTodaysNotifications(date);
        return mediciListLiveData;
    }


}
