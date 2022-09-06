package com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegapi;

import android.util.Log;

import com.zhaocaimaowerdfgf.nheradfert.BuildConfig;
import com.zhaocaimaowerdfgf.nheradfert.zhaocaimaowegbase.ZhaoCaiCatKfrtIgnoreHttpsValidate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitZhaoCaiCatKfrtManager {

    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/vozcmdk/zcxy.html";
    public static final String YSXY = "https://gnxys.pycxwl.cn/profile/vozcmdk/ysxy.html";
    public static final String API_BASE_URL = "http://121.41.122.164:6610/";

    private volatile static RetrofitZhaoCaiCatKfrtManager retrofitZhaoCaiCatKfrtManager;
    private Retrofit retrofit;

    //没有参数的单例模式
    public static RetrofitZhaoCaiCatKfrtManager getRetrofitManager(){
        if (retrofitZhaoCaiCatKfrtManager == null){
            synchronized (RetrofitZhaoCaiCatKfrtManager.class){
                retrofitZhaoCaiCatKfrtManager = new RetrofitZhaoCaiCatKfrtManager();
            }
        }
        return retrofitZhaoCaiCatKfrtManager;
    }

    //没有参数的构造方法
    public RetrofitZhaoCaiCatKfrtManager(){
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager(){
        // 09.29    跳过https校验客户端配置
        SSLContext sslContext = ZhaoCaiCatKfrtIgnoreHttpsValidate.getSSLContext();
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
        builder.hostnameVerifier(ZhaoCaiCatKfrtIgnoreHttpsValidate.doNotVerifier);
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
    public ZhaoCaiCatKfrtApiServiceManager getApiService(){
        return retrofit.create(ZhaoCaiCatKfrtApiServiceManager.class);
    }
}
