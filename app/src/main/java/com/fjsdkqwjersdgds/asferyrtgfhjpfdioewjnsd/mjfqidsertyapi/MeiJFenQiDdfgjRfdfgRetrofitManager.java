package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi;

import android.util.Log;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.BuildConfig;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgIgnoreHttpsValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeiJFenQiDdfgjRfdfgRetrofitManager {

    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/mfqjk/zcxy.html";
    public static final String YSXY = "https://gnxys.pycxwl.cn/profile/mfqjk/ysxy.html";
    public static final String API_BASE_URL = "http://106.75.64.111:7751/";

    private volatile static MeiJFenQiDdfgjRfdfgRetrofitManager meiJFenQiDdfgjRfdfgRetrofitManager;
    private Retrofit retrofit;

    //没有参数的单例模式
    public static MeiJFenQiDdfgjRfdfgRetrofitManager getRetrofitManager(){
        if (meiJFenQiDdfgjRfdfgRetrofitManager == null){
            synchronized (MeiJFenQiDdfgjRfdfgRetrofitManager.class){
                meiJFenQiDdfgjRfdfgRetrofitManager = new MeiJFenQiDdfgjRfdfgRetrofitManager();
            }
        }
        return meiJFenQiDdfgjRfdfgRetrofitManager;
    }

    //没有参数的构造方法
    public MeiJFenQiDdfgjRfdfgRetrofitManager(){
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager(){
        // 09.29    跳过https校验客户端配置
        SSLContext sslContext = MeiJFenQiDdfgjRfdfgIgnoreHttpsValidate.getSSLContext();
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
        builder.hostnameVerifier(MeiJFenQiDdfgjRfdfgIgnoreHttpsValidate.doNotVerifier);
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
    public MeiJFenQiDdfgjRfdfgApiServiceManager getApiService(){
        return retrofit.create(MeiJFenQiDdfgjRfdfgApiServiceManager.class);
    }
}
