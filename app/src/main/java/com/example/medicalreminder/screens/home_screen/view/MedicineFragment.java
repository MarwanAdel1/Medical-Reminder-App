package com.example.medicalreminder.screens.home_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicationActivityScreen;
import com.example.medicalreminder.screens.medication.MedicationRecycleViewAdapter;
import com.example.medicalreminder.screens.medication.OnMedClickListener;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MedicineFragment extends Fragment implements OnMedClickListener {

    MedicationRecycleViewAdapter adapter;
    RecyclerView activeRecycleView, inactiveRecycleView;
    ArrayList<Medicine> activeList, inactiveList;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    Button addmedicatonbtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeRecycleView = view.findViewById(R.id.activerecycleview);
        inactiveRecycleView = view.findViewById(R.id.inactiverecycleview);
        addmedicatonbtn = view.findViewById(R.id.addmedicatonbtn);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        addmedicatonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to add med fragment
                startActivity(new Intent(getActivity().getApplicationContext(), AddMedicationActivityScreen.class));
            }
        });

        activeList = new ArrayList<>();
        // activeList.add(new Medicine( "first ", "6"));


        adapter = new MedicationRecycleViewAdapter(this, activeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false
        activeRecycleView.setHasFixedSize(true);
        activeRecycleView.setLayoutManager(layoutManager);
        activeRecycleView.setAdapter(adapter);

        // Medicine medicine = new Medicine();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        firestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        activeList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Medicine medicine = new Medicine();

                            medicine.setMedName(doc.getString("medName"));
                            medicine.setMedForm(doc.getString("medForm"));
                            medicine.setMedStrength(doc.getDouble("medStrength"));
                            medicine.setMedStrengthUnit(doc.getString("medStrengthUnit"));
                            medicine.setMedReason(doc.getString("medReason"));
                            medicine.setMedRepeatingFrequency(doc.getLong("medRepeatingFrequency").intValue());
                            medicine.setMedRepeatingPerDay(doc.getLong("medRepeatingPerDay").intValue());
                            medicine.setMedRepeatingPerWeek(doc.getLong("medRepeatingPerWeek").intValue());


                            activeList.add(medicine);
                        }

                        adapter.setMyItems(activeList);
                        adapter.notifyDataSetChanged();
                    }
                });


        // active list

        // inactive list
        inactiveList = new ArrayList<>();
        inactiveList.add(new Medicine("second ", "6"));
        inactiveList.add(new Medicine("second ", "6"));


        MedicationRecycleViewAdapter adapter1 = new MedicationRecycleViewAdapter(this, inactiveList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false

        inactiveRecycleView.setHasFixedSize(true);
        inactiveRecycleView.setLayoutManager(layoutManager1);
        inactiveRecycleView.setAdapter(adapter1);

    }

    @Override
    public void onClick(Medicine model, int position) {
        // move to datails fragment
        // you have the model here

        Intent intent = new Intent(getActivity().getApplicationContext(), MedicationDrugScreen.class);
        intent.putExtra("obj", model);
        startActivity(intent);

        Toast.makeText(getContext(), "onClick : " + position + " name " + model.getMedName(), Toast.LENGTH_SHORT).show();
    }
}