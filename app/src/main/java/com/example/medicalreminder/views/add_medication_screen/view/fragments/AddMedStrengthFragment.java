package com.example.medicalreminder.views.add_medication_screen.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.data.MedStrength;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedStrengthFragment extends Fragment {
    private String[] spinnerValue = {};
    private String spinnerResult;
    private Medicine medicine;

    private Button toReasonBt;
    private EditText strengthValue;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedStrengthFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator, Medicine medicine) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
        this.medicine=medicine;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_med_strength, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toReasonBt = view.findViewById(R.id.toreson_bt);
        strengthValue = view.findViewById(R.id.med_strength_et_id);

        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerResult = spinnerValue[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter arrayAdapter = null;

        if (medicine.getMedForm().equalsIgnoreCase("pill")) {
            spinnerValue = MedStrength.pillStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("solution")) {
            spinnerValue = MedStrength.solutionStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("injection")) {
            spinnerValue = MedStrength.injectionStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("powder")) {
            spinnerValue = MedStrength.powderStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("drops")) {
            spinnerValue = MedStrength.dropsStrength;
        } else if (medicine.getMedForm().equalsIgnoreCase("inhaler")) {
            spinnerValue = MedStrength.inhalerStrength;
        }

        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerValue);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);


        toReasonBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.setMedStrength(Integer.parseInt(strengthValue.getText().toString()), spinnerResult);
            }
        });
    }
}