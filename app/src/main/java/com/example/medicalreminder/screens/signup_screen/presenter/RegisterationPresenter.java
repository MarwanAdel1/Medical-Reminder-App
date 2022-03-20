package com.example.medicalreminder.screens.signup_screen.presenter;

import com.example.medicalreminder.model.RegisterationRepoInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;

public class RegisterationPresenter implements RegisterationPresenterInterface {
    private RegisterationActivityInterface activityInterface;
    private RegisterationRepoInterface repoInterface;

    public RegisterationPresenter(RegisterationActivityInterface activityInterface, RegisterationRepoInterface repoInterface) {
        this.activityInterface = activityInterface;
        this.repoInterface = repoInterface;
    }

    @Override
    public void sendUserData(String email, String name, String phone, String password, String year, String month, String day, RegisterationActivityInterface activityInterface) {
        repoInterface.conFirmRegisteration(email, name, phone,password, year, month, day, activityInterface);
    }
}
