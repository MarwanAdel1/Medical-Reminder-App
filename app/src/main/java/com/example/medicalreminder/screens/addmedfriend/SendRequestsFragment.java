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
import android.widget.Toast;

import com.example.medicalreminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SendRequestsFragment extends Fragment implements OnClickDeleteSendRequest {

    RecyclerView senderRequestsRecycle;
    SenderFriendsAdapter friendsAdapter;
    ArrayList<RequestModel> modellist;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);

        getRequests();
    }

    public void initUI(View view) {

        senderRequestsRecycle = view.findViewById(R.id.Sender_Requests_Recycle_fragment);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        modellist = new ArrayList<>();

        friendsAdapter = new SenderFriendsAdapter(this, modellist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()); // , LinearLayoutManager.HORIZONTAL , false
        senderRequestsRecycle.setHasFixedSize(true);
        senderRequestsRecycle.setLayoutManager(layoutManager);
        senderRequestsRecycle.setAdapter(friendsAdapter);

    }

    public void getRequests() {

        firestore.collection("Requests")
                .document("SenderRequests")
                .collection(firebaseAuth.getCurrentUser().getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("TAG", "Error : " + error.getMessage());
                        }
                        modellist.clear();
                        for (QueryDocumentSnapshot doc : value) {

                            RequestModel model = new RequestModel();
                            model.setSenderEmail(doc.getString("senderEmail"));
                            model.setSenderName(doc.getString("senderName"));
                            model.setReciverEmail(doc.getString("reciverEmail"));
                            model.setReciverName(doc.getString("reciverName"));
                            model.setStatus(doc.getString("status"));
                            modellist.add(model);
                            friendsAdapter.notifyDataSetChanged();

                        }
                    }
                });

    }

    public void deleteRequest(RequestModel model) {

        // delete the request from reciverRequests
        firestore.collection("Requests")
                .document("RecieverRequests")//
                .collection(model.getReciverEmail()) // model.getreciverEmail ***********"noha@g.com"
                .document(firebaseAuth.getCurrentUser().getEmail())//***********   model.getSenderEmail()    ***************
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        firestore.collection("Requests")
                .document("SenderRequests")//
                .collection(firebaseAuth.getCurrentUser().getEmail())//model.getSenderEmail()
                .document(model.getReciverEmail())//  "noha@g.com"***********
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnClickDeleteSendRequest(RequestModel model) {
        // delete the request
        deleteRequest(model);


    }
}