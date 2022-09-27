package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrapi;

import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.BaseRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.ConfigRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.GoodsRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.LoginRYDQHdhtTsdhfrModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RYDQHdhtTsdhfrApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseRYDQHdhtTsdhfrModel<LoginRYDQHdhtTsdhfrModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                        @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseRYDQHdhtTsdhfrModel<LoginRYDQHdhtTsdhfrModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseRYDQHdhtTsdhfrModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseRYDQHdhtTsdhfrModel<List<GoodsRYDQHdhtTsdhfrModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseRYDQHdhtTsdhfrModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>> getValue(@Query("key") String phone);
}
