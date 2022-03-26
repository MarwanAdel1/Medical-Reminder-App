package com.example.medicalreminder.screens.add_medication_screen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.adapters.DaysAdapter;
import com.example.medicalreminder.screens.add_medication_screen.AdapterClickListener;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineFragmentsCommunicator;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class AddMedTakingTimeForWeekFragment extends Fragment implements AdapterClickListener {
    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    private static String[] days = new String[7];
    private static final String[] PREFIX_ORDER = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th"};

    private TextView question_tx;

    public static int count = 0;


    private static List<String> selectedDays = new ArrayList<>();
    private int frequency;

    private int currentFrequency = 0;
    private int dayNumber = 1;

    //private static List<Integer> daysNumber = new ArrayList<>();

    private RecyclerView recyclerView;

    public AddMedTakingTimeForWeekFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator, int frequency) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
        this.frequency = frequency;
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

        String dayNames[] = new DateFormatSymbols().getWeekdays();

        for (int i = 0; i < 7; i++) { /// week days name
            days[i] = dayNames[i + 1];
        }

        question_tx = view.findViewById(R.id.med_day_name);
        question_tx.setText(PREFIX_ORDER[count] + " day, Which day do you want?");

        count++;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        DaysAdapter daysAdapter = new DaysAdapter(getActivity().getApplicationContext(), days, this);
        recyclerView = view.findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(daysAdapter);

        /*

        daysNumber.clear();
        currentFrequency = 0;



*/
        //selectedDays.clear();


    }

    @Override
    public void updateUi(String medData) {
        if (medData.equalsIgnoreCase(days[0])) {//sunday
            dayNumber = 1;
        } else if (medData.equalsIgnoreCase(days[1])) {//monday
            dayNumber = 2;
        } else if (medData.equalsIgnoreCase(days[2])) {//tuesday
            dayNumber = 3;
        } else if (medData.equalsIgnoreCase(days[3])) {//wednesday
            dayNumber = 4;
        } else if (medData.equalsIgnoreCase(days[4])) {//thursday
            dayNumber = 5;
        } else if (medData.equalsIgnoreCase(days[5])) {//friday
            dayNumber = 6;
        } else {//saturday
            dayNumber = 7;
        }

        addMedicineFragmentsCommunicator.setMedNumberOfRepeatedDayPerWeek(dayNumber);


    /*

        boolean repeatSelectedFlag = false;
        for (int i = 0; i < daysNumber.size() && !repeatSelectedFlag; i++) {
            if (daysNumber.get(i) == dayNumber) {
                daysNumber.remove(currentFrequency);
                repeatSelectedFlag = true;
                currentFrequency--;
                break;
            }
        }

        if (!repeatSelectedFlag) {
            daysNumber.add(dayNumber);
            currentFrequency++;
        }


////
//        if (currentFrequency == frequency) {
//            addMedicineFragmentsCommunicator.setMedNumberOfRepeatedDayPerWeek(daysNumber);
//        }
/*
        Log.e(AddMedTakingTimeForWeekFragment.class.getSimpleName(), "Hi : " + dayNumber + ":" + medData);
        Log.e(AddMedTakingTimeForWeekFragment.class.getSimpleName(), "Hi3 : " + medData + ":" + days[dayNumber - 1]);


        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        do {
            if (currentFrequency < frequency) {
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == dayNumber) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    selectedDays.add(simpleDateFormat.format(cal.getTime()));
                    currentFrequency++;
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (cal.get(Calendar.MONTH) == month);


        for (int i = 0; i < selectedDays.size(); i++) {
            System.out.println(selectedDays.get(i));
        }*/
    }
}