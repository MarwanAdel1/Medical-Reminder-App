package com.example.medicalreminder.screens.addmedfriend;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecievedRequestsFragment extends Fragment implements OnAcceptClickListener, OnRefuseClickListener {

    RecieverRequestsRecycleAdapter requestsRecycleAdapter;
    RecyclerView requestsResycleView;
    ArrayList<RequestModel> modellist;
    TextView senderNametext, senderEmailtext;


    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    Button acceptbtn, refusebtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recieved_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        getRequests();

    }

    public void initUI(View view) {

        senderNametext = view.findViewById(R.id.sender_name_text_setting);
        senderEmailtext = view.findViewById(R.id.sender_mail_text_setting);
        requestsResycleView = view.findViewById(R.id.RecieverRequestsRecycleView_fragment);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        refusebtn = view.findViewById(R.id.refusebtn);
        acceptbtn = view.findViewById(R.id.acceptbtn);
        modellist = new ArrayList<>();

        requestsRecycleAdapter = new RecieverRequestsRecycleAdapter(this, this, modellist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false
        requestsResycleView.setHasFixedSize(true);
        requestsResycleView.setLayoutManager(layoutManager);
        requestsResycleView.setAdapter(requestsRecycleAdapter);

    }

    public void getRequests() {

        // path for all requests for the reciver
        DocumentReference documentReference = firestore.collection("Requests")
                .document("RecieverRequests")//
                .collection(firebaseAuth.getCurrentUser().getEmail())
                .document();

        // spicific path to test
        firestore.collection("Requests")
                .document("RecieverRequests")//
                .collection("jijipo@g.com")//firebaseAuth.getCurrentUser().getEmail()
                //  .whereEqualTo("reciverName","mona") // test
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("TAG", "Error : " + error.getMessage());
                        }
                        for (DocumentChange doc : value.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED) {

                                RequestModel model = doc.getDocument().toObject(RequestModel.class);
                                modellist.add(model);
                                requestsRecycleAdapter.notifyDataSetChanged();
                            }
                        }


                    }
                });


    }

    public void deleteRequest(RequestModel model) {

        // delete the request from reciverRequests
        firestore.collection("Requests")
                .document("RecieverRequests")//
                .collection("jiji@g.com") // model.getReciverEmail() ***********
                .document(model.getSenderEmail())//***********   or my user  firebaseAuth.getCurrentUser().getEmail()  ***************
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // delete the request from sender requests
        firestore.collection("Requests")
                .document("SenderRequests")//
                .collection(model.getSenderEmail()) // or my user  firebaseAuth.getCurrentUser().getEmail()
                .document("jiji@g.com")//  *********** model.getReciverEmail()
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveAsMedFreiend(RequestModel model) {

        // save sender medfriends
        firestore.collection("MedFriends")
                .document("MyFriends")
                .collection(model.getSenderEmail())
                .document(model.getReciverEmail()) //or firebaseAuth.getCurrentUser().getEmail() *********// ()
                .set(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "SederMedfriendadded_added", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "fail to add sender medfriend", Toast.LENGTH_SHORT).show();

            }
        });

        // save reciever medfriend
        firestore.collection("MedFriends")
                .document("MyFriends")
                .collection(model.getReciverEmail())  // or firebaseAuth.getCurrentUser().getEmail()  **********/
                .document(model.getSenderEmail())
                .set(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "recieverMedfriendadded_added", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "fail to add reciever medfriend", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void onAcceptClick(RequestModel model) {

        // delete request
        deleteRequest(model);

        // save as medfriend
        saveAsMedFreiend(model);
    }

    @Override
    public void onRefuseClick(RequestModel model) {

        // delete request
        deleteRequest(model);

    }
}