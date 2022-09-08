package com.dfjsdndsuweq.sfdkdfmsz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BaseTaoFenQiDfREvfApp extends Application {

    private static BaseTaoFenQiDfREvfApp sInstance;
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

    public static BaseTaoFenQiDfREvfApp getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
