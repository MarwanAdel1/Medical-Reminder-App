package com.example.medicalreminder.screens.add_medication_screen.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.DoseTime;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicationActivityScreen;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineFragmentsCommunicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddMedHowManyDaysYouWantToRepeatFragment extends Fragment {
    private Medicine medicine;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    private Button next;
    private EditText daysNumber;
    private TextView errorTx;


    public AddMedHowManyDaysYouWantToRepeatFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator, Medicine medicine) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
        this.medicine = medicine;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_how_many_days_you_want_to_repeat, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        daysNumber = view.findViewById(R.id.days_number_et_id);
        next = view.findViewById(R.id.next_Bt);
        errorTx = view.findViewById(R.id.error_tx);
        errorTx.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!daysNumber.getText().toString().isEmpty()) {

                    Calendar startCalendar = Calendar.getInstance();

                    while (!dateToString(startCalendar.getTime()).equalsIgnoreCase(medicine.getStartDate())) {
                        startCalendar.add(Calendar.DATE, 1);
                        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Test 00: " + dateToString(startCalendar.getTime()));
                        Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Test 00: " + medicine.getStartDate());
                    }


                    Map<String, List<DoseTime>> map = new HashMap<>();


                    if (medicine.getMedRepeatingFrequency() == 1) { // daily
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(startCalendar.getTime());

                        for (int i = 0; i < Integer.parseInt(daysNumber.getText().toString()); i++) {
                            Date d = calendar.getTime();
                            map.put(dateToString(d), AddMedicationActivityScreen.doses);

                            calendar.add(Calendar.DATE, 1);
                        }
                    } else if (medicine.getMedRepeatingFrequency() == 2) { // not daily
                        for (int i = 0; i < AddMedicationActivityScreen.daysNumber.size(); i++) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(calendar.getTime());

                            int n = 0;
                            do {
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                if (dayOfWeek == AddMedicationActivityScreen.daysNumber.get(i)) {
                                    Date d = calendar.getTime();
                                    map.put(dateToString(d), AddMedicationActivityScreen.doses);
                                }
                                calendar.add(Calendar.DATE, 1);
                                n++;
                            } while (n < Integer.parseInt(daysNumber.getText().toString()));
                        }
                    }

                    medicine.setMedTimeDosesPerDay(map);

                    Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Test 1: " + AddMedicationActivityScreen.daysNumber.size());
                    Log.e(AddMedicationActivityScreen.class.getSimpleName(), "Hello 1: " + medicine.getMedRepeatingFrequency());
                    for (Map.Entry<String, List<DoseTime>> item : map.entrySet()) {
                        Log.e(AddMedHowManyDaysYouWantToRepeatFragment.class.getSimpleName(), "Hello 2: " + item.getKey());
                    }

                    addMedicineFragmentsCommunicator.saveRepeatedDates(medicine);
                } else {
                    errorTx.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    public String dateToString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

}