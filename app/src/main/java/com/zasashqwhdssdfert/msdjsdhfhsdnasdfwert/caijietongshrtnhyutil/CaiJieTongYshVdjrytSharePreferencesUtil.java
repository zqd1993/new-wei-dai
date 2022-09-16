package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.CaiJieTongYshVdjrytApp;
public class CaiJieTongYshVdjrytSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        CaiJieTongYshVdjrytApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return CaiJieTongYshVdjrytApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        CaiJieTongYshVdjrytApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        CaiJieTongYshVdjrytApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return CaiJieTongYshVdjrytApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return CaiJieTongYshVdjrytApp.getPreferences().getBoolean(key, false);
    }

}
