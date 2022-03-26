package com.example.medicalreminder.screens.home_screen.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.DoseTime;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineTimeList;
import com.example.medicalreminder.screens.medication.OnMedClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment implements OnMedClickListener {
    RecyclerView recyclerView;
    MedicineAdapter adapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private String selectedDate;
    private List<MedicineTimeList> medicineTime;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //          UI References
        recyclerView = view.findViewById(R.id.drugs_recycleView);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //-------------------------------------- Start of calendar ------------------------------------------------------------------
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 12);
        //      setup horizontal calendar
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .build();

        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        Date today = new Date();
        selectedDate = dtf.format(today.getTime());
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
                Date today = (Date) date.getTime();
                selectedDate = dtf.format(today);
                Log.i("TAG", "onDateSelected: " + selectedDate);
                setDayMedicines(selectedDate);
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });
        // ------------------------------------------ End of calendar ------------------------------------------------


        //------------------------------------------- Start of medicines list -----------------------------------------
        medicineTime = new ArrayList<>();
        adapter = new MedicineAdapter(this, medicineTime);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setDayMedicines(selectedDate);
    }

    @Override
    public void onClick(Medicine model, int position) {
//        Intent intent = new Intent(getActivity().getApplicationContext(), MedicineNotificationDialog.class);
//        intent.putExtra("obj", model);
//        startActivity(intent);
        Toast.makeText(getContext(), "onClick : " + position + " name " + model.getMedName(), Toast.LENGTH_SHORT).show();
    }

    void setDayMedicines(String todayDate) {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        //      get medicine info
        firestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        medicineTime.clear();
                        for (QueryDocumentSnapshot doc : value)
                            if (doc.getLong("active").intValue() == 1) {
                                if (((HashMap<String, List<DoseTime>>) doc.get("medTimeDosesPerDay")).containsKey(selectedDate)) {
                                    Log.i("TAG", "key: " + ((HashMap<String, List<DoseTime>>) doc.get("medTimeDosesPerDay")).containsKey(selectedDate));
                                    Medicine medicine = new Medicine();
                                    medicine.setMedName(doc.getString("medName"));
                                    medicine.setMedStrength(doc.getDouble("medStrength"));
                                    medicine.setMedStrengthUnit(doc.getString("medStrengthUnit"));
                                    medicine.setMedRepeatingPerDay(doc.getLong("medRepeatingPerDay").intValue());
                                    medicine.setMedNumberOfPillsPerDose(doc.getLong("medNumberOfPillsPerDose").intValue());
                                    HashMap<String, List<DoseTime>> allTime = (HashMap<String, List<DoseTime>>) doc.get("medTimeDosesPerDay");

                                    int i = 0;
                                    while (i < allTime.get(selectedDate).size()) {
                                        Log.i("TAG", "TimeDose: " + allTime.get(selectedDate).get(i));
                                        String[] time = String.valueOf(allTime.get(selectedDate).get(i)).split(",");
                                        String hour = time[0];
                                        if (hour.substring(hour.length() - 2).startsWith("=")) {
                                            hour = "0" + hour.substring(hour.length() - 1);
                                        } else {
                                            hour = hour.substring(hour.length() - 2);
                                        }
                                        String minute = time[1];
                                        if (minute.substring(minute.length() - 3, minute.length() - 1).startsWith("=")) {
                                            minute = "0" + minute.substring(minute.length() - 2, minute.length() - 1);
                                        } else {
                                            minute = minute.substring(minute.length() - 3, minute.length() - 1);
                                        }
                                        medicineTime.add(new MedicineTimeList(medicine, get12Hrs(hour + ":" + minute)));
                                        Log.i("TAG", "onEvent: " + medicineTime.get(i).getMedicine().getMedName() + " -> " + medicineTime.get(i).getTime());
                                        ++i;
                                    }
                                    List<MedicineTimeList> amMedicines = new ArrayList<>();
                                    List<MedicineTimeList> pmMedicines = new ArrayList<>();
                                    for (int x = 0; x < medicineTime.size(); x++) {
                                        if (medicineTime.get(x).getTime().contains("AM")) {
                                            amMedicines.add(medicineTime.get(x));
                                            Log.i("TAG", "am: " + medicineTime.get(x).getMedicine().getMedName() + " -> " + medicineTime.get(x).getTime());
                                        } else {
                                            if (medicineTime.get(x).getTime().contains("PM")) {
                                                pmMedicines.add(medicineTime.get(x));
                                                Log.i("TAG", "pm: " + medicineTime.get(x).getMedicine().getMedName() + " -> " + medicineTime.get(x).getTime());
                                            }
                                        }
                                    }
                                    amMedicines.sort(Comparator.comparing(MedicineTimeList::getTime));
                                    pmMedicines.sort(Comparator.comparing(MedicineTimeList::getTime));
                                    medicineTime.clear();
                                    for (int a = 0; a < amMedicines.size(); a++) {
                                        medicineTime.add(amMedicines.get(a));
                                    }
                                    for (int b = 0; b < pmMedicines.size(); b++) {
                                        medicineTime.add(pmMedicines.get(b));
                                    }
                                    adapter.setMyMedicines(medicineTime);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    medicineTime.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                    }
                });
    }

    public String get12Hrs(String time) {
        String[] splitString = time.split(":");
        String returnedDate = "";
        if (Integer.valueOf(splitString[0]) > 12) {
            returnedDate = (Integer.valueOf(splitString[0]) - 12) + ":" + splitString[1] + " PM";
        } else {
            returnedDate = (Integer.valueOf(splitString[0])) + ":" + splitString[1] + " AM";
        }
        return returnedDate;
    }
}