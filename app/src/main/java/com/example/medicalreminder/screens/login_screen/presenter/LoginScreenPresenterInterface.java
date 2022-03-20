package com.example.medicalreminder.screens.login_screen.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface LoginScreenPresenterInterface {
    void normalLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface);

    GoogleSignInClient googleSignInClient(Context context);
    void googleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity);
}
