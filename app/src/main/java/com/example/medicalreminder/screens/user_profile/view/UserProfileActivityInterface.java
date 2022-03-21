package com.example.medicalreminder.screens.user_profile.view;

import org.json.JSONObject;

public interface UserProfileActivityInterface{
    void setUserData(JSONObject data);
    void onSuccess(JSONObject dataObject);
    void onFailure(String message);
}
