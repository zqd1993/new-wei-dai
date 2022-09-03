package com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgapi;

import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.BaseNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.ConfigNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.GoodsNewCodeXiaoNiuKuaiModel;
import com.ertyfghxiaoniutrghdfrty.bdtyertyh.xiaoniuvwedfgmodel.NewCodeXiaoNiuKuaiLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewCodeXiaoNiuKuaiApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                                @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseNewCodeXiaoNiuKuaiModel<NewCodeXiaoNiuKuaiLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseNewCodeXiaoNiuKuaiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseNewCodeXiaoNiuKuaiModel<List<GoodsNewCodeXiaoNiuKuaiModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseNewCodeXiaoNiuKuaiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseNewCodeXiaoNiuKuaiModel<ConfigNewCodeXiaoNiuKuaiModel>> getValue(@Query("key") String phone);
}
