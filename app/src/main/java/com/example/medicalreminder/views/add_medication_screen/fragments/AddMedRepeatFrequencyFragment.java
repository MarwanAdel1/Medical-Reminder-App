package com.example.medicalreminder.views.add_medication_screen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.AdapterClickListener;
import com.example.medicalreminder.adapters.MedAdatpter;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.AddMedicineFragmentsCommunicator;

public class AddMedRepeatFrequencyFragment extends Fragment implements AdapterClickListener {
    public static final String[] medRepeatFrequency = {"Yes", "No", "Only as needed"};
    private RecyclerView recyclerView;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedRepeatFrequencyFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator){
        this.addMedicineFragmentsCommunicator=addMedicineFragmentsCommunicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_med_repeat_frequency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        MedAdatpter medAdatpter = new MedAdatpter(getActivity().getApplicationContext(), medRepeatFrequency, this);
        recyclerView = view.findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);
    }

    @Override
    public void updateUi(String medData) {
        addMedicineFragmentsCommunicator.setMedRepeatingFrequency(medData);
    }
}