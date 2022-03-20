package com.example.medicalreminder.screens.login_screen.view;

public interface LoginScreenActivityInterface {
    public void loginUser();
    public void loginOnSuccess();
    public void loginOnFailure(String message);
    public void googleLoginOnSuccess();
    public void googleLoginOnFailure(String message);
}
