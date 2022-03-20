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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.adapters.MedAdatpter;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.view.AdapterClickListener;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedOnSaveFragment extends Fragment implements AdapterClickListener {
    private final String[] finalList = {"Set treatment duration", "Add instructions", "Change medicine icon"};

    private Medicine medicine;

    private TextView medDetailsTx;
    private ImageView backImg;
    private Button onSaveBt;
    private RecyclerView recyclerView;


    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedOnSaveFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator, Medicine medicine) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
        this.medicine = medicine;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        MedAdatpter medAdatpter = new MedAdatpter(getActivity().getApplicationContext(), finalList, this);


        recyclerView = view.findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medAdatpter);


        backImg = view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });

        medDetailsTx = view.findViewById(R.id.med_details_tx_id);
        medDetailsTx.setText(medicine.getMedName() + ", "
                + medicine.getMedForm() + ", "
                + medicine.getMedStrength() + " "
                + medicine.getMedStrengthUnit());

        onSaveBt = view.findViewById(R.id.save_bt);
        onSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.saveMedicine(medicine);
            }
        });


    }

    @Override
    public void updateUi(String medData) {
        if (medData.equalsIgnoreCase(finalList[0])) {

        } else if (medData.equalsIgnoreCase(finalList[1])) {

        } else if (medData.equalsIgnoreCase(finalList[2])) {

        }
    }
}