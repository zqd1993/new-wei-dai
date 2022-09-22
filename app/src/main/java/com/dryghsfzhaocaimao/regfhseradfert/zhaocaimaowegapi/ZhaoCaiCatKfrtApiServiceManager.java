package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegapi;

import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtBaseModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ConfigZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.GoodsZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.LoginZhaoCaiCatKfrtModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZhaoCaiCatKfrtApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>> getConfig();

    @GET("/app/user/login")
    Observable<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                        @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<ZhaoCaiCatKfrtBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<ZhaoCaiCatKfrtBaseModel<List<GoodsZhaoCaiCatKfrtModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<ZhaoCaiCatKfrtBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>> getValue(@Query("key") String phone);
}
