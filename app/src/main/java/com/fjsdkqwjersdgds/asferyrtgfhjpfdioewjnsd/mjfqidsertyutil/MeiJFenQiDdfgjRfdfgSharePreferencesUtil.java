package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.MeiJFenQiDdfgjRfdfgApp;
public class MeiJFenQiDdfgjRfdfgSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        MeiJFenQiDdfgjRfdfgApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MeiJFenQiDdfgjRfdfgApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        MeiJFenQiDdfgjRfdfgApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MeiJFenQiDdfgjRfdfgApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MeiJFenQiDdfgjRfdfgApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MeiJFenQiDdfgjRfdfgApp.getPreferences().getBoolean(key, false);
    }

}
