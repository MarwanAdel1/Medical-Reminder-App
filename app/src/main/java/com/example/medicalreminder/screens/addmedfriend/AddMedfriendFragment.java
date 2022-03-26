package com.example.medicalreminder.screens.addmedfriend;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AddMedfriendFragment extends Fragment {

    TextView send;
    EditText invite_name_edittext, invite_phone_edittext, invite_email_edittext;
    ImageView imageView;
    Medicine model;
    RequestModel recieverRequestModel, senderRequestModel;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private DocumentReference documentReferenceSender;
    AddMedfriendFragment fragment;


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

        send = view.findViewById(R.id.sendtext);
        invite_name_edittext = view.findViewById(R.id.invite_name_edittext);
        invite_phone_edittext = view.findViewById(R.id.invite_phone_edittext);
        invite_email_edittext = view.findViewById(R.id.invite_email_edittext);
        imageView = view.findViewById(R.id.exit);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save request in firestore
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (!invite_email_edittext.getText().toString().equals("") && !invite_name_edittext.getText().toString().equals("")) {
                        // if in meds
                        if (!(invite_email_edittext.getText().toString().equals(firebaseAuth.getCurrentUser().getEmail()))) {
                            firestore.collection("MedFriends")
                                    .document("MyFriends") // senderfriends   ///nargesnagy21@gmail.com
                                    .collection(firebaseAuth.getCurrentUser().getEmail()) // my current user    firebaseAuth.getCurrentUser().getEmail()
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.d("TAG", "Error : " + error.getMessage());
                                            }
                                            boolean found = false;
                                            for (QueryDocumentSnapshot doc : value) {
                                                if (invite_email_edittext.getText().toString().equals(doc.getString("reciverEmail"))) {
                                                    found = true;

                                                }
                                            }

                                            if (!found) {
                                                send();
                                            }
                                            //  getActivity().finish();
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "you cant sent to your self", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "please enter a name and email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close the fragmnt
                getActivity().finish();
            }
        });


    }

    public void send() {

        senderRequestModel = new RequestModel(firebaseAuth.getCurrentUser().getEmail().toString(), "Narges", invite_email_edittext.getText().toString(), invite_name_edittext.getText().toString(), "pending");

        // add senderRequests data
        firestore.collection("Requests")
                .document("SenderRequests")//
                .collection(firebaseAuth.getCurrentUser().getEmail())
                .document(invite_email_edittext.getText().toString())
                .set(senderRequestModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(AddMedicationActivityScreen.class.getSimpleName(), "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        // add Reciever Requests data
        firestore.collection("Requests")
                .document("RecieverRequests")
                .collection(invite_email_edittext.getText().toString())
                .document(firebaseAuth.getCurrentUser().getEmail())
                .set(senderRequestModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(AddMedicationActivityScreen.class.getSimpleName(), "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}