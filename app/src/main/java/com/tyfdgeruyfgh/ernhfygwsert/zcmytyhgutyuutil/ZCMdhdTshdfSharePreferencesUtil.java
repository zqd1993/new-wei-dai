package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuutil;

import com.tyfdgeruyfgh.ernhfygwsert.ZCMdhdTshdfApp;
public class ZCMdhdTshdfSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        ZCMdhdTshdfApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return ZCMdhdTshdfApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        ZCMdhdTshdfApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        ZCMdhdTshdfApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return ZCMdhdTshdfApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return ZCMdhdTshdfApp.getPreferences().getBoolean(key, false);
    }

}
