package com.xbk1jk.zldkbk.zhulihuavrsdtrapi;

import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgBaseModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ConfigZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.GoodsZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.LoginZhuLiDaiKuanHuadewgModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZhuLiDaiKuanHuadewgApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>> getConfig();

    @GET("/app/user/login")
    Observable<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                  @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<ZhuLiDaiKuanHuadewgBaseModel<LoginZhuLiDaiKuanHuadewgModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<ZhuLiDaiKuanHuadewgBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<ZhuLiDaiKuanHuadewgBaseModel<List<GoodsZhuLiDaiKuanHuadewgModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<ZhuLiDaiKuanHuadewgBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<ZhuLiDaiKuanHuadewgBaseModel<ConfigZhuLiDaiKuanHuadewgModel>> getValue(@Query("key") String phone);
}
