package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class RongjieSfFgdfIgnoreHttpsValidate {
    /**
     * 获取Https的证书
     * 09/29
     * context Activity（fragment）的上下文
     * @return SSL的上下文对象
     */
    public static SSLContext getSSLContext() {
        SSLContext ssLContext;
        TrustManager[] trustManagers;
        X509TrustManager x509TrustManager;
        try {
            x509TrustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            };
            trustManagers = new TrustManager[]{ x509TrustManager };         //生成trustmanager数组
            ssLContext = SSLContext.getInstance("TLS");                     //得到sslcontext实例。SSL TSL 是一种https使用的安全传输协议
            ssLContext.init(null, trustManagers, new SecureRandom());  //初始化sslcontext 信任所有证书 （官方不推荐使用）
            return ssLContext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    //跳过Hostname，直接返回true
    public static HostnameVerifier doNotVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
