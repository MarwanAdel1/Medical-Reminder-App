package com.example.medicalreminder.local_data;

public interface LocalLoginUserDataInterface {
    void saveUserLoginData(String email);
    String getUserEmail();
    void deleteEmail();
}
