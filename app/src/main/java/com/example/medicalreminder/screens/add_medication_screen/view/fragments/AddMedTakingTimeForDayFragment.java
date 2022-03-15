package com.example.medicalreminder.screens.add_medication_screen.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedTakingTimeForDayFragment extends Fragment {
    private Medicine medicine;

    private Button toSaveBt;
    private TextView tx;
    private ImageView backImg;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedTakingTimeForDayFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator){
        this.addMedicineFragmentsCommunicator=addMedicineFragmentsCommunicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_med_taking_time_for_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backImg=view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });

        tx=view.findViewById(R.id.med_repeat_question_id);
        tx.setText("What time of day for Period Number "/*+counter*/);


        toSaveBt = view.findViewById(R.id.tosave_bt);
        toSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.setMedNumberOfRepeatingPerday();
            }
        });

    }
}