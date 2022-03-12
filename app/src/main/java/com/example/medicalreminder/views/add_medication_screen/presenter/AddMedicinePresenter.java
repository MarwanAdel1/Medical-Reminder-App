package com.example.medicalreminder.views.add_medication_screen.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.views.add_medication_screen.view.AddMedicationActivityScreen;
import com.example.medicalreminder.views.add_medication_screen.view.AddMedicineViewInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMedicinePresenter implements AddMedicinePresenterInterface {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private AddMedicineViewInterface addMedicineViewInterface;

    public AddMedicinePresenter(AddMedicineViewInterface addMedicineViewInterface) {
        this.addMedicineViewInterface = addMedicineViewInterface;
    }


    @Override
    public void addMedicine(Medicine medicine) {
        saveMedicineToFirestore(medicine);
    }


    public void saveMedicineToFirestore(Medicine savedMedicine) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        savingProccesToFirebase(savedMedicine);
    }

    public void savingProccesToFirebase(Medicine medicine) {
        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medicine.getMedName())
                .set(medicine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(AddMedicationActivityScreen.class.getSimpleName(), "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
