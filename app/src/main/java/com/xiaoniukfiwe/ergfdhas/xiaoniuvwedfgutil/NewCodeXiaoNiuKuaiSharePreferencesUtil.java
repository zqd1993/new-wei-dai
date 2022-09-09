package com.xiaoniukfiwe.ergfdhas.xiaoniuvwedfgutil;

import com.xiaoniukfiwe.ergfdhas.NewCodeXiaoNiuKuaiApp;
public class NewCodeXiaoNiuKuaiSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        NewCodeXiaoNiuKuaiApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return NewCodeXiaoNiuKuaiApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        NewCodeXiaoNiuKuaiApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        NewCodeXiaoNiuKuaiApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return NewCodeXiaoNiuKuaiApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return NewCodeXiaoNiuKuaiApp.getPreferences().getBoolean(key, false);
    }

}
