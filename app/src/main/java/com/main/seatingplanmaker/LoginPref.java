package com.main.seatingplanmaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class LoginPref {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "Login_Preference";
    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_EMAIL = "email";

    public LoginPref(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(PREF_NAME, name);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(PREF_NAME, pref.getString(PREF_NAME, null));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
