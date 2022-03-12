package com.example.medicalreminder.views.add_medication_screen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.SplashScreen;
import com.example.medicalreminder.views.add_medication_screen.AddMedicineFragmentsCommunicator;

public class AddMedNameFragment extends Fragment {
    private AutoCompleteTextView medAutoCompleteTextView;
    private Button toFormBt;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedNameFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_medication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medAutoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.med_name_act_id);
        ArrayAdapter<String> medArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.auto_complete_list_items, SplashScreen.medicines);
        medAutoCompleteTextView.setThreshold(1);
        medAutoCompleteTextView.setAdapter(medArrayAdapter);


        toFormBt = view.findViewById(R.id.toform_bt);
        toFormBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.setMedName(medAutoCompleteTextView.getText().toString());
            }
        });

    }

}