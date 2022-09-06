package com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfapi;

import android.util.Log;

import com.jinzhudfent.bbsdfwewfg.BuildConfig;
import com.jinzhudfent.bbsdfwewfg.jinzhujietebndgfbase.JinZhuPigThdfgIgnoreHttpsValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JinZhuPigThdfgRetrofitManager {

    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/vojzjq/zcxy.html";
    public static final String YSXY = "https://gnxys.pycxwl.cn/profile/vojzjq/ysxy.html";
    public static final String API_BASE_URL = "http://106.75.14.23:7748/";

    private volatile static JinZhuPigThdfgRetrofitManager jinZhuPigThdfgRetrofitManager;
    private Retrofit retrofit;

    //没有参数的单例模式
    public static JinZhuPigThdfgRetrofitManager getRetrofitManager(){
        if (jinZhuPigThdfgRetrofitManager == null){
            synchronized (JinZhuPigThdfgRetrofitManager.class){
                jinZhuPigThdfgRetrofitManager = new JinZhuPigThdfgRetrofitManager();
            }
        }
        return jinZhuPigThdfgRetrofitManager;
    }

    //没有参数的构造方法
    public JinZhuPigThdfgRetrofitManager(){
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager(){
        // 09.29    跳过https校验客户端配置
        SSLContext sslContext = JinZhuPigThdfgIgnoreHttpsValidate.getSSLContext();
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
        builder.hostnameVerifier(JinZhuPigThdfgIgnoreHttpsValidate.doNotVerifier);
        builder.sslSocketFactory(sslContext.getSocketFactory());
        if (BuildConfig.DEBUG){
            builder.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加Rx转换器
                .addConverterFactory(GsonConverterFactory.create())         //添加Gson转换器
                .client(builder.build())     // 09/29，设置客户端的请求
                .build();

    }

    //获取网络接口实例
    public JinZhuPigThdfgApiServiceManager getApiService(){
        return retrofit.create(JinZhuPigThdfgApiServiceManager.class);
    }
}
