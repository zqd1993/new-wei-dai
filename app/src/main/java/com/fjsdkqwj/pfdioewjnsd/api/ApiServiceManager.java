package com.fjsdkqwj.pfdioewjnsd.api;

import com.fjsdkqwj.pfdioewjnsd.model.BaseModel;
import com.fjsdkqwj.pfdioewjnsd.model.ConfigModel;
import com.fjsdkqwj.pfdioewjnsd.model.GoodsModel;
import com.fjsdkqwj.pfdioewjnsd.model.LoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseModel<ConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseModel<LoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/login")
    Observable<BaseModel<LoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseModel<List<GoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseModel<ConfigModel>> getValue(@Query("key") String phone);
}
