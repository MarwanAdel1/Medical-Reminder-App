package com.example.medicalreminder.views.addmedfriend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;


public class AddMedfriendFragment extends Fragment {

    TextView send ;
    EditText nameedittext , phoneedittext , emailedittext ;
    ImageView imageView ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_medfriend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        send = view.findViewById(R.id.sendtext) ;
        nameedittext = view.findViewById(R.id.nameedittext) ;
        phoneedittext = view.findViewById(R.id.phoneedittext) ;
        emailedittext = view.findViewById(R.id.emailedittext) ;
        imageView = view.findViewById(R.id.exit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailedittext.getText().toString().contains("@") && phoneedittext.getText().toString().equals("")  ){
                    Intent intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                    intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, new String[] {"com.google"});

                    startActivity(intent);

                }
                else if(phoneedittext.getText().toString().equals("") || nameedittext.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "faildes is required", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms: " + phoneedittext.getText().toString()) );
                    sendIntent.putExtra("sms_body", nameedittext.getText().toString());
                    startActivity(sendIntent);

                    Toast.makeText(getContext(), "send", Toast.LENGTH_SHORT).show();

                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close the fragmnt
                onDestroy();
            }
        });
    }
}