package com.example.medicalreminder.views.add_medication_screen.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedOnSaveFragment extends Fragment {
    private Medicine medicine;

    private TextView medDetailsTx;

    private Button onSaveBt;


    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedOnSaveFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator , Medicine medicine) {
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
        return inflater.inflate(R.layout.activity_add_med_on_save, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medDetailsTx = view.findViewById(R.id.med_details_tx_id);
        medDetailsTx.setText(medicine.getMedName()+", "
                +medicine.getMedForm()+", "
                +medicine.getMedStrength()+" "
                +medicine.getMedStrengthUnit());

        onSaveBt = view.findViewById(R.id.save_bt);
        onSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.saveMedicine(medicine);
            }
        });

    }
}