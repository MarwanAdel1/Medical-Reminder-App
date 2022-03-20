package com.example.medicalreminder.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.network_data.FirebaseAccessInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class RegisterationRepo implements RegisterationRepoInterface{

    private Context context;
    private FirebaseAccessInterface firebaseInterface;
    private LocalLoginUserData localData;

    private static RegisterationRepo registerationRepo = null;
    private RegisterationRepo(Context context, FirebaseAccessInterface firebaseInterface){
        this.context = context;
        this.firebaseInterface = firebaseInterface;
    }

    private RegisterationRepo(Context context, FirebaseAccessInterface firebaseInterface, LocalLoginUserData localData){
        this.context = context;
        this.firebaseInterface = firebaseInterface;
        this.localData = localData;
    }

    public static RegisterationRepo getInstance(Context context, FirebaseAccessInterface firebaseInterface) {
        if (registerationRepo == null) {
            registerationRepo = new RegisterationRepo(context, firebaseInterface);
        }
        return registerationRepo;
    }

    public static RegisterationRepo getInstance(Context context, FirebaseAccessInterface firebaseInterface, LocalLoginUserData localData) {
        if (registerationRepo == null) {
            registerationRepo = new RegisterationRepo(context, firebaseInterface,localData);
        }
        return registerationRepo;
    }


    @Override
    public void conFirmRegisteration(String email, String name, String phone, String password, String year, String month, String day,RegisterationActivityInterface activityInterface) {
        firebaseInterface.userRegisteration(email, name, phone, password, year, month, day, activityInterface);
    }

    public void normalLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface){
        localData.saveUserLoginData(email);
        firebaseInterface.normalUserLogin(email,password,loginScreenInterface);
    }

    public GoogleSignInClient googleLogin(Context context){
        //          for login with google
        return firebaseInterface.requestGoogleLogin(context);
    }

    public void finalpartOfGoogleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity){
        firebaseInterface.googleLogin(data, loginInterface, activity, localData);
    }
}
