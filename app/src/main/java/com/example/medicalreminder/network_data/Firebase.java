package com.example.medicalreminder.network_data;

import androidx.annotation.NonNull;

import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Map;

public class Firebase implements FirebaseInterface {
    private static Firebase firebase = null;

    private Firebase() {
    }

    public static Firebase getInstance() {
        if (firebase == null) {
            firebase = new Firebase();
        }
        return firebase;
    }

    @Override
    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface) {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.setFirestoreSettings(settings);

        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        medicationDrugDispalyViewInterface.updateUiOnSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        medicationDrugDispalyViewInterface.updateUiOnFailure();
                    }
                });
    }

    @Override
    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface) {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.setFirestoreSettings(settings);

        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medName)
                .update(medMap);


        editMedicationDrugViewInterface.updateUi();
    }
}
