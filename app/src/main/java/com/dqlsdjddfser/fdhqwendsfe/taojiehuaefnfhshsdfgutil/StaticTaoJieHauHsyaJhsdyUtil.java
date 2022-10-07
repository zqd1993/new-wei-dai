package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class StaticTaoJieHauHsyaJhsdyUtil {

    public static boolean isMobile(String number) {
        if ((number != null) && (!number.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", number);
        }
        return false;
    }

    public static void startActivity(Context context, Class clazz, Bundle bundle) {
            Intent intent = new Intent(context, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
        context.startActivity(intent);
    }

    public static String getAppVersion(Context context) {
        String version = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
            if (TextUtils.isEmpty(version) || version.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> createSpanTexts() {
        List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
        ClickTextViewTaoJieHauHsyaJhsdy.SpanModel textSpanModel = new ClickTextViewTaoJieHauHsyaJhsdy.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewTaoJieHauHsyaJhsdy.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewTaoJieHauHsyaJhsdy.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    public static List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> createDlSpanTexts() {
        List<ClickTextViewTaoJieHauHsyaJhsdy.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
        ClickTextViewTaoJieHauHsyaJhsdy.SpanModel textSpanModel = new ClickTextViewTaoJieHauHsyaJhsdy.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewTaoJieHauHsyaJhsdy.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * @param context * @return
     */
    public static String getMacDefault(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

}
