package com.example.medicalreminder.work_manager.medication_notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medicalreminder.screens.notification_dialogs.MedicineNotificationDialog;

public class Reminder extends Worker {

    private Context context;
    public Reminder(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @Override
    public Result doWork() {
        Log.e(Reminder.class.getSimpleName(),"Broadcast");
        Intent nextIntent = new Intent(context.getApplicationContext(), MedicineNotificationDialog.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(nextIntent);
//        sendBroadcast();
        return Result.success();
    }


    public void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.example.medicineNotification");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        getApplicationContext().sendBroadcast(intent);
    }
}
