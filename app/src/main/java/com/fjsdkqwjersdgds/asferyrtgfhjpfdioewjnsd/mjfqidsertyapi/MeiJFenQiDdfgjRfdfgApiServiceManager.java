package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.BaseMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgConfigModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.GoodsMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.MeiJFenQiDdfgjRfdfgLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeiJFenQiDdfgjRfdfgApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel<List<GoodsMeiJFenQiDdfgjRfdfgModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseMeiJFenQiDdfgjRfdfgModel<MeiJFenQiDdfgjRfdfgConfigModel>> getValue(@Query("key") String phone);
}
