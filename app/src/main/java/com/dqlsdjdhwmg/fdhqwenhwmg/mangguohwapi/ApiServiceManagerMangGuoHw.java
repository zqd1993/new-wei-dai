package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi;

import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwConfigModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwGoodsModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.LoginMangGuoHwModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceManagerMangGuoHw {
    @GET("/app/config/getConfig")
    Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseMangGuoHwModel<LoginMangGuoHwModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device, @Query("ip") String ip);

    @GET("/app/user/login")
    Observable<BaseMangGuoHwModel<LoginMangGuoHwModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseMangGuoHwModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseMangGuoHwModel<List<MangGuoHwGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseMangGuoHwModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseMangGuoHwModel<MangGuoHwConfigModel>> getValue(@Query("key") String phone);
}
