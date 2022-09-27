package com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyuapi;

import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ZCMdhdTshdfBaseModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.ConfigZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.GoodsZCMdhdTshdfModel;
import com.tyfdgeruyfgh.ernhfygwsert.zcmytyhgutyumodel.LoginZCMdhdTshdfModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZCMdhdTshdfApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>> getConfig();

    @GET("/app/user/login")
    Observable<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<ZCMdhdTshdfBaseModel<LoginZCMdhdTshdfModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<ZCMdhdTshdfBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<ZCMdhdTshdfBaseModel<List<GoodsZCMdhdTshdfModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<ZCMdhdTshdfBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<ZCMdhdTshdfBaseModel<ConfigZCMdhdTshdfModel>> getValue(@Query("key") String phone);
}
