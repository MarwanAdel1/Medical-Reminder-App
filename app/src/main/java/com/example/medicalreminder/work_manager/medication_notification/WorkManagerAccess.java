package com.example.medicalreminder.work_manager.medication_notification;

import android.content.Context;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicationActivityScreen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkManagerAccess {
    private static WorkManagerAccess workManagerAccess = null;
    private List<WorkRequest> workRequests;

    private Context context;


    private WorkManagerAccess(Context context) {
        workRequests = new ArrayList<>();
        this.context=context;
    }

    public static WorkManagerAccess getInstance(Context context) {
        if (workManagerAccess == null) {
            workManagerAccess = new WorkManagerAccess(context);
        }
        return workManagerAccess;
    }

    public void setWorkManager(List<MedicineNotification> lists) {
        workRequests.clear();

        makeRequests(lists);
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Success 6 : " + workRequests.size());

        WorkManager.getInstance(context).cancelAllWork();
        if (workRequests.size() > 0) {
            WorkManager.getInstance(context).enqueue(workRequests);
        }
    }

    public void makeRequests(List<MedicineNotification> lists) {
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Success 4: " + lists.size());

        for (int i = 0; i < lists.size(); i++) {
            Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Success 5 : " + lists.size());
            makeRequestsReady(lists.get(i).getTime());
            /*
            for(Map.Entry<String,List<DoseTime>> entry : lists.get(i).getDoses().entrySet()){
                if(entry.getKey().equalsIgnoreCase(dateToString(new Date()))){
                    makeRequestsReady(entry.getValue());
                }
            }
*/
        }
    }

    public void makeRequestsReady(String dose) {
        int duration = calcDiffInSec(dose);
        if (duration > 0) {
            OneTimeWorkRequest mywork =
                    new OneTimeWorkRequest.Builder(Reminder.class)
                            .setInitialDelay(duration, TimeUnit.SECONDS)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .build();

            workRequests.add(mywork);
        }
    }

    //12:15
    public int calcDiffInSec(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentDate = simpleDateFormat.format(new Date());

        String[] dateSplit1 = currentDate.split(":");
        String[] dateSplit2 = time.split(":");

        String dateH1 = dateSplit1[0];
        String dateM1 = dateSplit1[1];
        String dateS1 = dateSplit1[2];

        String dateH2 = dateSplit2[0];
        String dateM2 = dateSplit2[1];

        int diffH = Integer.parseInt(dateH2) - Integer.parseInt(dateH1);
        int diffM = Integer.parseInt(dateM2) - Integer.parseInt(dateM1);
        int diffS = Integer.parseInt(dateS1);


        int duration = -1;
        if (diffH >= 0) {
            duration = diffH >= 1 ? ((diffH * 60 * 60) + (diffM * 60) - (60 - diffS)) : diffM >= 1 ? ((diffM * 60) - (60 - diffS)) :
                    (60 - diffS);
        }

        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Duration : " + duration);

        return duration;
    }

    public String dateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

}
