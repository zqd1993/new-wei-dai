package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.WuYouJieShdfnFjfuirApp;
public class WuYouJieShdfnFjfuirSharePreferencesUtil {

    public static void saveInt(String key, int value) {
        WuYouJieShdfnFjfuirApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return WuYouJieShdfnFjfuirApp.getPreferences().getInt(key, 0);
    }
    public static void saveBool(String key, boolean value) {
        WuYouJieShdfnFjfuirApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        WuYouJieShdfnFjfuirApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return WuYouJieShdfnFjfuirApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return WuYouJieShdfnFjfuirApp.getPreferences().getBoolean(key, false);
    }

}
