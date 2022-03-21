package com.example.medicalreminder.screens.user_profile.presenter;

import com.example.medicalreminder.model.RegisterationRepoInterface;
import com.example.medicalreminder.screens.user_profile.view.UserProfileActivityInterface;

import org.json.JSONObject;

public class UserProfilePresenter implements UserProfilePresenterInterface{
    private UserProfileActivityInterface activityInterface;
    private RegisterationRepoInterface repoInterface;

    public UserProfilePresenter(UserProfileActivityInterface activityInterface, RegisterationRepoInterface repoInterface) {
        this.activityInterface = activityInterface;
        this.repoInterface = repoInterface;
    }

    @Override
    public String getUserEmail() {
        return repoInterface.getUserEmail();
    }

    @Override
    public void getUserdata(String email, UserProfileActivityInterface activityInterface) {
        repoInterface.getUserData(email, activityInterface);
    }

    @Override
    public void saveUserdataAfterEditing(String email, String name, String phone, String dateOfBirth, UserProfileActivityInterface activityInterface) {
        repoInterface.saveUserdataAfterEditing(email, name, phone, dateOfBirth, activityInterface);
    }
}
