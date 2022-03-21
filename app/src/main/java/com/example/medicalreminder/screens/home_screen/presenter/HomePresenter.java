package com.example.medicalreminder.screens.home_screen.presenter;

import com.example.medicalreminder.model.RegisterationRepoInterface;
import com.example.medicalreminder.screens.home_screen.view.HomeActivityInterface;
import com.example.medicalreminder.screens.signup_screen.view.RegisterationActivityInterface;

public class HomePresenter implements HomePresenterInterface{

    private HomeActivityInterface activityInterface;
    private RegisterationRepoInterface repoInterface;

    public HomePresenter(HomeActivityInterface activityInterface, RegisterationRepoInterface repoInterface) {
        this.activityInterface = activityInterface;
        this.repoInterface = repoInterface;
    }

    @Override
    public void getUserName(HomeActivityInterface activityInterface) {
        repoInterface.getUserName(activityInterface);
    }

    @Override
    public void logout() {
        repoInterface.logout();
    }
}
