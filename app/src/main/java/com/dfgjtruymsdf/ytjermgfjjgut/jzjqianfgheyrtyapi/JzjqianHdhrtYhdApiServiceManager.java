package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi;

import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.BaseJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdConfigModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.GoodsJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.LoginJzjqianHdhrtYhdModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JzjqianHdhrtYhdApiServiceManager {
    @GET("/app/config/getConfig")
    Observable<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>> getConfig();

    @GET("/app/user/login")
    Observable<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                          @Query("ip") String ip, @Query("oaid") String oaid);

    @GET("/app/user/login")
    Observable<BaseJzjqianHdhrtYhdModel<LoginJzjqianHdhrtYhdModel>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Observable<BaseJzjqianHdhrtYhdModel> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Observable<BaseJzjqianHdhrtYhdModel<List<GoodsJzjqianHdhrtYhdModel>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Observable<BaseJzjqianHdhrtYhdModel> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Observable<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>> getValue(@Query("key") String phone);
}
