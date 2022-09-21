package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.YingJiHDSdJdgfsApp;
public class YingJiHDSdJdgfsSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        YingJiHDSdJdgfsApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return YingJiHDSdJdgfsApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        YingJiHDSdJdgfsApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        YingJiHDSdJdgfsApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return YingJiHDSdJdgfsApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return YingJiHDSdJdgfsApp.getPreferences().getBoolean(key, false);
    }

}
