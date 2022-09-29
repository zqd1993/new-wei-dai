package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil;

import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnapp.MainJZhuJsiQIajsdnApp;

public class MyJZhuJsiQIajsdnPreferences {
    public static void saveInt(String key, int value) {
        MainJZhuJsiQIajsdnApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainJZhuJsiQIajsdnApp.getPreferences().getInt(key, 0);
    }

    public static void saveString(String key, String value) {
        MainJZhuJsiQIajsdnApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainJZhuJsiQIajsdnApp.getPreferences().getString(key, "");
    }

    public static void saveBool(String key, boolean value) {
        MainJZhuJsiQIajsdnApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBool(String key) {
        return MainJZhuJsiQIajsdnApp.getPreferences().getBoolean(key, false);
    }
}
