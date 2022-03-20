package com.example.medicalreminder.screens.add_medication_screen.view.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineFragmentsCommunicator;

public class AddMedTakingTimeForDayFragment extends Fragment {
    private Medicine medicine;
    private Dialog dialog;

    private Button toSaveBt, cancelBtn, setBtn;
    private TextView tx, numberOfPillsTx;
    private ImageView backImg, editPillsImg;
    private TimePicker timePicker;
    private EditText numberTx;

    private String timePeriod;

    private AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator;

    public AddMedTakingTimeForDayFragment(AddMedicineFragmentsCommunicator addMedicineFragmentsCommunicator, String timePeriod) {
        this.addMedicineFragmentsCommunicator = addMedicineFragmentsCommunicator;
        this.timePeriod = timePeriod;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_med_taking_time_for_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timePicker = view.findViewById(R.id.timePicker1);
        if(timePeriod.equalsIgnoreCase("Morning")){
            timePicker.setHour(5);
            timePicker.setMinute(0);
        }else if(timePeriod.equalsIgnoreCase("Noon")){
            timePicker.setHour(12);
            timePicker.setMinute(0);
        }else if(timePeriod.equalsIgnoreCase("Evening")){
            timePicker.setHour(18);
            timePicker.setMinute(0);
        }

        backImg = view.findViewById(R.id.close_img_id);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.backFragment();
            }
        });

        tx = view.findViewById(R.id.med_repeat_question_id);
        tx.setText("What time of the day?");

        toSaveBt = view.findViewById(R.id.tosave_bt);
        toSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedicineFragmentsCommunicator.setMedNumberOfRepeatingPerday(
                        Integer.parseInt(numberOfPillsTx.getText().toString()),
                        timePicker.getHour(),
                        timePicker.getMinute()
                );
            }
        });

        numberOfPillsTx = view.findViewById(R.id.pills_number_tx_id);
        editPillsImg = view.findViewById(R.id.edit_number_of_pills_id);

        editPillsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(numberOfPillsTx.getText().toString());
                dialog.show();
            }
        });


    }


    private void createDialog(String number) {
        dialog = new Dialog(getContext());
        //set content
        dialog.setContentView(R.layout.pills_number_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        numberTx = (EditText) dialog.findViewById(R.id.number);
        cancelBtn = (Button) dialog.findViewById(R.id.cancel_dialog_button);
        setBtn = (Button) dialog.findViewById(R.id.set_dialog_button);

        numberTx.setText(number);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!numberTx.getText().toString().trim().isEmpty()) {
                    numberOfPillsTx.setText(numberTx.getText().toString());
                    dialog.cancel();
                }
            }
        });
    }

}