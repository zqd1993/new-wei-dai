package com.fghjtuytjuj.drtysghjertyh.net;

import android.util.Log;

import com.fjsdkqwj.pfdioewjnsd.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetApi {
    public static final String  REGISTRATION_AGREEMENT = "https://xyssml.yiqian888.xyz/profile/oprjdk/zcxy.html";
    public static final String PRIVACY_AGREEMENT = "https://xyssml.yiqian888.xyz/profile/oprjdk/ysxy.html";
    public static final String API_URL = "http://47.105.47.183:6606/";

    private volatile static NetApi netApi;
    private Retrofit retrofit;

    public static NetApi getNetApi(){
        if (netApi == null){
            synchronized (NetApi.class){
                netApi = new NetApi();
            }
        }
        return netApi;
    }

    public NetApi(){
        initRetrofitManager();
    }

    private void initRetrofitManager(){
        SSLContext sslContext = getSSLContext();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    message = message.replaceAll("%(?![0-9a-fA-F]{2})","%25");
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.d("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d("OKHttp-----", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(doNotVerifier);
        builder.sslSocketFactory(sslContext.getSocketFactory());
        if (BuildConfig.DEBUG){
            builder.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加Rx转换器
                .addConverterFactory(GsonConverterFactory.create())         //添加Gson转换器
                .client(builder.build())     // 09/29，设置客户端的请求
                .build();

    }

    public SSLContext getSSLContext() {
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
            trustManagers = new TrustManager[]{ x509TrustManager };
            ssLContext = SSLContext.getInstance("TLS");
            ssLContext.init(null, trustManagers, new SecureRandom());
            return ssLContext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HostnameVerifier doNotVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public NetInterface getNetInterface(){
        return retrofit.create(NetInterface.class);
    }
}
