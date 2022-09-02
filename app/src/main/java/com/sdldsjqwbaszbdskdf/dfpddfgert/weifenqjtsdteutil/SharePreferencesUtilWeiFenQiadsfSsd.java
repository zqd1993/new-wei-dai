package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil;

import com.sdldsjqwbaszbdskdf.dfpddfgert.WeiFenQiadsfSsdApp;
public class SharePreferencesUtilWeiFenQiadsfSsd {

    public static void saveInt(String key, int value) {
        WeiFenQiadsfSsdApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return WeiFenQiadsfSsdApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        WeiFenQiadsfSsdApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        WeiFenQiadsfSsdApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return WeiFenQiadsfSsdApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return WeiFenQiadsfSsdApp.getPreferences().getBoolean(key, false);
    }

}
