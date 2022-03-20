package com.example.medicalreminder.screens.notification_dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;

public class MedicineNotificationDialog extends AppCompatActivity {
    private Dialog dialog;

    private Button cancelBtn, deleteBtn;
    private TextView medNameDialog;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_notification_dialog);

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
        dialog.setContentView(R.layout.medicine_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
}