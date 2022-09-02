package com.xbk1jk.zldkbk.zhulihuavrsdtrutil;

import com.xbk1jk.zldkbk.ZhuLiDaiKuanHuadewgApp;
public class SharePreferencesZhuLiDaiKuanHuadewgUtil {

    public static void saveInt(String key, int value) {
        ZhuLiDaiKuanHuadewgApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return ZhuLiDaiKuanHuadewgApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        ZhuLiDaiKuanHuadewgApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        ZhuLiDaiKuanHuadewgApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return ZhuLiDaiKuanHuadewgApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return ZhuLiDaiKuanHuadewgApp.getPreferences().getBoolean(key, false);
    }

}
