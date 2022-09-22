package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil;

import android.content.Context;
import android.widget.Toast;

import com.dryghsfzhaocaimao.regfhseradfert.ZhaoCaiCatKfrtApp;

public class ToastZhaoCaiCatKfrtUtil {

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

    private ToastZhaoCaiCatKfrtUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = ZhaoCaiCatKfrtApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastZhaoCaiCatKfrtUtil.register(Context context) in your " +
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