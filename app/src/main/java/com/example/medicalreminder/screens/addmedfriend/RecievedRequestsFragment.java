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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecievedRequestsFragment extends Fragment implements OnAcceptClickListener, OnRefuseClickListener {

    RecieverRequestsRecycleAdapter requestsRecycleAdapter;
    RecyclerView requestsResycleView;
    ArrayList<RequestModel> modellist;
    TextView senderNametext, senderEmailtext;


    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user ;
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
        user = FirebaseAuth.getInstance().getCurrentUser();
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

        if(user!=null){
// path for all requests for the reciver
            DocumentReference documentReference = firestore.collection("Requests")
                    .document("RecieverRequests")//
                    .collection(firebaseAuth.getCurrentUser().getEmail())
                    .document();

            // spicific path to test
            firestore.collection("Requests")
                    .document("RecieverRequests")//
                    .collection(firebaseAuth.getCurrentUser().getEmail())//"jijipo@g.com"
                    //  .whereEqualTo("reciverName","mona") // test
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
                            }
                            requestsRecycleAdapter.setMyItems(modellist);
                            requestsRecycleAdapter.notifyDataSetChanged();

                        }
                    });
        }else{
            Toast.makeText(getContext(),"invalid" ,Toast.LENGTH_SHORT).show();
        }




    }

    public void deleteRequest(RequestModel model) {

        if(user!=null){
            // delete the request from reciverRequests
            firestore.collection("Requests")
                    .document("RecieverRequests")//
                    .collection(model.getReciverEmail()) // model.getreciverEmail *****"noha@g.com"
                    .document(model.getSenderEmail())//**** firebaseAuth.getCurrentUser().getEmail()      ******
                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            firestore.collection("Requests")
                    .document("SenderRequests")//
                    .collection(model.getSenderEmail())//firebaseAuth.getCurrentUser().getEmail()
                    .document(model.getReciverEmail())//  "noha@g.com"*****
                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            modellist.clear();
        }else{
            Toast.makeText(getContext(),"invalid" ,Toast.LENGTH_SHORT).show();
        }


    }

    public void saveRecieverSenderMed(RequestModel model)
    {
        model.setStatus("1");

        if(user!=null){
            // save reciever medfriend
            firestore.collection("MedFriends")
                    .document("MyFriends")
                    .collection(model.getReciverEmail())  // or firebaseAuth.getCurrentUser().getEmail()  ****/
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

        }else{
            Toast.makeText(getContext(),"invalid" ,Toast.LENGTH_SHORT).show();
        }


    }
    public void saveSenderRecieverMed(RequestModel model) {


        model.setStatus("0");
        if(user!=null){
            firestore.collection("MedFriends")
                    .document("MyFriends")
                    .collection(model.getSenderEmail())  // or firebaseAuth.getCurrentUser().getEmail()  ****/
                    .document(model.getReciverEmail())
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
        }else{
            Toast.makeText(getContext(),"invalid" ,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAcceptClick(RequestModel model) {

        // delete request
        deleteRequest(model);

        saveRecieverSenderMed(model);

        // save as medfriend
        saveSenderRecieverMed(model);

    }

    @Override
    public void onRefuseClick(RequestModel model) {

        // delete request
        deleteRequest(model);

    }
}