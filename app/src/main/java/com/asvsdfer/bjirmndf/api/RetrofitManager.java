package com.asvsdfer.bjirmndf.api;

import android.util.Log;

import com.asvsdfer.bjirmndf.base.IgnoreHttpsValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/opyfqdk/zcxy.html";
    public static final String YSXY = "https://gnxys.pycxwl.cn/profile/opyfqdk/ysxy.html";
    public static final String API_BASE_URL = "http://43.249.30.98:6603/";

    private volatile static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    //没有参数的单例模式
    public static RetrofitManager getRetrofitManager(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    //没有参数的构造方法
    public RetrofitManager(){
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager(){
        // 09.29    跳过https校验客户端配置
        SSLContext sslContext = IgnoreHttpsValidate.getSSLContext();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(IgnoreHttpsValidate.doNotVerifier)
                .sslSocketFactory(sslContext.getSocketFactory())    //得到sslSocketFactory实例   设置sllsocketfactory
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加Rx转换器
                .addConverterFactory(GsonConverterFactory.create())         //添加Gson转换器
                .client(client)     // 09/29，设置客户端的请求
                .build();
    }

    //获取网络接口实例
    public ApiServiceManager getApiService(){
        return retrofit.create(ApiServiceManager.class);
    }
}
