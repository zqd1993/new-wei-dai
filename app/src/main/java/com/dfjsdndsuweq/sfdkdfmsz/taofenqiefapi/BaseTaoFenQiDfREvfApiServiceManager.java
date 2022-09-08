package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefapi;

import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfConfigModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.GoodsBaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfLoginModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseTaoFenQiDfREvfApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfLoginModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                            @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfLoginModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseTaoFenQiDfREvfModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseTaoFenQiDfREvfModel<List<GoodsBaseTaoFenQiDfREvfModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseTaoFenQiDfREvfModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>> getValue(@Query("key") String phone);
}
