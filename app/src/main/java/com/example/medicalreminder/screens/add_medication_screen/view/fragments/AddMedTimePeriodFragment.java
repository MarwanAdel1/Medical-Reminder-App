package com.example.medicalreminder.screens.add_medication_screen.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.adapters.MedAdatpter;
import com.example.medicalreminder.screens.add_medication_screen.view.AdapterClickListener;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedTimePeriodFragment extends Fragment implements AdapterClickListener {
    public static final String[] medTimePeriod = {"Morning", "Noon","Evening"};


    private RecyclerView recyclerView;
    private ImageView backImg;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedTimePeriodFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator){
        this.addMedicineFragmentsCommunicator=addMedicineFragmentsCommunicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_med_period, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        backImg=view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        MedAdatpter medAdatpter = new MedAdatpter(getActivity().getApplicationContext(), medTimePeriod, this);
        recyclerView = view.findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);
    }

    @Override
    public void updateUi(String medData) {
        addMedicineFragmentsCommunicator.sendTimePeriod(medData);
    }
}