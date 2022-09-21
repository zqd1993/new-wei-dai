package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdBaseModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.ConfigJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.GoodsJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.LoginJiuJiHdfnfhGDhsdModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JiuJiHdfnfhGDhsdApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>> getConfig();

    @GET("/app/user/login")
    Observable<JiuJiHdfnfhGDhsdBaseModel<LoginJiuJiHdfnfhGDhsdModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                            @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<JiuJiHdfnfhGDhsdBaseModel<LoginJiuJiHdfnfhGDhsdModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<JiuJiHdfnfhGDhsdBaseModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<JiuJiHdfnfhGDhsdBaseModel<List<GoodsJiuJiHdfnfhGDhsdModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<JiuJiHdfnfhGDhsdBaseModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>> getValue(@Query("key") String phone);
}
