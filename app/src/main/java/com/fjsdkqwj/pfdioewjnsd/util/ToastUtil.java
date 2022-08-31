package com.fjsdkqwj.pfdioewjnsd.util;

import android.content.Context;
import android.widget.Toast;

import com.fjsdkqwj.pfdioewjnsd.App;

public class ToastUtil {

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

    private ToastUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = App.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtil.register(Context context) in your " +
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
