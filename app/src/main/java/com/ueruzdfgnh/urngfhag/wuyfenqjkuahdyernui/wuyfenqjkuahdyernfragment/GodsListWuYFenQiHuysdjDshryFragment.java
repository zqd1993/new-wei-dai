package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryFragment;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.WuYFenQiHuysdjDshryObserverManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryBaseModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.GoodsWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.GoodsDetailsWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernadapter.WuYFenQiHuysdjDshryGoodsListAdapter;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.StaticWuYFenQiHuysdjDshryUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListWuYFenQiHuysdjDshryFragment extends BaseWuYFenQiHuysdjDshryFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private WuYFenQiHuysdjDshryGoodsListAdapter wuYFenQiHuysdjDshryGoodsListAdapter;
    private Bundle bundle;
    private GoodsWuYFenQiHuysdjDshryModel mGoodsWuYFenQiHuysdjDshryModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_wu_yfen_qijai_dfjrt;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsWuYFenQiHuysdjDshryModel));
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
        mobileType = WuYFenQiHuysdjDshrySharePreferencesUtil.getInt("mobileType");
        phone = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("phone");
        mGoodsWuYFenQiHuysdjDshryModel = null;
        Observable<WuYFenQiHuysdjDshryBaseModel<List<GoodsWuYFenQiHuysdjDshryModel>>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<List<GoodsWuYFenQiHuysdjDshryModel>>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<List<GoodsWuYFenQiHuysdjDshryModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsWuYFenQiHuysdjDshryModel = model.getData().get(0);
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
                        if (wuYFenQiHuysdjDshryGoodsListAdapter == null){
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

    private void productClick(GoodsWuYFenQiHuysdjDshryModel goodsWuYFenQiHuysdjDshryModel) {
        phone = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("phone");
        Observable<WuYFenQiHuysdjDshryBaseModel> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().
                getApiService().productClick(goodsWuYFenQiHuysdjDshryModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsWuYFenQiHuysdjDshryModel.getProductName());
                        bundle.putString("url", goodsWuYFenQiHuysdjDshryModel.getUrl());
                        StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), GoodsDetailsWuYFenQiHuysdjDshryActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsWuYFenQiHuysdjDshryModel> mData){
        if (wuYFenQiHuysdjDshryGoodsListAdapter == null){
            wuYFenQiHuysdjDshryGoodsListAdapter = new WuYFenQiHuysdjDshryGoodsListAdapter(R.layout.adapter_goods_list_layout_wu_yfen_qijai_dfjrt, mData);
            wuYFenQiHuysdjDshryGoodsListAdapter.setHasStableIds(true);
            wuYFenQiHuysdjDshryGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(wuYFenQiHuysdjDshryGoodsListAdapter);
        } else {
            wuYFenQiHuysdjDshryGoodsListAdapter.replaceData(mData);
        }
    }
}
