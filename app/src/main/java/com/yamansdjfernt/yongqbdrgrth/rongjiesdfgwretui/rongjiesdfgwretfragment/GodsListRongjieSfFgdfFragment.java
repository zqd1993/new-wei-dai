package com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.rongjiesdfgwretfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yamansdjfernt.yongqbdrgrth.R;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretapi.RongjieSfFgdfRetrofitManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.BaseRongjieSfFgdfFragment;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretbase.RongjieSfFgdfObserverManager;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfBaseModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretmodel.RongjieSfFgdfGoodsModel;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.RongjieSfFgdfGoodsDetailsRongjieSfFgdfActivity;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretui.rongjiesdfgwretadapter.RongjieSfFgdfGoodsListAdapter;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.SharePreferencesUtilRongjieSfFgdf;
import com.yamansdjfernt.yongqbdrgrth.rongjiesdfgwretutil.StaticRongjieSfFgdfUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListRongjieSfFgdfFragment extends BaseRongjieSfFgdfFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private RongjieSfFgdfGoodsListAdapter rongjieSfFgdfGoodsListAdapter;
    private Bundle bundle;
    private RongjieSfFgdfGoodsModel mRongjieSfFgdfGoodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_rong_jie_sdf_brty;
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
        rootLl.setOnClickListener(v -> productClick(mRongjieSfFgdfGoodsModel));
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
        mobileType = SharePreferencesUtilRongjieSfFgdf.getInt("mobileType");
        phone = SharePreferencesUtilRongjieSfFgdf.getString("phone");
        mRongjieSfFgdfGoodsModel = null;
        Observable<RongjieSfFgdfBaseModel<List<RongjieSfFgdfGoodsModel>>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<List<RongjieSfFgdfGoodsModel>>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<List<RongjieSfFgdfGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mRongjieSfFgdfGoodsModel = model.getData().get(0);
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
                        if (rongjieSfFgdfGoodsListAdapter == null){
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

    private void productClick(RongjieSfFgdfGoodsModel rongjieSfFgdfGoodsModel) {
        phone = SharePreferencesUtilRongjieSfFgdf.getString("phone");
        Observable<RongjieSfFgdfBaseModel> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().
                getApiService().productClick(rongjieSfFgdfGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", rongjieSfFgdfGoodsModel.getProductName());
                        bundle.putString("url", rongjieSfFgdfGoodsModel.getUrl());
                        StaticRongjieSfFgdfUtil.startActivity(getActivity(), RongjieSfFgdfGoodsDetailsRongjieSfFgdfActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<RongjieSfFgdfGoodsModel> mData){
        if (rongjieSfFgdfGoodsListAdapter == null){
            rongjieSfFgdfGoodsListAdapter = new RongjieSfFgdfGoodsListAdapter(R.layout.adapter_goods_list_layout_rong_jie_sdf_brty, mData);
            rongjieSfFgdfGoodsListAdapter.setHasStableIds(true);
            rongjieSfFgdfGoodsListAdapter.setOnItemClickListener((adapter, view, position) -> {
                rongjieSfFgdfGoodsListAdapter.setItemClickListener(item -> productClick(item));
            });
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(rongjieSfFgdfGoodsListAdapter);
        } else {
            rongjieSfFgdfGoodsListAdapter.replaceData(mData);
        }
    }
}
