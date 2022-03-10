package com.example.medicalreminder.views.add_dependent_screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;


public class AddDependentFragment extends Fragment {


    EditText firstNameEditText , lastNameEditText ;
    TextView doneimagebtn ;
    RadioButton malrRadiobtn , FemaleRadiobtn ;
    Fragment fragment1 = null ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dependent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doneimagebtn = view.findViewById(R.id.donetext) ;
        firstNameEditText = view.findViewById(R.id.firstnameedittext);
        lastNameEditText = view.findViewById(R.id.secondnameedittext2) ;


        doneimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment1 = new DeleteDependentFragment();

                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                // move to the next fragment with the first and last name

            }
        });


    }
}