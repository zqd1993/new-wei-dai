package com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil;

import com.qingsongvyrnng.mrjgndsdg.BaseQingSongShfjAFduApp;
public class BaseQingSongShfjAFduSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        BaseQingSongShfjAFduApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return BaseQingSongShfjAFduApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        BaseQingSongShfjAFduApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        BaseQingSongShfjAFduApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return BaseQingSongShfjAFduApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return BaseQingSongShfjAFduApp.getPreferences().getBoolean(key, false);
    }

}
