package com.example.medicalreminder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.example.medicalreminder.row_calendar.WeekViewActivity;
import com.example.medicalreminder.medicine_list_home.MedicineInfoHome;
import com.example.medicalreminder.medicine_list_home.MedicineListHomeAdapter;
import com.example.medicalreminder.row_calendar.WeekViewActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private Calendar calendar = Calendar.getInstance();
    private int currentMonth = 0;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<MedicineInfoHome> medicines = new ArrayList<>();
        medicines.add(new MedicineInfoHome("5:10 AM","Omega-3","200 g, take 2 Pill(s)",""));
        medicines.add(new MedicineInfoHome("7:30 AM, 5:00 PM","Vitamin D","500 g, take 3 Pill(s)","Missed"));
        medicines.add(new MedicineInfoHome("10:00 AM, 7 PM, 3AM","Supravet","70 g, take 1 Pill(s)",""));

        Log.i("TAG", "Medicines: " + medicines.size());

        //          UI References
        recyclerView = view.findViewById(R.id.drugs_recycleView);

        MedicineListHomeAdapter adapter = new MedicineListHomeAdapter(getContext(), medicines);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // set current date to calendar and current month to currentMonth variable
//        WeekViewActivity weekViewActivity = new WeekViewActivity();

    }

}