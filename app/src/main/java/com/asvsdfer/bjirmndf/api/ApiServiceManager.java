package com.asvsdfer.bjirmndf.api;

import com.asvsdfer.bjirmndf.model.BaseModel;
import com.asvsdfer.bjirmndf.model.ConfigEntity;
import com.asvsdfer.bjirmndf.model.GoodsModel;
import com.asvsdfer.bjirmndf.model.LoginModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseModel<ConfigEntity>> getConfig();

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
}
