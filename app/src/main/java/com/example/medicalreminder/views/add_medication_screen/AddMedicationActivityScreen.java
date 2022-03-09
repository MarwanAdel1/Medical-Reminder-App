package com.example.medicalreminder.views.add_medication_screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedFormFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedNameFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedOnSaveFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedReasonFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedRepeatFrequencyFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedRepeatingPeriodFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedStrengthFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedTakingTimeForDayFragment;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedTakingTimeForWeekFragment;

public class AddMedicationActivityScreen extends AppCompatActivity implements AddMedicineFragmentsCommunicator {

    Medicine medicine = new Medicine();
    Fragment[] medicineFragment = new Fragment[9];
    FragmentManager fragmentManager;
    int currentFragment = 0;
    boolean dayFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();

    }

    void initFragments() {
        medicineFragment[0] = new AddMedNameFragment(this);
        medicineFragment[1] = new AddMedFormFragment(this);
        medicineFragment[2] = new AddMedStrengthFragment(this, medicine);
        medicineFragment[3] = new AddMedReasonFragment(this);
        medicineFragment[4] = new AddMedRepeatFrequencyFragment(this);
        medicineFragment[5] = new AddMedRepeatingPeriodFragment(this, medicine);
        medicineFragment[6] = new AddMedTakingTimeForDayFragment(this);
        medicineFragment[7] = new AddMedTakingTimeForWeekFragment(this);
        medicineFragment[8] = new AddMedOnSaveFragment(this, medicine);


    }


    @Override
    public void onBackPressed() {
        if (currentFragment == 7 ) {
            currentFragment -= 2;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }

        if(currentFragment==8 && dayFlag){
            currentFragment -= 2;
            dayFlag=false;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }

        if (currentFragment > 0 && currentFragment != 7) {
            currentFragment--;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        } else {
            finish();
        }
    }

    @Override
    public void nextFragment(int nextFragment) {
        if ((currentFragment + nextFragment) < 9) {
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
        ///// save on database here
        finish();
    }

    @Override
    public void setMedReason(String reason) {
        medicine.setMedReason(reason);
        nextFragment(1);
    }

    @Override
    public void setMedNumberOfRepeatingPerday() {
        for (int i = 0; i < medicine.getMedRepeatingPerDay(); i++) {
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }
        dayFlag=true;
        nextFragment(2);
    }

    @Override
    public void setMedRepeatingPeriod(int choice,Medicine medicine) {
        this.medicine = medicine;
        if (choice == 1) {
            nextFragment(1);
        } else if (choice == 2) {
            nextFragment(2);
        }
    }

}