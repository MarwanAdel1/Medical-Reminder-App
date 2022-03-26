package com.example.medicalreminder.screens.add_medication_screen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedFormFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedHowManyDaysYouWantToRepeatFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedInstructionFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedNameFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedOnSaveFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedReasonFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedRefillRemindarFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedRepeatFrequencyFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedRepeatingPeriodFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedStrengthFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedTakingTimeForDayFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedTakingTimeForWeekFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedTimePeriodFragment;
import com.example.medicalreminder.screens.add_medication_screen.fragments.AddMedTreatmentDurationFragment;
import com.example.medicalreminder.screens.add_medication_screen.presenter.AddMedicinePresenter;
import com.example.medicalreminder.screens.add_medication_screen.presenter.AddMedicinePresenterInterface;
import com.example.medicalreminder.work_manager.medication_notification.WorkManagerAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMedicationActivityScreen extends AppCompatActivity implements AddMedicineFragmentsCommunicator, AddMedicineViewInterface {
    private Medicine medicine = new Medicine();
    private Fragment[] medicineFragment = new Fragment[16];
    private FragmentManager fragmentManager;
    private int currentFragment = 0;
    private boolean dayFlag = false;
    private int count = 1;
    private int choice;
    public static List<DoseTime> doses = new ArrayList<>();
    public static List<Integer> daysNumber;
    private int daysLoop = 0;

    private String timePeriod = "Morning";


    private AddMedicinePresenterInterface addMedicinePresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daysNumber = new ArrayList<>();
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
        medicineFragment[6] = new AddMedTimePeriodFragment(this);  /// Morning.... Evening
        medicineFragment[7] = new AddMedTakingTimeForDayFragment(this, timePeriod);
        medicineFragment[8] = new AddMedTakingTimeForWeekFragment(this, medicine.getMedRepeatingPerWeek());


        medicineFragment[9] = new AddMedTimePeriodFragment(this);  /// Morning.... Evening
        medicineFragment[10] = new AddMedTakingTimeForDayFragment(this, timePeriod);

        medicineFragment[11] = new AddMedTreatmentDurationFragment(this);

        medicineFragment[12] = new AddMedHowManyDaysYouWantToRepeatFragment(this, medicine);

        medicineFragment[13] = new AddMedOnSaveFragment(this, medicine);

        medicineFragment[14] = new AddMedRefillRemindarFragment(this);
        medicineFragment[15] = new AddMedInstructionFragment(this);


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
        if (currentFragment == 11 || currentFragment == 13 || currentFragment == 8 || currentFragment == 15) {
            if (currentFragment == 11) {
                currentFragment -= 5;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            } else if (currentFragment == 13 && medicine.getActive() == 0) {
                currentFragment -= 8;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            } else if (currentFragment == 13 && medicine.getActive() == 1) {
                currentFragment -= 2;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            } else if (currentFragment == 8) {
                currentFragment -= 3;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            } else if ( currentFragment == 15) {
                currentFragment -= 2;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            }
        } else {
            if (currentFragment > 0 && (currentFragment != 8 || currentFragment != 11 || currentFragment != 13 || currentFragment != 15)) {
                currentFragment--;
                fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
            } else {
                finish();
            }
        }
    }

    @Override
    public void nextFragment(int nextFragment) {
        if ((currentFragment + nextFragment) < 16) {
            currentFragment += nextFragment;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }
    }


    @Override
    public void setMedName(String name) {//0  //from add med name fragment
        medicine.setMedName(name);
        nextFragment(1);
    }

    @Override
    public void setMedForm(String medicineForm) {//1  //from add med form fragment
        medicine.setMedForm(medicineForm);
        nextFragment(1);
    }

    @Override
    public void setMedStrength(int strength, String unit) {//2  //from add med strength fragment
        medicine.setMedStrength(strength);
        medicine.setMedStrengthUnit(unit);
        nextFragment(1);
    }

    @Override
    public void setMedReason(String reason) {//3  // from add med reason fragment
        medicine.setMedReason(reason);
        nextFragment(1);
    }

    @Override
    public void setMedRepeatingFrequency(String repeatingFrequency) { //4 /// Yes - No - As needed  *** Frequency fragment ***
        if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[0])) { /// repeated yes
            repeatingFrequency = "1";
            medicine.setActive(1);
            medicine.setMedRepeatingFrequency(Integer.parseInt(repeatingFrequency));
            nextFragment(1);
        } else if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[1])) { /// repeated no
            repeatingFrequency = "2";
            medicine.setActive(1);
            medicine.setMedRepeatingFrequency(Integer.parseInt(repeatingFrequency));
            nextFragment(1);
        } else if (repeatingFrequency.equalsIgnoreCase(AddMedRepeatFrequencyFragment.medRepeatFrequency[2])) { /// only as needed
            repeatingFrequency = "0";
            medicine.setActive(0);
            medicine.setMedRepeatingFrequency(Integer.parseInt(repeatingFrequency));
            nextFragment(9);//////////////
        }

    }


    @Override
    public void setMedRepeatingPeriod(int choice, Medicine medicine) { //5 ///from  repeating period fragment /// 1 for daily - 2 for not daily
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Choice : " + choice);
        this.choice = choice;
        this.medicine = medicine;
        if (choice == 1) {
            nextFragment(1);
        } else if (choice == 2) {
            dayFlag = false;
            nextFragment(3);
        }
    }

    @Override
    public void setMedNumberOfRepeatingPerday(int numberOfPills, int hour, int minute) {///7  /// from add taking time per day fragment
        medicine.setMedNumberOfPillsPerDose(numberOfPills);


        DoseTime doseTime = new DoseTime();
        doseTime.setHour(hour);
        doseTime.setMinute(minute);

        doses.add(doseTime);
        if (medicine.getMedRepeatingPerWeek() == 0 && medicine.getMedRepeatingPerDay() > 0) { //daily
            if (count < medicine.getMedRepeatingPerDay()) {
                count++;

                previousFragment();
            } else {
                dayFlag = true;
                count = 1;
                nextFragment(4);
            }
        } else if (medicine.getMedRepeatingPerWeek() > 0 && medicine.getMedRepeatingPerDay() == 0) { // repeated
            count = 1;
            nextFragment(1);
        }
    }


    @Override
    public void setMedNumberOfRepeatedDayPerWeek(int dayNumber) {
        if (daysLoop < medicine.getMedRepeatingPerWeek()) {
            boolean flag = false; // not repeated in list
            for (int i = 0; i < daysNumber.size() && !flag; i++) {
                if (daysNumber.get(i) == dayNumber) {
                    flag = true;
                    Toast.makeText(AddMedicationActivityScreen.this, "Already Selected", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            if (!flag && daysNumber.size() < medicine.getMedRepeatingPerWeek()) {
                Toast.makeText(AddMedicationActivityScreen.this, "Added", Toast.LENGTH_SHORT).show();
                daysNumber.add(dayNumber);
                daysLoop++;
            }
            for (int i = 0; i < daysNumber.size(); i++) {
                Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Hi : " + daysNumber.get(i));
            }
            Log.e(AddMedicationActivityScreen.class.getSimpleName(), "-----------------------------");
        }

        if (daysLoop < medicine.getMedRepeatingPerWeek()) {
            nextFragment(0);
        } else {
            nextFragment(1);
            AddMedTakingTimeForWeekFragment.count = 0;
            daysLoop = 0;
        }
    }

    @Override
    public void saveRepeatedDates(Medicine medicine) {
        nextFragment(1);
    }

    @Override
    public void saveMedicine(Medicine medicine) {
        addMedicineToFirebaseFirestore(medicine);
    }  /// from save fragment


    @Override
    public void addMedicineToFirebaseFirestore(Medicine medicine) {
        MedicineNotification medicineNotification = makeNotificationObject(medicine);
        addMedicinePresenterInterface.addMedicine(medicine, medicineNotification);
        if (medicine.getActive() == 1) {
            addMedicinePresenterInterface.addMedicineToDatabase(medicine, medicineNotification);
        }

    }

    @Override
    public void updateUiAfterAddingSuccess() {
        addMedicinePresenterInterface.getTodayNotification(dateToString(Calendar.getInstance().getTime()), this);
        finish();
    }

    @Override
    public void notifyAddFromDatabase(List<MedicineNotification> medicineNotifications) {
        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Success 3: " + medicineNotifications.size());
        WorkManagerAccess workManagerAccess = WorkManagerAccess.getInstance(this);
        workManagerAccess.setWorkManager(medicineNotifications);
    }

    @Override
    public void sendTimePeriod(String time) {
        timePeriod = time;
        nextFragment(1);
    }

    @Override
    public void setStartDate(int day, int month, int year) {
        if (month < 10) {
            medicine.setStartDate(day + "-0" + month + "-" + year);
        } else {
            medicine.setStartDate(day + "-" + month + "-" + year);
        }
        nextFragment(1);
    }

    @Override
    public void saveMedRefillRemindar(int leastNumber, int totalNumber) {
        medicine.setRefillRemaindarNumber(leastNumber);
        medicine.setTotalMedsNumber(totalNumber);
        previousFragment();
    }

    @Override
    public void saveMedInstruction(String instruction) {
        medicine.setMedInstruction(instruction);
        previousFragment();
    }

    @Override
    public void goToRefillRemindar() {
        nextFragment(1);
    }

    @Override
    public void goToInstruction() {
        nextFragment(2);
    }

    public MedicineNotification makeNotificationObject(Medicine medicine) {
        MedicineNotification data = new MedicineNotification();
        data.setDate(medicine.getStartDate());
        data.setTime("");
        data.setMedicineName(medicine.getMedName());
        data.setUserName("Marwan");
        data.setInstruction(medicine.getMedInstruction());
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