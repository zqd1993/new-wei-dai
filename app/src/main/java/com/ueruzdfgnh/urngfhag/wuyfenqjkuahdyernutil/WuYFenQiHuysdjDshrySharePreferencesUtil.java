package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil;

import com.ueruzdfgnh.urngfhag.WuYFenQiHuysdjDshryApp;
public class WuYFenQiHuysdjDshrySharePreferencesUtil {

    public static void saveInt(String key, int value) {
        WuYFenQiHuysdjDshryApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return WuYFenQiHuysdjDshryApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        WuYFenQiHuysdjDshryApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        WuYFenQiHuysdjDshryApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return WuYFenQiHuysdjDshryApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return WuYFenQiHuysdjDshryApp.getPreferences().getBoolean(key, false);
    }

}
