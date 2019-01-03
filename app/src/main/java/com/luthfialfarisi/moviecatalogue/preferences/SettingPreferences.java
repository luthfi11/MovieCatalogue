package com.luthfialfarisi.moviecatalogue.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingPreferences {
    private String KEY_RELEASE = "upcoming";
    private String KEY_DAILY = "daily";
    private String PREF_NAME = "UserPref";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SettingPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyReminder(boolean check){
        editor = preferences.edit();
        editor.putBoolean(KEY_DAILY, check);
        editor.apply();
    }

    public void setReleaseTodayReminder(boolean check){
        editor = preferences.edit();
        editor.putBoolean(KEY_RELEASE, check);
        editor.apply();
    }

    public boolean dailyChecked(){
        return preferences.getBoolean(KEY_DAILY, false);
    }

    public boolean releaseChecked(){
        return preferences.getBoolean(KEY_RELEASE,false);
    }
}
