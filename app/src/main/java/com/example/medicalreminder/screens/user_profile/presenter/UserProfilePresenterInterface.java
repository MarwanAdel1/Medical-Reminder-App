package com.example.medicalreminder.screens.user_profile.presenter;

import com.example.medicalreminder.screens.user_profile.view.UserProfileActivityInterface;

import org.json.JSONObject;

public interface UserProfilePresenterInterface {
    String getUserEmail();
    void getUserdata(String email, UserProfileActivityInterface activityInterface);
    void saveUserdataAfterEditing(String email, String name, String phone, String dateOfBirth, UserProfileActivityInterface activityInterface);
}
