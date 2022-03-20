package com.example.medicalreminder.screens.add_medication_screen.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.room_database.DatabaseAccess;
import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.pojo.DoseTime;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineNotification;
import com.example.medicalreminder.screens.add_medication_screen.presenter.AddMedicinePresenter;
import com.example.medicalreminder.screens.add_medication_screen.presenter.AddMedicinePresenterInterface;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedFormFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedNameFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedOnSaveFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedReasonFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedRepeatFrequencyFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedRepeatingPeriodFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedStrengthFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedTakingTimeForDayFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedTakingTimeForWeekFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedTimePeriodFragment;
import com.example.medicalreminder.screens.add_medication_screen.view.fragments.AddMedTreatmentDurationFragment;
import com.example.medicalreminder.work_manager.medication_notification.WorkManagerAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMedicationActivityScreen extends AppCompatActivity implements AddMedicineFragmentsCommunicator, AddMedicineViewInterface {
    private Medicine medicine = new Medicine();
    private Fragment[] medicineFragment = new Fragment[11];
    private FragmentManager fragmentManager;
    private int currentFragment = 0;
    private boolean dayFlag = false;
    private int count = 1;
    private List<DoseTime> doses;

    private String timePeriod = "Morning";


    private AddMedicinePresenterInterface addMedicinePresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doses = new ArrayList<>();

        initFragments();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();

        addMedicinePresenterInterface = new AddMedicinePresenter(this, MedicineRepo.getInstance(this, FirebaseAccess.getInstance(), DatabaseAccess.getInstance(this)));

    }

    public void initFragments() {
        medicineFragment[0] = new AddMedNameFragment(this);
        medicineFragment[1] = new AddMedFormFragment(this);
        medicineFragment[2] = new AddMedStrengthFragment(this, medicine);
        medicineFragment[3] = new AddMedReasonFragment(this);
        medicineFragment[4] = new AddMedRepeatFrequencyFragment(this);
        medicineFragment[5] = new AddMedRepeatingPeriodFragment(this, medicine);
        medicineFragment[6] = new AddMedTimePeriodFragment(this);
        medicineFragment[7] = new AddMedTakingTimeForDayFragment(this, timePeriod);
        medicineFragment[8] = new AddMedTakingTimeForWeekFragment(this);

        medicineFragment[9] = new AddMedTreatmentDurationFragment(this);

        medicineFragment[10] = new AddMedOnSaveFragment(this, medicine);

    }


    @Override
    public void backFragment() {
        previousFragment();
    }

    @Override
    public void backToPreviousActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        previousFragment();
    }

    public void previousFragment() {
        if (currentFragment == 8) {
            currentFragment -= 2;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }

        if (currentFragment == 10 && dayFlag) {
            currentFragment -= 3;
            dayFlag = false;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        } else if (currentFragment == 10 && !dayFlag) {
            currentFragment -= 2;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }

        if (currentFragment > 0 && currentFragment != 8) {
            currentFragment--;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        } else {
            finish();
        }
    }

    @Override
    public void nextFragment(int nextFragment) {
        if ((currentFragment + nextFragment) < 11) {
            currentFragment += nextFragment;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }
    }

    @Override
    public void setMedName(String name) {
        medicine.setMedName(name);
        nextFragment(1);
    }

    @Override
    public void setMedForm(String medicineForm) {
        medicine.setMedForm(medicineForm);
        nextFragment(1);
    }

    @Override
    public void setMedStrength(int strength, String unit) {
        medicine.setMedStrength(strength);
        medicine.setMedStrengthUnit(unit);
        nextFragment(1);
    }

    @Override
    public void setMedRepeatingFrequency(String repeatingFrequency) {
        if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[0])) { /// repeated yes
            repeatingFrequency = "1";
        } else if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[1])) { /// repeated no
            repeatingFrequency = "2";
        } else if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[2])) { /// only as needed
            repeatingFrequency = "0";
        }
        medicine.setMedRepeatingFrequency(Integer.parseInt(repeatingFrequency));
        nextFragment(1);
    }

    @Override
    public void saveMedicine(Medicine medicine) {
        addMedicineToFirebaseFirestore(medicine);
    }

    @Override
    public void setMedReason(String reason) {
        medicine.setMedReason(reason);
        nextFragment(1);
    }


    @Override
    public void setMedNumberOfRepeatingPerday(int numberOfPills, int hour, int minute) {
        if (count < medicine.getMedRepeatingPerDay()) {
            count++;

            medicine.setMedNumberOfPillsPerDose(numberOfPills);

            DoseTime doseTime = new DoseTime();
            doseTime.setHour(hour);
            doseTime.setMinute(minute);

            doses.add(doseTime);
////////
            previousFragment();
        } else {
            medicine.setMedNumberOfPillsPerDose(numberOfPills);

            DoseTime doseTime = new DoseTime();
            doseTime.setHour(hour);
            doseTime.setMinute(minute);

            doses.add(doseTime);/////

            dayFlag = true;
            count = 1;

///////////////////////Doses lazm tfdaa ///////////
            nextFragment(2);
        }
    }

    @Override
    public void setMedRepeatingPeriod(int choice, Medicine medicine) {
        this.medicine = medicine;
        if (choice == 1) {
            nextFragment(1);
        } else if (choice == 2) {
            nextFragment(2);
        }
    }

    @Override
    public void addMedicineToFirebaseFirestore(Medicine medicine) {
        String startDate = medicine.getStartDate();


        Map<String, List<DoseTime>> map = new HashMap<>();

        Calendar c = Calendar.getInstance();
/*
        while (!dateToString(c.getTime()).equalsIgnoreCase(startDate)){
            c.add(Calendar.DATE,1);
        }*/

        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Hello 1: " + dateToString(c.getTime()));
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Hello 2: " + startDate);

        for (int i = 0; i < 30; i++) {
            Date d = c.getTime();
            map.put(dateToString(d), doses);

            c.add(Calendar.DATE, 1);
        }

        medicine.setMedTimeDosesPerDay(map);


        MedicineNotification medicineNotification = makeNotificationObject(medicine);
        addMedicinePresenterInterface.addMedicine(medicine, medicineNotification);
        addMedicinePresenterInterface.addMedicineToDatabase(medicine, medicineNotification);
    }

    @Override
    public void updateUiAfterAddingSuccess() {
        addMedicinePresenterInterface.getTodayNotification(dateToString(Calendar.getInstance().getTime()), this);
    }

    @Override
    public void notifyAddFromDatabase(List<MedicineNotification> medicineNotifications) {
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Success 3: " + medicineNotifications.size());
        WorkManagerAccess workManagerAccess = WorkManagerAccess.getInstance(this);
        workManagerAccess.setWorkManager(medicineNotifications);
        finish();
    }

    @Override
    public void sendTimePeriod(String time) {
        timePeriod = time;
        nextFragment(1);
    }

    @Override
    public void setStartDate(int day, int month, int year) {
        medicine.setStartDate(day + "-" + month + "-" + year);
        nextFragment(1);
    }

    public MedicineNotification makeNotificationObject(Medicine medicine) {
        MedicineNotification data = new MedicineNotification();
        data.setDate(medicine.getStartDate());
        data.setTime("");
        data.setMedicineName(medicine.getMedName());
        data.setUserName("Marwan");
        data.setInstruction("Before");
        data.setMedLastTakenDate("");
        data.setMedLastTakenTime("");
        data.setStrength(medicine.getMedStrength());
        data.setStrenthUnit(medicine.getMedStrengthUnit());


        return data;
    }

    public String dateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String timeToString(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }


}