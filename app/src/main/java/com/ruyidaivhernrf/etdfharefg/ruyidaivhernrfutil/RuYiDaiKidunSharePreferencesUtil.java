package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil;

import com.ruyidaivhernrf.etdfharefg.RuYiDaiKidunApp;
public class RuYiDaiKidunSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        RuYiDaiKidunApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return RuYiDaiKidunApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        RuYiDaiKidunApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        RuYiDaiKidunApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return RuYiDaiKidunApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return RuYiDaiKidunApp.getPreferences().getBoolean(key, false);
    }

}
