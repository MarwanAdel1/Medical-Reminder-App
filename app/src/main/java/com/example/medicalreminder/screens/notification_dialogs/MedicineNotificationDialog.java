package com.example.medicalreminder.screens.notification_dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.room_database.DatabaseAccess;
import com.example.medicalreminder.pojo.MedicineNotification;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicineNotificationDialog extends AppCompatActivity {
    private Dialog dialog;

    private ImageView getDeleteButton, editButton, takeButton, skipButton, rescheduleButton;
    private TextView medicineName, medicineDetails, medicineTime;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_dailouge);

        getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        createDialog();
        dialog.show();

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.medic);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null) {
            dialog.dismiss();
            dialog=null;
        }
    }

    private void createDialog() {
        dialog = new Dialog(this);
        //set content
        dialog.setContentView(R.layout.notification_dailouge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        Date today = new Date();
        String selectedDate = dtf.format(today.getTime());
        DatabaseAccess.getInstance(this).getTodayNotification(selectedDate).observe(this, new Observer<List<MedicineNotification>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<MedicineNotification> medicineNotifications) {
                if(medicineNotifications != null){
                    for(int i=0 ; i<medicineNotifications.size(); i++){
//                        if(medicineNotifications.get(i).getTime() == )
                        Log.i("TAG", "onChanged: " + medicineNotifications.get(i).getTime());
                        LocalDateTime now = LocalDateTime.now();
                        String time = now.getHour() + ":" + now.getMinute();
                        Log.i("TAG", "onChanged: time " + time);
                        if((medicineNotifications.get(i).getTime()).equals(time)){
                            Log.i("TAG", "name: " + medicineNotifications.get(i).getMedicineName());
                            medicineName.setText(medicineNotifications.get(i).getMedicineName());
                            medicineTime.setText("Scheduled for " + medicineNotifications.get(i).getTime());
                            String strength = String.valueOf(medicineNotifications.get(i).getStrength());
                            String strengthUnit = medicineNotifications.get(i).getStrenthUnit();
                            medicineDetails.setText(strength + strengthUnit);
                        }
                    }
                }
            }
        });
//        medNameDialog = (TextView) dialog.findViewById(R.id.name_delete_dialog_tx_id);
//        cancelBtn = (Button) dialog.findViewById(R.id.cancel_dialog_button);
//        deleteBtn = (Button) dialog.findViewById(R.id.delete_dialog_button);
//
//        medNameDialog.setText("Delete " + medName);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                mediaPlayer.stop();
//                finish();
//            }
//        });
//
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OneTimeWorkRequest mywork =
//                        new OneTimeWorkRequest.Builder(Reminder.class)
//                                .setInitialDelay(1, TimeUnit.MINUTES)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
//                                .build();
//
//                WorkManager.getInstance().enqueue(mywork);
//                dialog.dismiss();
//                mediaPlayer.stop();
//                finish();
//            }
//        });
    }

    void init(){
        getDeleteButton = findViewById(R.id.delete_btn);
        editButton = findViewById(R.id.edite_btn);
        takeButton = findViewById(R.id.take_btn);
        skipButton = findViewById(R.id.skip_btn);
        rescheduleButton = findViewById(R.id.reschedule_btn);
        medicineName = findViewById(R.id.medicine_name);
        medicineDetails = findViewById(R.id.medicine_details);
        medicineTime = findViewById(R.id.medicine_time);
    }
}