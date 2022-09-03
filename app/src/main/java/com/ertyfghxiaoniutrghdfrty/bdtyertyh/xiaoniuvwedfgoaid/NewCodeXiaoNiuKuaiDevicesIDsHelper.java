package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgoaid;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

public class NewCodeXiaoNiuKuaiDevicesIDsHelper {
    private AppIdsUpdater _listener;

    public NewCodeXiaoNiuKuaiDevicesIDsHelper(AppIdsUpdater callback) {
        _listener = callback;
    }

    private String getBrand() {
        return android.os.Build.BRAND.toUpperCase();
    }

    private String getManufacturer() {
        return Build.MANUFACTURER.toUpperCase();
    }

    public void getOAID(Context mcontext) {

        String oaid = null;
        boolean isSupport = false;

        Log.d("OAID_TOOL", "getManufacturer ===> " + getManufacturer());

        String manufacturer = getManufacturer().toUpperCase();

        if ("ASUS".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);
        } else if ("HUAWEI".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("OPPO".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("VIVO".equals(manufacturer)) {
            oaid = new NewCodeXiaoNiuKuaiVivoDeviceIDHelper(mcontext).getOaid();

        } else if ("XIAOMI".equals(manufacturer)) {

            oaid = new NewCodeXiaoNiuKuaiXiaomiDeviceIDHelper(mcontext).getOAID();

        } else if ("BLACKSHARK".equals(manufacturer)) {
            oaid = new NewCodeXiaoNiuKuaiXiaomiDeviceIDHelper(mcontext).getOAID();

        } else if ("ONEPLUS".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("ZTE".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("FERRMEOS".equals(manufacturer) || isFreeMeOS()) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("SSUI".equals(manufacturer) || isSSUIOS()) {
            getIDFromNewThead(mcontext, manufacturer);
        }

        if (oaid != null) {
            isSupport = true;
        }

        if (_listener != null) {
            _listener.OnIdsAvalid(oaid, isSupport);
        }
    }

    private String getProperty(String property) {
        String res = null;
        if (property == null) {
            return null;
        }
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", new Class[]{String.class, String.class});
            res = (String) method.invoke(clazz, new Object[]{property, "unknown"});
        } catch (Exception e) {
            // ignore
        }
        return res;
    }


    public boolean isFreeMeOS() {
        String pro = getProperty("ro.build.freeme.label");      // "ro.build.freeme.label"
        if ((!TextUtils.isEmpty(pro)) && pro.equalsIgnoreCase("FREEMEOS")) {      // "FreemeOS"  FREEMEOS
            return true;
        }
        return false;
    }

    public boolean isSSUIOS() {
        String pro = getProperty("ro.ssui.product");    // "ro.ssui.product"
        if ((!TextUtils.isEmpty(pro)) && (!pro.equalsIgnoreCase("unknown"))) {
            return true;
        }
        return false;
    }


    /**
     * 启动子线程获取
     *
     * @param context
     */
    private void getIDFromNewThead(final Context context, final String manufacturerm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ("OPPO".equals(manufacturerm)) {
                    new NewCodeXiaoNiuKuaiOppoDeviceIDHelper(context).getID(_listener);
                } else if ("HUAWEI".equals(manufacturerm)) {
                    new NewCodeXiaoNiuKuaiHWDeviceIDHelper(context).getHWID(_listener);

                }
            }
        }).start();
    }


    public interface AppIdsUpdater {
        void OnIdsAvalid(@NonNull String ids, boolean support);
    }
}
