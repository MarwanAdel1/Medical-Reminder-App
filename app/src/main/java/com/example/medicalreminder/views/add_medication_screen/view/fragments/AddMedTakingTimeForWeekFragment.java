package com.example.medicalreminder.views.add_medication_screen.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedTakingTimeForWeekFragment extends Fragment {
    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedTakingTimeForWeekFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator){
        this.addMedicineFragmentsCommunicator=addMedicineFragmentsCommunicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_med_taking_time_for_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}