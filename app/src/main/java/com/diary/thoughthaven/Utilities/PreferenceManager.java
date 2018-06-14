package com.diary.thoughthaven.Utilities;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "PreferenceTH";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PASSWORD_SET = "IsPasswordSet";
    private static final String PASSWORD = "Password";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

    public void setPasswordFlag(boolean isPassword) {
        editor.putBoolean(IS_PASSWORD_SET, isPassword);
        editor.commit();
    }
    public boolean isPasswordSet() {
        return pref.getBoolean(IS_PASSWORD_SET,false);
    }

    public void setPassword(String password)
    {
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public String getPassword()
    {
        return pref.getString(PASSWORD,"NONE");
    }

}
