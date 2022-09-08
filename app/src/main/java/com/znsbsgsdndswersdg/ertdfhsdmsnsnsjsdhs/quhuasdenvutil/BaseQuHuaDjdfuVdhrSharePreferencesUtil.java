package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.BaseQuHuaDjdfuVdhrApp;
public class BaseQuHuaDjdfuVdhrSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        BaseQuHuaDjdfuVdhrApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return BaseQuHuaDjdfuVdhrApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        BaseQuHuaDjdfuVdhrApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        BaseQuHuaDjdfuVdhrApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return BaseQuHuaDjdfuVdhrApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return BaseQuHuaDjdfuVdhrApp.getPreferences().getBoolean(key, false);
    }

}
