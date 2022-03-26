package com.example.medicalreminder.network_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.model.MedicineRepoInterface;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.screens.add_medication_screen.AddMedicineViewInterface;
import com.example.medicalreminder.screens.home_screen.presenter.MedicationPresenterInterface;
import com.example.medicalreminder.screens.home_screen.view.HomeActivityInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.example.medicalreminder.screens.medication_drug_display_screen.view.MedicationDrugDispalyViewInterface;
import com.example.medicalreminder.screens.medication_drug_edit_screen.view.EditMedicationDrugViewInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;
import com.example.medicalreminder.screens.user_profile.view.UserProfileActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.Map;

public interface FirebaseAccessInterface {
    public void addMedicine(Medicine medicine, AddMedicineViewInterface addMedicineViewInterface);

    public void deleteMedicine(String medName, MedicationDrugDispalyViewInterface medicationDrugDispalyViewInterface);

    public void editMedicine(String medName, Map<String, Object> medMap, EditMedicationDrugViewInterface editMedicationDrugViewInterface);

    void userRegisteration(String email, String name, String phone, String password, String year, String month, String day, RegisterationActivityInterface activityInterface);

    void normalUserLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface);

    //      for login with google
    GoogleSignInClient requestGoogleLogin(Context context);
    void googleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity, LocalLoginUserData localFile);

    //      for display and edit profile data
    void getUserData(String email, UserProfileActivityInterface activityInterface);
    void saveUserdataAfterEditing(String email, String name, String phone, String dateOfBirth, UserProfileActivityInterface activityInterface);

    //      for retrieve user name
    void getUserName(String email, HomeActivityInterface activityInterface);

    //      for logout
    public void logout();

    public void getMedication(String email, MedicineRepoInterface medicineRepoInterface, MedicationPresenterInterface medicationPresenterInterface);
}
