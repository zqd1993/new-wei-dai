package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegutil;

import com.zhaocaimaowerdfgf.nheradfert.ZhaoCaiCatKfrtApp;
public class ZhaoCaiCatKfrtSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        ZhaoCaiCatKfrtApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return ZhaoCaiCatKfrtApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        ZhaoCaiCatKfrtApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        ZhaoCaiCatKfrtApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return ZhaoCaiCatKfrtApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return ZhaoCaiCatKfrtApp.getPreferences().getBoolean(key, false);
    }

}
