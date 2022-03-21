package com.example.medicalreminder.local_data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;


public class LocalLoginUserData implements LocalLoginUserDataInterface{
    //      Shared Preferences
    SharedPreferences userData;
    private static LocalLoginUserData localLoginUserData= null;
    private LocalLoginUserData(Context context){
        //      Shared Preferences
        userData = context.getSharedPreferences("user_file", MODE_PRIVATE);
    }

    public static LocalLoginUserData getInstance(Context context){
        if(localLoginUserData == null){
            localLoginUserData = new LocalLoginUserData(context);
        }
        return localLoginUserData;
    }

    public void saveUserLoginData(String email){
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("email",email);
        editor.commit();
    }

    public String getUserEmail(){
        return userData.getString("email", "no email");
    }

    public void deleteEmail(){
        userData.edit().clear().apply();
    }
}
