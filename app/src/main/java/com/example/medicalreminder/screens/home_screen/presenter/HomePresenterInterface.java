package com.example.medicalreminder.screens.home_screen.presenter;

import com.example.medicalreminder.screens.home_screen.view.HomeActivityInterface;

public interface HomePresenterInterface {
    void getUserName(HomeActivityInterface activityInterface);
    void logout();
}
