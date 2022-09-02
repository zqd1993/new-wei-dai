package com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yhbnwghjfdkdfet.tgvshdjg.R;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdapi.MiaoBaiTiaoAdfFgsRetrofitManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.BaseMiaoBaiTiaoAdfFgsFragment;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdbase.MiaoBaiTiaoAdfFgsObserverManager;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.MiaoBaiTiaoAdfFgsBaseModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdmodel.GoodsMiaoBaiTiaoAdfFgsModel;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.GoodsDetailsMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdui.miaobaitiaosdfsdadapter.GoodsMiaoBaiTiaoAdfFgsListAdapter;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.SharePreferencesMiaoBaiTiaoAdfFgsUtil;
import com.yhbnwghjfdkdfet.tgvshdjg.miaobaitiaosdfsdutil.StaticMiaoBaiTiaoAdfFgsUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsFragment extends BaseMiaoBaiTiaoAdfFgsFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsMiaoBaiTiaoAdfFgsListAdapter goodsMiaoBaiTiaoAdfFgsListAdapter;
    private Bundle bundle;
    private GoodsMiaoBaiTiaoAdfFgsModel mGoodsMiaoBaiTiaoAdfFgsModel;
    private View more_tv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_miao_bai_tiao_sdf_main;
    }

    @Override
    public void initData() {
        goodsList = rootView.findViewById(R.id.goods_list);
        noDataLl = rootView.findViewById(R.id.no_data_ll);
        rootLl = rootView.findViewById(R.id.root_ll);
        more_tv = rootView.findViewById(R.id.more_tv);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> getGoodsList());
        rootLl.setOnClickListener(v -> productClick(mGoodsMiaoBaiTiaoAdfFgsModel));
        noDataLl.setOnClickListener(v -> {
            getGoodsList();
        });
        more_tv.setOnClickListener(v -> {
            ((MainMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity)getActivity()).changePage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getInt("mobileType");
        phone = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("phone");
        mGoodsMiaoBaiTiaoAdfFgsModel = null;
        Observable<MiaoBaiTiaoAdfFgsBaseModel<List<GoodsMiaoBaiTiaoAdfFgsModel>>> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel<List<GoodsMiaoBaiTiaoAdfFgsModel>>>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel<List<GoodsMiaoBaiTiaoAdfFgsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsMiaoBaiTiaoAdfFgsModel = model.getData().get(0);
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
                        if (goodsMiaoBaiTiaoAdfFgsListAdapter == null){
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

    private void productClick(GoodsMiaoBaiTiaoAdfFgsModel goodsMiaoBaiTiaoAdfFgsModel) {
        phone = SharePreferencesMiaoBaiTiaoAdfFgsUtil.getString("phone");
        Observable<MiaoBaiTiaoAdfFgsBaseModel> observable = MiaoBaiTiaoAdfFgsRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsMiaoBaiTiaoAdfFgsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MiaoBaiTiaoAdfFgsObserverManager<MiaoBaiTiaoAdfFgsBaseModel>() {
                    @Override
                    public void onSuccess(MiaoBaiTiaoAdfFgsBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsMiaoBaiTiaoAdfFgsModel.getProductName());
                        bundle.putString("url", goodsMiaoBaiTiaoAdfFgsModel.getUrl());
                        StaticMiaoBaiTiaoAdfFgsUtil.startActivity(getActivity(), GoodsDetailsMiaoBaiTiaoAdfFgsMiaoBaiTiaoAdfFgsActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsMiaoBaiTiaoAdfFgsModel> mData){
        if (goodsMiaoBaiTiaoAdfFgsListAdapter == null){
            goodsMiaoBaiTiaoAdfFgsListAdapter = new GoodsMiaoBaiTiaoAdfFgsListAdapter(R.layout.adapter_goods_list_layout_miao_bai_tiao_sdf, mData);
            goodsMiaoBaiTiaoAdfFgsListAdapter.setHasStableIds(true);
            goodsMiaoBaiTiaoAdfFgsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsMiaoBaiTiaoAdfFgsListAdapter);
        } else {
            goodsMiaoBaiTiaoAdfFgsListAdapter.replaceData(mData);
        }
    }
}
