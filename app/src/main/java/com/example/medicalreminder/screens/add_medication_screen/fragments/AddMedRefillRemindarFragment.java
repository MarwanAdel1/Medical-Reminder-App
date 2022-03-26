package com.example.medicalreminder.screens.add_medication_screen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineFragmentsCommunicator;


public class AddMedRefillRemindarFragment extends Fragment {
    private Button setMedRemindar;
    private EditText medRemaindarEt, medTotalEt;
    private TextView error1, error2;
    private ImageView backImg;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedRefillRemindarFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_refill_remindar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setMedRemindar = view.findViewById(R.id.set_remindar_bt);
        medRemaindarEt = view.findViewById(R.id.med_remindar_et_id);
        medTotalEt = view.findViewById(R.id.total_med_et_id);
        error1 = view.findViewById(R.id.error_tx);
        error1.setVisibility(View.INVISIBLE);
        error2 = view.findViewById(R.id.error2_tx);
        error2.setVisibility(View.INVISIBLE);

        backImg = view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });

        setMedRemindar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error1.setVisibility(View.INVISIBLE);
                error2.setVisibility(View.INVISIBLE);
                if (medRemaindarEt.getText().toString().trim().isEmpty()) {
                    error1.setVisibility(View.VISIBLE);
                } else if (medTotalEt.getText().toString().trim().isEmpty()) {
                    error2.setVisibility(View.VISIBLE);
                } else if (medRemaindarEt.getText().toString().trim().isEmpty() && medTotalEt.getText().toString().trim().isEmpty()) {
                    error1.setVisibility(View.VISIBLE);
                    error2.setVisibility(View.VISIBLE);
                } else {
                    int least = Integer.parseInt(medRemaindarEt.getText().toString());
                    int total = Integer.parseInt(medTotalEt.getText().toString());
                    addMedicineFragmentsCommunicator.saveMedRefillRemindar(least, total);
                }
            }
        });
    }
}