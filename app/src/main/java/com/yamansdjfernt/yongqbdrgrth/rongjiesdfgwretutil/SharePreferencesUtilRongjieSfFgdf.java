package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil;

import com.yamansdjfernt.yongqbdrgrth.RongjieSfFgdfApp;
public class SharePreferencesUtilRongjieSfFgdf {

    public static void saveInt(String key, int value) {
        RongjieSfFgdfApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return RongjieSfFgdfApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        RongjieSfFgdfApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        RongjieSfFgdfApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return RongjieSfFgdfApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return RongjieSfFgdfApp.getPreferences().getBoolean(key, false);
    }

}
