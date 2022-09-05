package com.geihuawefvjelkfu.qwersdbn.ncopgeinihuaapi;

import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.BaseNcOpGeiNiHuaModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.NcOpGeiNiHuaConfigModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.NcOpGeiNiHuaGoodsModel;
import com.geihuawefvjelkfu.qwersdbn.ncopgeinihuamodel.LoginNcOpGeiNiHuaModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceNcOpGeiNiHuaManager {
    @GET("/app/config/getConfig")
    Observable<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                    @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseNcOpGeiNiHuaModel<LoginNcOpGeiNiHuaModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseNcOpGeiNiHuaModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseNcOpGeiNiHuaModel<List<NcOpGeiNiHuaGoodsModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseNcOpGeiNiHuaModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseNcOpGeiNiHuaModel<NcOpGeiNiHuaConfigModel>> getValue(@Query("key") String phone);
}
