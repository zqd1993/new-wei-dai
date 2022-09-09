package com.xiaoniukfiwe.ergfdhas;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NewCodeXiaoNiuKuaiApp extends Application {

    private static NewCodeXiaoNiuKuaiApp sInstance;
    private static Context context;

    protected static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static NewCodeXiaoNiuKuaiApp getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
