package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil;

import com.yhbnwghjfdkdfet.tgvshdjg.MiaoBaiTiaoAdfFgsApp;
public class SharePreferencesMiaoBaiTiaoAdfFgsUtil {

    public static void saveInt(String key, int value) {
        MiaoBaiTiaoAdfFgsApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MiaoBaiTiaoAdfFgsApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        MiaoBaiTiaoAdfFgsApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MiaoBaiTiaoAdfFgsApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MiaoBaiTiaoAdfFgsApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MiaoBaiTiaoAdfFgsApp.getPreferences().getBoolean(key, false);
    }

}
