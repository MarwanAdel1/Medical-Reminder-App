package com.example.medicalreminder.network_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicationActivityScreen;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicineViewInterface;
import com.example.medicalreminder.screens.home_screen.view.HomeActivityInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;
import com.example.medicalreminder.screens.user_profile.view.UserProfileActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FirebaseAccess implements FirebaseAccessInterface {
    private static FirebaseAccess firebaseAccess = null;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private FirebaseAccess() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.setFirestoreSettings(settings);
    }

    public static FirebaseAccess getInstance() {
        if (firebaseAccess == null) {
            firebaseAccess = new FirebaseAccess();
        }
        return firebaseAccess;
    }

    @Override
    public void addMedicine(Medicine medicine, AddMedicineViewInterface addMedicineViewInterface) {
        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medicine.getMedName())
                .set(medicine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addMedicineViewInterface.updateUiAfterAddingSuccess();
                        Log.d(AddMedicationActivityScreen.class.getSimpleName(), "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface) {
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
        firebaseFirestore.collection("Medicines")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("Dependant Name")
                .document(medName)
                .update(medMap);

        editMedicationDrugViewInterface.updateUi();
    }

    public void userRegisteration(String email, String name, String phone, String password, String year, String month, String day, RegisterationActivityInterface activityInterface){
        //              Firebase database object to access firebase's realtime database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medical-reminder-62cc4-default-rtdb.firebaseio.com/");
        //          Firebase elements
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //  to check if the email already existed in firebase realtie database
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(email)) {
                    activityInterface.registerationOnFailure("This email already existed");
                } else {
                    //  sending data to firebase realtime database
                    databaseReference.child("users").child(email).child("name").setValue(name);
                    databaseReference.child("users").child(email).child("phone").setValue(phone);
                    databaseReference.child("users").child(email).child("password").setValue(password);
                    databaseReference.child("users").child(email).child("date of birth").setValue(year + "/" + month + "/" + day);
                    firebaseAuth.createUserWithEmailAndPassword(email.replace('*','.'), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //          if user registration created successfully
                            if(task.isSuccessful()){
                                activityInterface.registerationOnSuccess();
                            } else{
                                activityInterface.registerationOnFailure("Registration Error Occurred ");
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void normalUserLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface){
        //              Firebase element
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
        //              Firebase database object to access firebase's realtime database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medical-reminder-62cc4-default-rtdb.firebaseio.com/");

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  Check if email is existing in the firebase realtime database
                if (snapshot.hasChild(email)) {
                    //  Email is existed in the firebase realtime database
                    //  retrieve password of this user from firebase realtime database and compare it with the existed one in database
                    final String getPassword = snapshot.child(email).child("password").getValue(String.class);
                    Log.i("TAG", "get password: " + getPassword);
                    Log.i("TAG", "password: " + password);
                    if (getPassword.equals(password)) {
                        Log.i("TAG", "onDataChange1: " + email);
                        Log.i("TAG", "onDataChange2: " + email.replace('*','.'));
                        firebaseAuth.signInWithEmailAndPassword(email.replace('*','.'), password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //          if user registration created successfully
                                if (task.isSuccessful()) {
                                    loginScreenInterface.loginOnSuccess();
                                } else {
                                    loginScreenInterface.loginOnFailure("Login Error" + task.getException().getMessage());
                                }
                            }
                        });
                    } else {
                        loginScreenInterface.loginOnFailure("Wrong password");
                    }
                } else {
                    loginScreenInterface.loginOnFailure("Wrong email");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public GoogleSignInClient requestGoogleLogin(Context context){

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1042439510582-mlaoe41nvpo6h6g4e4pcr8qnbktvdu9e.apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        return mGoogleSignInClient;
    }

    @Override
    public void googleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity, LocalLoginUserData localFile){
        //              Firebase database object to access firebase's realtime database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medical-reminder-62cc4-default-rtdb.firebaseio.com/");
        //          For save email in shared preference
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
            firebaseAuthWithGoogle(account.getIdToken(), loginInterface, activity);
            //  to check if the email already existed in firebase realtime database
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.hasChild(account.getEmail().replace('.','*'))) {
                        //  sending data to firebase realtime database
                        databaseReference.child("users").child(account.getEmail().replace('.','*')).child("name").setValue(account.getDisplayName());
                        databaseReference.child("users").child(account.getEmail().replace('.','*')).child("phone").setValue("");
                        databaseReference.child("users").child(account.getEmail().replace('.','*')).child("password").setValue("");
                        databaseReference.child("users").child(account.getEmail().replace('.','*')).child("date of birth").setValue("");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

            // to save email in shared prefernce file
            localFile.saveUserLoginData(account.getEmail().replace('.','*'));

        } catch (ApiException e) {
            loginInterface.googleLoginOnFailure("Google sign in failed: " + e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(String idToken, LoginScreenActivityInterface loginInterface, Activity activity) {
        //          Firebase elements
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginInterface.googleLoginOnSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            loginInterface.loginOnFailure("Authentication Error");
                        }
                    }
                });
    }

    public void getUserData(String email, UserProfileActivityInterface activityInterface) {
        JSONObject returnObject = new JSONObject();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.keepSynced(true);
        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                String date_of_birth = snapshot.child("date of birth").getValue(String.class);
                try {
                    returnObject.put("name", name);
                    returnObject.put("phone", phone);
                    returnObject.put("dateOfBirth", date_of_birth);
                    activityInterface.onSuccess(returnObject);
                } catch (JSONException e) {
                    activityInterface.onFailure("Something wrong with retrieving data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void saveUserdataAfterEditing(String email, String name, String phone, String dateOfBirth, UserProfileActivityInterface activityInterface){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //      To save updates in firebase realtime database
                databaseReference.child(email).child("name").setValue(name);
                databaseReference.child(email).child("phone").setValue(phone);
                databaseReference.child(email).child("date of birth").setValue(dateOfBirth);
                getUserData(email,activityInterface);
                activityInterface.onFailure("data updated successfully");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                activityInterface.onFailure("Editing data fail");
            }
        });
    }

    public void getUserName(String email, HomeActivityInterface activityInterface){
        //              Firebase database object to access firebase's realtime database.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        if(!databaseReference.child(email).equals("no email")) {
            databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    activityInterface.onSuccess(snapshot.child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    activityInterface.onFailure("retrieving user name failed");
                }
            });
        }
        else{
            activityInterface.onSuccess("no name");
        }
    }

    public void logout(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }
}