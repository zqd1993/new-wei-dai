package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrFragment;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrObserverManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.GoodsBaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.GoodsDetailsQuHuaDjdfuVdhrActivityBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvadapter.GoodsListBaseQuHuaDjdfuVdhrAdapter;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.StaticBaseQuHuaDjdfuVdhrUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListQuHuaDjdfuVdhrFragmentBase extends BaseQuHuaDjdfuVdhrFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsListBaseQuHuaDjdfuVdhrAdapter goodsListBaseQuHuaDjdfuVdhrAdapter;
    private Bundle bundle;
    private GoodsBaseQuHuaDjdfuVdhrModel mGoodsBaseQuHuaDjdfuVdhrModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_qu_hua_hua_erf_engh;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsBaseQuHuaDjdfuVdhrModel));
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
        mobileType = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getInt("mobileType");
        phone = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("phone");
        mGoodsBaseQuHuaDjdfuVdhrModel = null;
        Observable<BaseQuHuaDjdfuVdhrModel<List<GoodsBaseQuHuaDjdfuVdhrModel>>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<List<GoodsBaseQuHuaDjdfuVdhrModel>>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<List<GoodsBaseQuHuaDjdfuVdhrModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsBaseQuHuaDjdfuVdhrModel = model.getData().get(0);
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
                        if (goodsListBaseQuHuaDjdfuVdhrAdapter == null){
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

    private void productClick(GoodsBaseQuHuaDjdfuVdhrModel goodsBaseQuHuaDjdfuVdhrModel) {
        phone = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("phone");
        Observable<BaseQuHuaDjdfuVdhrModel> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsBaseQuHuaDjdfuVdhrModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsBaseQuHuaDjdfuVdhrModel.getProductName());
                        bundle.putString("url", goodsBaseQuHuaDjdfuVdhrModel.getUrl());
                        StaticBaseQuHuaDjdfuVdhrUtil.startActivity(getActivity(), GoodsDetailsQuHuaDjdfuVdhrActivityBase.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsBaseQuHuaDjdfuVdhrModel> mData){
        if (goodsListBaseQuHuaDjdfuVdhrAdapter == null){
            goodsListBaseQuHuaDjdfuVdhrAdapter = new GoodsListBaseQuHuaDjdfuVdhrAdapter(R.layout.adapter_goods_list_layout_qu_hua_hua_erf_engh, mData);
            goodsListBaseQuHuaDjdfuVdhrAdapter.setHasStableIds(true);
            goodsListBaseQuHuaDjdfuVdhrAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsListBaseQuHuaDjdfuVdhrAdapter);
        } else {
            goodsListBaseQuHuaDjdfuVdhrAdapter.replaceData(mData);
        }
    }
}
