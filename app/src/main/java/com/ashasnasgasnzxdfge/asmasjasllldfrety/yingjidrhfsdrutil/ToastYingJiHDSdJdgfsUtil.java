package com.ashasnasgasnzxdfge.asmasjasllldfrety.yingjidrhfsdrutil;

import android.content.Context;
import android.widget.Toast;

import com.ashasnasgasnzxdfge.asmasjasllldfrety.YingJiHDSdJdgfsApp;

public class ToastYingJiHDSdJdgfsUtil {

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

    private ToastYingJiHDSdJdgfsUtil() {
    }

    private static void check() {
        if (sContext == null) sContext = YingJiHDSdJdgfsApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastCaiJieTongYshVdjrytUtil.register(Context context) in your " +
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
