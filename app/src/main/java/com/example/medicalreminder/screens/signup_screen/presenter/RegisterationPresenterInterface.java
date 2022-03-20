package com.example.medicalreminder.screens.signup_screen.presenter;

import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;

public interface RegisterationPresenterInterface {
    void sendUserData(String email, String name, String phone, String password, String year, String month, String day, RegisterationActivityInterface activityInterface);
}
