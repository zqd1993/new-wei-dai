package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil;

import android.content.Context;
import android.widget.Toast;

import com.rtgjfjgwuett.rugjjdfgurj.RYDQHdhtTsdhfrApp;

public class ToastRYDQHdhtTsdhfrUtil {

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

    private ToastRYDQHdhtTsdhfrUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = RYDQHdhtTsdhfrApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastRuYiDaiKidunUtil.register(Context context) in your " +
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
