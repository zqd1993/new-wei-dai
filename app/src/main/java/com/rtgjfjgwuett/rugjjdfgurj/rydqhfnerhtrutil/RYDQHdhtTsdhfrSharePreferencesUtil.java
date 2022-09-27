package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil;

import com.rtgjfjgwuett.rugjjdfgurj.RYDQHdhtTsdhfrApp;
public class RYDQHdhtTsdhfrSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        RYDQHdhtTsdhfrApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return RYDQHdhtTsdhfrApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        RYDQHdhtTsdhfrApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        RYDQHdhtTsdhfrApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return RYDQHdhtTsdhfrApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return RYDQHdhtTsdhfrApp.getPreferences().getBoolean(key, false);
    }

}
