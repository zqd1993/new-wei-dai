package com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfutil;

import android.content.Context;
import android.widget.Toast;

import com.jinzhudfent.bbsdfwewfg.JinZhuPigThdfgApp;

public class ToastJinZhuPigThdfgUtil {

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

    private ToastJinZhuPigThdfgUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = JinZhuPigThdfgApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastJinZhuPigThdfgUtil.register(Context context) in your " +
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
