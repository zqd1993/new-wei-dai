package com.youjiegherh.pocketqwrh.youjiewetdfhapi;

import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.BaseYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.ConfigYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.GoodsYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.YouJieSDjdfiLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewCodeXiaoNiuKuaiApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                    @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseYouJieSDjdfiModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseYouJieSDjdfiModel<List<GoodsYouJieSDjdfiModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseYouJieSDjdfiModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>> getValue(@Query("key") String phone);
}
