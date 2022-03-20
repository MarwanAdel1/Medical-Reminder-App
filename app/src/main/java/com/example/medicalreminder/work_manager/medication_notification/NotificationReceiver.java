package com.example.medicalreminder.work_manager.medication_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.screens.notification_dialogs.MedicineNotificationDialog;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextIntent = new Intent(context.getApplicationContext(), MedicineNotificationDialog.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(nextIntent);
    }
}