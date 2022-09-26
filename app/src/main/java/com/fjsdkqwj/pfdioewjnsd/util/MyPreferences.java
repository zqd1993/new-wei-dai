package com.fjsdkqwj.pfdioewjnsd.util;

import com.fjsdkqwj.pfdioewjnsd.app.MainApp;

public class MyPreferences {
    public static void saveInt(String key, int value) {
        MainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainApp.getPreferences().getInt(key, 0);
    }

    public static void saveString(String key, String value) {
        MainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainApp.getPreferences().getString(key, "");
    }

    public static void saveBool(String key, boolean value) {
        MainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBool(String key) {
        return MainApp.getPreferences().getBoolean(key, false);
    }
}
