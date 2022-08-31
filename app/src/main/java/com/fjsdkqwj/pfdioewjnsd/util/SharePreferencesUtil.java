package com.fjsdkqwj.pfdioewjnsd.util;

import com.fjsdkqwj.pfdioewjnsd.App;
public class SharePreferencesUtil {

    public static void saveInt(String key, int value) {
        App.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return App.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        App.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        App.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return App.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return App.getPreferences().getBoolean(key, false);
    }

}
