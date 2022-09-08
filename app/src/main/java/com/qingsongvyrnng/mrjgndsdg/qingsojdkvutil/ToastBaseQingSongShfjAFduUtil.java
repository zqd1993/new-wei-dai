package com.qingsongvyrnng.mrjgndsdg.qingsojdkvutil;

import android.content.Context;
import android.widget.Toast;

import com.qingsongvyrnng.mrjgndsdg.BaseQingSongShfjAFduApp;

public class ToastBaseQingSongShfjAFduUtil {

    public static Context sContext;

    private static long lastClickTime =0;

    public static boolean isFastToast() {
        boolean flag =true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime -lastClickTime) >= 500) {
            flag =false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    private ToastBaseQingSongShfjAFduUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = BaseQingSongShfjAFduApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastBaseQingSongShfjAFduUtil.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }

    public static void showShort(String message) {
        check();
        if (isFastToast()){
            return;
        }
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }
}
