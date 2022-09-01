package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil;

import com.dqlsdjdhwmg.fdhqwenhwmg.MangGuoHwApp;
public class MangGuoHwSharePreferencesUtils {

    public static void saveInt(String key, int value) {
        MangGuoHwApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MangGuoHwApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        MangGuoHwApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MangGuoHwApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MangGuoHwApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MangGuoHwApp.getPreferences().getBoolean(key, false);
    }

}
