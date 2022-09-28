package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi;

import android.util.Log;

import com.ueruzdfgnh.urngfhag.BuildConfig;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.WuYFenQiHuysdjDshryIgnoreHttpsValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWuYFenQiHuysdjDshryManager {

    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/oppowyfqjk/zcxy.html";
    public static final String YSXY = "https://xyssml.yiqian888.xyz/profile/oppowyfqjk/ysxy.html";
    public static final String API_BASE_URL = "http://121.41.89.214:7745/";

    private volatile static RetrofitWuYFenQiHuysdjDshryManager retrofitWuYFenQiHuysdjDshryManager;
    private Retrofit retrofit;

    //没有参数的单例模式
    public static RetrofitWuYFenQiHuysdjDshryManager getRetrofitManager(){
        if (retrofitWuYFenQiHuysdjDshryManager == null){
            synchronized (RetrofitWuYFenQiHuysdjDshryManager.class){
                retrofitWuYFenQiHuysdjDshryManager = new RetrofitWuYFenQiHuysdjDshryManager();
            }
        }
        return retrofitWuYFenQiHuysdjDshryManager;
    }

    //没有参数的构造方法
    public RetrofitWuYFenQiHuysdjDshryManager(){
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager(){
        // 09.29    跳过https校验客户端配置
        SSLContext sslContext = WuYFenQiHuysdjDshryIgnoreHttpsValidate.getSSLContext();
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
        builder.hostnameVerifier(WuYFenQiHuysdjDshryIgnoreHttpsValidate.doNotVerifier);
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
    public WuYFenQiHuysdjDshryApiServiceManager getApiService(){
        return retrofit.create(WuYFenQiHuysdjDshryApiServiceManager.class);
    }
}
