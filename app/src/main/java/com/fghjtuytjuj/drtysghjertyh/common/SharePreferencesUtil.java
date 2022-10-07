package com.fghjtuytjuj.drtysghjertyh.common;

import com.fghjtuytjuj.drtysghjertyh.BaseApp;

public class SharePreferencesUtil {
    public static void saveInt(String key, int value) {
        BaseApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return BaseApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        BaseApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        BaseApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return BaseApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return BaseApp.getPreferences().getBoolean(key, false);
    }
}
