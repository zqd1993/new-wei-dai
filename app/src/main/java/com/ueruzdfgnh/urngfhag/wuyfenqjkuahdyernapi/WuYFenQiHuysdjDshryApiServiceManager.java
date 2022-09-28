package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi;

import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryBaseModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.ConfigWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.GoodsWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.LoginWuYFenQiHuysdjDshryModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WuYFenQiHuysdjDshryApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>> getConfig();

    @GET("/app/user/login")
    Observable<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<WuYFenQiHuysdjDshryBaseModel<LoginWuYFenQiHuysdjDshryModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<WuYFenQiHuysdjDshryBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<WuYFenQiHuysdjDshryBaseModel<List<GoodsWuYFenQiHuysdjDshryModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<WuYFenQiHuysdjDshryBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>> getValue(@Query("key") String phone);
}
