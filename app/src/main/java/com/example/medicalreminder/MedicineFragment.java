package com.example.medicalreminder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.medicalreminder.medication.MedicationModel;
import com.example.medicalreminder.medication.MedicationRecycleViewAdapter;
import com.example.medicalreminder.medication.OnMedClickListener;

import java.util.ArrayList;

public class MedicineFragment extends Fragment implements OnMedClickListener {

    MedicationRecycleViewAdapter adapter;
    RecyclerView activeRecycleView , inactiveRecycleView;
    ArrayList<MedicationModel> activeList , inactiveList  ;
    Button addmedicatonbtn ;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeRecycleView = view.findViewById(R.id.activerecycleview) ;
        inactiveRecycleView = view.findViewById(R.id.inactiverecycleview) ;
        addmedicatonbtn = view.findViewById(R.id.addmedicatonbtn) ;

        addmedicatonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to add med fragment
                Toast.makeText(getContext(), "Add med", Toast.LENGTH_SHORT).show();
            }
        });

        // active list
        activeList = new ArrayList<>();
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;
        activeList.add(new MedicationModel(R.drawable.ic_launcher_background , "first " , "6")) ;

        MedicationRecycleViewAdapter adapter = new MedicationRecycleViewAdapter(this , activeList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false
        activeRecycleView.setHasFixedSize(true);
        activeRecycleView.setLayoutManager(layoutManager);
        activeRecycleView.setAdapter(adapter);

        // inactive list
        inactiveList = new ArrayList<>();
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;
        inactiveList.add(new MedicationModel(R.drawable.ic_launcher_foreground, "second " , "6")) ;

        MedicationRecycleViewAdapter adapter1 = new MedicationRecycleViewAdapter(this , inactiveList);
        RecyclerView.LayoutManager layoutManager1= new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false

        inactiveRecycleView.setHasFixedSize(true);
        inactiveRecycleView.setLayoutManager(layoutManager1);
        inactiveRecycleView.setAdapter(adapter1);




    }



    @Override
    public void onClick(MedicationModel model) {
        // move to datails fragment
        Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
    }
}