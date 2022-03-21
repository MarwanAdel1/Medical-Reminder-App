package com.example.medicalreminder.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.screens.home_screen.view.HomeActivityInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;
import com.example.medicalreminder.screens.user_profile.view.UserProfileActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface RegisterationRepoInterface {
    void conFirmRegisteration(String email, String name, String phone, String password, String year, String month, String day, RegisterationActivityInterface activityInterface);
    void normalLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface);
    GoogleSignInClient googleLogin(Context context);
    void finalpartOfGoogleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity);
    String getUserEmail();
    void getUserData(String email, UserProfileActivityInterface activityInterface);
    void saveUserdataAfterEditing(String email, String name, String phone, String dateOfBirth, UserProfileActivityInterface activityInterface);
    void getUserName(HomeActivityInterface activityInterface);
    void logout();
}
