package com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtrfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xbk1jk.zldkbk.R;
import com.xbk1jk.zldkbk.zhulihuavrsdtrapi.ZhuLiDaiKuanHuadewgRetrofitManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.BaseZhuLiDaiKuanHuadewgFragment;
import com.xbk1jk.zldkbk.zhulihuavrsdtrbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.ZhuLiDaiKuanHuadewgBaseModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrmodel.GoodsZhuLiDaiKuanHuadewgModel;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.GoodsDetailsZhuLiDaiKuanHuadewgActivity;
import com.xbk1jk.zldkbk.zhulihuavrsdtrui.zhulihuavrsdtradapter.GoodsZhuLiDaiKuanHuadewgListAdapter;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.SharePreferencesZhuLiDaiKuanHuadewgUtil;
import com.xbk1jk.zldkbk.zhulihuavrsdtrutil.StaticZhuLiDaiKuanHuadewgUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListZhuLiDaiKuanHuadewgFragment extends BaseZhuLiDaiKuanHuadewgFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsZhuLiDaiKuanHuadewgListAdapter goodsZhuLiDaiKuanHuadewgListAdapter;
    private Bundle bundle;
    private GoodsZhuLiDaiKuanHuadewgModel mGoodsZhuLiDaiKuanHuadewgModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_zhu_li_dai_kuan_hua_setg_sert;
    }

    @Override
    public void initData() {
        goodsList = rootView.findViewById(R.id.goods_list);
        noDataLl = rootView.findViewById(R.id.no_data_ll);
        rootLl = rootView.findViewById(R.id.root_ll);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> getGoodsList());
        rootLl.setOnClickListener(v -> productClick(mGoodsZhuLiDaiKuanHuadewgModel));
        noDataLl.setOnClickListener(v -> {
            getGoodsList();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = SharePreferencesZhuLiDaiKuanHuadewgUtil.getInt("mobileType");
        phone = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("phone");
        mGoodsZhuLiDaiKuanHuadewgModel = null;
        Observable<ZhuLiDaiKuanHuadewgBaseModel<List<GoodsZhuLiDaiKuanHuadewgModel>>> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel<List<GoodsZhuLiDaiKuanHuadewgModel>>>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel<List<GoodsZhuLiDaiKuanHuadewgModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsZhuLiDaiKuanHuadewgModel = model.getData().get(0);
                                setListData(model.getData());
                            } else {
                                noDataLl.setVisibility(View.VISIBLE);
                                goodsList.setVisibility(View.GONE);
                            }
                        } else {
                            noDataLl.setVisibility(View.VISIBLE);
                            goodsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                        if (goodsZhuLiDaiKuanHuadewgListAdapter == null){
                            noDataLl.setVisibility(View.VISIBLE);
                            goodsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void productClick(GoodsZhuLiDaiKuanHuadewgModel goodsZhuLiDaiKuanHuadewgModel) {
        phone = SharePreferencesZhuLiDaiKuanHuadewgUtil.getString("phone");
        Observable<ZhuLiDaiKuanHuadewgBaseModel> observable = ZhuLiDaiKuanHuadewgRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsZhuLiDaiKuanHuadewgModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<ZhuLiDaiKuanHuadewgBaseModel>() {
                    @Override
                    public void onSuccess(ZhuLiDaiKuanHuadewgBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsZhuLiDaiKuanHuadewgModel.getProductName());
                        bundle.putString("url", goodsZhuLiDaiKuanHuadewgModel.getUrl());
                        StaticZhuLiDaiKuanHuadewgUtil.startActivity(getActivity(), GoodsDetailsZhuLiDaiKuanHuadewgActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsZhuLiDaiKuanHuadewgModel> mData){
        if (goodsZhuLiDaiKuanHuadewgListAdapter == null){
            goodsZhuLiDaiKuanHuadewgListAdapter = new GoodsZhuLiDaiKuanHuadewgListAdapter(R.layout.adapter_goods_list_layout_zhu_li_dai_kuan_hua_setg_sert, mData);
            goodsZhuLiDaiKuanHuadewgListAdapter.setHasStableIds(true);
            goodsZhuLiDaiKuanHuadewgListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsZhuLiDaiKuanHuadewgListAdapter);
        } else {
            goodsZhuLiDaiKuanHuadewgListAdapter.replaceData(mData);
        }
    }
}
