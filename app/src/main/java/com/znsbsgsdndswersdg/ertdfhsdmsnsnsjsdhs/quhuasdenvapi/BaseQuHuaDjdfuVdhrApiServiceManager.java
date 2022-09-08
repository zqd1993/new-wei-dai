package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrConfigModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.GoodsBaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseQuHuaDjdfuVdhrApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                            @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseQuHuaDjdfuVdhrModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseQuHuaDjdfuVdhrModel<List<GoodsBaseQuHuaDjdfuVdhrModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseQuHuaDjdfuVdhrModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> getValue(@Query("key") String phone);
}
