package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfapi;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.BaseJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgConfigModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.GoodsJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.LoginJinZhuPigThdfgModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JinZhuPigThdfgApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                        @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseJinZhuPigThdfgModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseJinZhuPigThdfgModel<List<GoodsJinZhuPigThdfgModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseJinZhuPigThdfgModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>> getValue(@Query("key") String phone);
}
