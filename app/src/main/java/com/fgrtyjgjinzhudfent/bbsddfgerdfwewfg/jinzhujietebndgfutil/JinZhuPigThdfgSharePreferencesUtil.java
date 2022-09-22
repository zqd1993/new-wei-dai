package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.JinZhuPigThdfgApp;
public class JinZhuPigThdfgSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        JinZhuPigThdfgApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return JinZhuPigThdfgApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        JinZhuPigThdfgApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        JinZhuPigThdfgApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return JinZhuPigThdfgApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return JinZhuPigThdfgApp.getPreferences().getBoolean(key, false);
    }

}
