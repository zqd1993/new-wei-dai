package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfapi;

import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.BaseRuYiDaiKidunModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.ConfigRuYiDaiKidunModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.GoodsRuYiDaiKidunModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.LoginRuYiDaiKidunModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RuYiDaiKidunApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                    @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseRuYiDaiKidunModel<LoginRuYiDaiKidunModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseRuYiDaiKidunModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseRuYiDaiKidunModel<List<GoodsRuYiDaiKidunModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseRuYiDaiKidunModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>> getValue(@Query("key") String phone);
}
