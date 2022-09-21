package com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.util;


import com.qwsdbsdvdsnd.dfmcvndfhdfsdfwe.MineApp;

public class SPUtil {

    public static void saveInt(String key, int value) {
        MineApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MineApp.getPreferences().getInt(key, 0);
    }

    public static void saveString(String key, String value) {
        MineApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MineApp.getPreferences().getString(key, "");
    }

    public static void saveBool(String key, boolean value) {
        MineApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBool(String key) {
        return MineApp.getPreferences().getBoolean(key, false);
    }
}
