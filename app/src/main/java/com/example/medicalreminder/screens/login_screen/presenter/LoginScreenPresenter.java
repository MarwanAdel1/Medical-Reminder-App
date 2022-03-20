package com.example.medicalreminder.screens.login_screen.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.medicalreminder.model.RegisterationRepoInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivityInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class LoginScreenPresenter implements LoginScreenPresenterInterface{
    private LoginScreenActivityInterface loginActivityInterface;
    private RegisterationRepoInterface repoInterface;

    public LoginScreenPresenter(LoginScreenActivityInterface loginActivityInterface, RegisterationRepoInterface repoInterface) {
        this.loginActivityInterface = loginActivityInterface;
        this.repoInterface = repoInterface;
    }
     public void normalLogin(String email, String password, LoginScreenActivityInterface loginScreenInterface){
        repoInterface.normalLogin(email,password,loginScreenInterface);
     }

    @Override
    public GoogleSignInClient googleSignInClient(Context context) {
        return repoInterface.googleLogin(context);
    }

    public void googleLogin(Intent data , LoginScreenActivityInterface loginInterface, Activity activity){
        repoInterface.finalpartOfGoogleLogin(data, loginInterface, activity);
    }
}
