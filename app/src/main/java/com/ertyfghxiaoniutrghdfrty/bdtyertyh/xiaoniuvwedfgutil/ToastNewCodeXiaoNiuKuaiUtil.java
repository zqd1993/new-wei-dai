package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgutil;

import android.content.Context;
import android.widget.Toast;

import com.ertyfghxiaoniutrghdfrty.bdtyertyh.NewCodeXiaoNiuKuaiApp;

public class ToastNewCodeXiaoNiuKuaiUtil {

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

    private ToastNewCodeXiaoNiuKuaiUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = NewCodeXiaoNiuKuaiApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastNewCodeXiaoNiuKuaiUtil.register(Context context) in your " +
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
