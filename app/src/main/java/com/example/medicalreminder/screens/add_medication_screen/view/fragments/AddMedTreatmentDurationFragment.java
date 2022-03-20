package com.example.medicalreminder.screens.add_medication_screen.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineFragmentsCommunicator;

import java.util.Calendar;


public class AddMedTreatmentDurationFragment extends Fragment {
    private ImageView backImg;
    private CalendarView datePicker;
    private Button toEndDateBt;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    private int day;
    private int month;
    private int year;

    public AddMedTreatmentDurationFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator) {
        this.addMedicineFragmentsCommunicator=addMedicineFragmentsCommunicator;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month= Calendar.getInstance().get(Calendar.MONTH)+1;
        year = Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_treatment_duration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        datePicker = view.findViewById(R.id.date_picker);
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Log.e(AddMedTreatmentDurationFragment.class.getSimpleName(),"Cal : "+i2+"-"+i1+"-"+i);
                day=i2;
                month=i1+1;
                year=i;
            }
        });

        backImg = view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });


        toEndDateBt = view.findViewById(R.id.toEndDate);
        toEndDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(AddMedTreatmentDurationFragment.class.getSimpleName(),day+"-"+month+"-"+year);
                addMedicineFragmentsCommunicator.setStartDate(
                        day,
                        month,
                        year
                );
            }
        });
    }
}