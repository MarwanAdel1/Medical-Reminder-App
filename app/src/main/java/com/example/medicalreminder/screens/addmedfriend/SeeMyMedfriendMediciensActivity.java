package com.example.medicalreminder.screens.addmedfriend;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.room_database.DatabaseAccess;
import com.example.medicalreminder.model.MedicineRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.home_screen.presenter.MedicationPresenter;
import com.example.medicalreminder.screens.home_screen.presenter.MedicationPresenterInterface;
import com.example.medicalreminder.screens.home_screen.view.MedicationFragmentInterface;
import com.example.medicalreminder.screens.medication.MedicationRecycleViewAdapter;
import com.example.medicalreminder.screens.medication.OnMedClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeeMyMedfriendMediciensActivity extends AppCompatActivity implements OnMedClickListener  {
/*
    MedicationRecycleViewAdapter adapter;
    RecyclerView activeRecycleView, inactiveRecycleView;
    ArrayList<Medicine> activeList, inactiveList;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user ;

    private MedicationPresenterInterface medicationPresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_medfriend_mediciens);

        medicationPresenterInterface = new MedicationPresenter(this, MedicineRepo.getInstance(this, FirebaseAccess.getInstance(), DatabaseAccess.getInstance(this)));


        Intent intent = getIntent();
        String senderMail = intent.getStringExtra("SENDERMAIL");
        String recieverMail = intent.getStringExtra("RECIEVERMAIL");


        activeRecycleView = findViewById(R.id.see_myfriend_inactiverecycleview);
        inactiveRecycleView = findViewById(R.id.inactiverecycleview);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        activeList = new ArrayList<>();
        inactiveList = new ArrayList<>();


        MedactiveAdapter = new MedicationRecycleViewAdapter(this, activeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false); // , LinearLayoutManager.HORIZONTAL , false
        activeRecycleView.setHasFixedSize(true);
        activeRecycleView.setLayoutManager(layoutManager);
        activeRecycleView.setAdapter(activeAdapter);


        inActiveAdapter = new MedicationRecycleViewAdapter(this, inactiveList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false); // , LinearLayoutManager.HORIZONTAL , false
        inactiveRecycleView.setHasFixedSize(true);
        inactiveRecycleView.setLayoutManager(linearLayoutManager);
        inactiveRecycleView.setAdapter(inActiveAdapter);




        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            medicationPresenterInterface.getMedicens(user.getEmail());
        }



    }

    @Override
    public void onClick(Medicine model, int position) {
        Toast.makeText(this, "onClick : " + position + " name " + model.getMedName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void retuenMedicines(List<Medicine> active, List<Medicine> inactive) {
        activeAdapter.setMyItems(active);
        activeAdapter.notifyDataSetChanged();

        inActiveAdapter.setMyItems(inactive);
        inActiveAdapter.notifyDataSetChanged();
    }
    */

    MedicationRecycleViewAdapter adapter;
    RecyclerView activeRecycleView, inactiveRecycleView;
    ArrayList<Medicine> activeList, inactiveList;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_medfriend_mediciens);


        Intent intent = getIntent();
        String senderMail = intent.getStringExtra("SENDERMAIL");
        String recieverMail = intent.getStringExtra("RECIEVERMAIL");


        activeRecycleView = findViewById(R.id.see_myfriend_inactiverecycleview);
        inactiveRecycleView = findViewById(R.id.inactiverecycleview);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        activeList = new ArrayList<>();
        inactiveList = new ArrayList<>();
        // activeList.add(new Medicine( "first ", "6"));


        adapter = new MedicationRecycleViewAdapter(this, activeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // , LinearLayoutManager.HORIZONTAL , false
        activeRecycleView.setHasFixedSize(true);
        activeRecycleView.setLayoutManager(layoutManager);
        activeRecycleView.setAdapter(adapter);



        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        if(user!=null){

            firestore.collection("Medicines")
                    .document(senderMail)
                    .collection("Dependant Name")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            // activeList.clear();
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

        }else{
            Toast.makeText(this,"invalid" ,Toast.LENGTH_SHORT).show();
        }

        // active list

        // inactive list
        inactiveList = new ArrayList<>();


        MedicationRecycleViewAdapter adapter1 = new MedicationRecycleViewAdapter(this, inactiveList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this); // , LinearLayoutManager.HORIZONTAL , false

//       inactiveRecycleView.setHasFixedSize(true);
//        inactiveRecycleView.setLayoutManager(layoutManager1);
//       inactiveRecycleView.setAdapter(adapter1);

    }

    @Override
    public void onClick(Medicine model, int position) {
        Toast.makeText(this, "onClick : " + position + " name " + model.getMedName(), Toast.LENGTH_SHORT).show();

    }
}