package com.example.medicalreminder.views.add_medication_screen.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.presenter.AddMedicinePresenter;
import com.example.medicalreminder.views.add_medication_screen.presenter.AddMedicinePresenterInterface;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedFormFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedNameFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedOnSaveFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedReasonFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedRepeatFrequencyFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedRepeatingPeriodFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedStrengthFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedTakingTimeForDayFragment;
import com.example.medicalreminder.views.add_medication_screen.view.fragments.AddMedTakingTimeForWeekFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMedicationActivityScreen extends AppCompatActivity implements AddMedicineFragmentsCommunicator, AddMedicineViewInterface {
    private Medicine medicine = new Medicine();
    private Fragment[] medicineFragment = new Fragment[9];
    private FragmentManager fragmentManager;
    private int currentFragment = 0;
    private boolean dayFlag = false;

    private AddMedicinePresenterInterface addMedicinePresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();

        addMedicinePresenterInterface = new AddMedicinePresenter(this);

    }

    public void initFragments() {
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
        if (currentFragment == 7) {
            currentFragment -= 2;
            fragmentManager.beginTransaction().replace(R.id.FragmentContainerView, medicineFragment[currentFragment]).commit();
        }

        if (currentFragment == 8 && dayFlag) {
            currentFragment -= 2;
            dayFlag = false;
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
        addMedicineToFirebaseFirestore(medicine);
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
        dayFlag = true;
        nextFragment(2);
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
        addMedicinePresenterInterface.addMedicine(medicine);
    }
}