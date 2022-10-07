package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil;

import com.dqlsdjddfser.fdhqwendsfe.TaoJieHauHsyaJhsdyApp;
public class SharePreferencesUtilTaoJieHauHsyaJhsdy {

    public static void saveInt(String key, int value) {
        TaoJieHauHsyaJhsdyApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return TaoJieHauHsyaJhsdyApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        TaoJieHauHsyaJhsdyApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        TaoJieHauHsyaJhsdyApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return TaoJieHauHsyaJhsdyApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return TaoJieHauHsyaJhsdyApp.getPreferences().getBoolean(key, false);
    }

}
