package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.BaseHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.HaojjQianShsjHajduConfigEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.GoodsHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.HaojjQianShsjHajduLoginEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetHaojjQianShsjHajduWork {
    @GET("/app/config/getConfig")
    Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> getConfig();

    @GET("/app/user/login")
    Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduLoginEntity>> login(@Query("phone") String phone, @Query("code") String code, @Query("device") String device,
                                                                            @Query("ip") String ip);

    @GET("/app/user/login")
    Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduLoginEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @GET("/app/user/sendVerifyCode")
    Call<BaseHaojjQianShsjHajduEntity> sendVerifyCode(@Query("phone") String phone);

    @GET("/app/product/productList")
    Call<BaseHaojjQianShsjHajduEntity<List<GoodsHaojjQianShsjHajduEntity>>> getGoodsList(@Query("mobileType") int mobileType, @Query("phone") String phone);

    @GET("/app/product/productClick")
    Call<BaseHaojjQianShsjHajduEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);

    @GET("/app/config/getValue")
    Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> getValue(@Query("key") String phone);
}
