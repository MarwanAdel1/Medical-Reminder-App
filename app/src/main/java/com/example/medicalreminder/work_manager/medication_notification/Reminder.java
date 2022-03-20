package com.example.medicalreminder.work_manager.medication_notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class Reminder extends Worker {

    public Reminder(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        Log.e(Reminder.class.getSimpleName(),"Broadcast");
        sendBroadcast();
        return Result.success();
    }


    public void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.example.medicineNotification");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent);
    }
}
