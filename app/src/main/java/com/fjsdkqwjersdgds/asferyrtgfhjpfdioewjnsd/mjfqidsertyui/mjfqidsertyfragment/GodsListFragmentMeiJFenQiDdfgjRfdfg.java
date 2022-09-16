package com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.R;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyapi.MeiJFenQiDdfgjRfdfgRetrofitManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgBaseFragment;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertybase.MeiJFenQiDdfgjRfdfgObserverManager;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.BaseMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertymodel.GoodsMeiJFenQiDdfgjRfdfgModel;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyactivity.GoodsDetailsMeiJFenQiDdfgjRfdfgActivity;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyui.mjfqidsertyadapter.GoodsMeiJFenQiDdfgjRfdfgListAdapter;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.MeiJFenQiDdfgjRfdfgSharePreferencesUtil;
import com.fjsdkqwjersdgds.asferyrtgfhjpfdioewjnsd.mjfqidsertyutil.StaticMeiJFenQiDdfgjRfdfgUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListFragmentMeiJFenQiDdfgjRfdfg extends MeiJFenQiDdfgjRfdfgBaseFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsMeiJFenQiDdfgjRfdfgListAdapter goodsMeiJFenQiDdfgjRfdfgListAdapter;
    private Bundle bundle;
    private GoodsMeiJFenQiDdfgjRfdfgModel mGoodsMeiJFenQiDdfgjRfdfgModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_mei_jie_sfgh_ewyfhg;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsMeiJFenQiDdfgjRfdfgModel));
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
        mobileType = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getInt("mobileType");
        phone = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("phone");
        mGoodsMeiJFenQiDdfgjRfdfgModel = null;
        Observable<BaseMeiJFenQiDdfgjRfdfgModel<List<GoodsMeiJFenQiDdfgjRfdfgModel>>> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel<List<GoodsMeiJFenQiDdfgjRfdfgModel>>>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel<List<GoodsMeiJFenQiDdfgjRfdfgModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsMeiJFenQiDdfgjRfdfgModel = model.getData().get(0);
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
                        if (goodsMeiJFenQiDdfgjRfdfgListAdapter == null){
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

    private void productClick(GoodsMeiJFenQiDdfgjRfdfgModel goodsMeiJFenQiDdfgjRfdfgModel) {
        phone = MeiJFenQiDdfgjRfdfgSharePreferencesUtil.getString("phone");
        Observable<BaseMeiJFenQiDdfgjRfdfgModel> observable = MeiJFenQiDdfgjRfdfgRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsMeiJFenQiDdfgjRfdfgModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new MeiJFenQiDdfgjRfdfgObserverManager<BaseMeiJFenQiDdfgjRfdfgModel>() {
                    @Override
                    public void onSuccess(BaseMeiJFenQiDdfgjRfdfgModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsMeiJFenQiDdfgjRfdfgModel.getProductName());
                        bundle.putString("url", goodsMeiJFenQiDdfgjRfdfgModel.getUrl());
                        StaticMeiJFenQiDdfgjRfdfgUtil.startActivity(getActivity(), GoodsDetailsMeiJFenQiDdfgjRfdfgActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsMeiJFenQiDdfgjRfdfgModel> mData){
        if (goodsMeiJFenQiDdfgjRfdfgListAdapter == null){
            goodsMeiJFenQiDdfgjRfdfgListAdapter = new GoodsMeiJFenQiDdfgjRfdfgListAdapter(R.layout.adapter_goods_list_layout_mei_jie_sfgh_ewyfhg, mData);
            goodsMeiJFenQiDdfgjRfdfgListAdapter.setHasStableIds(true);
            goodsMeiJFenQiDdfgjRfdfgListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goodsList.setAdapter(goodsMeiJFenQiDdfgjRfdfgListAdapter);
        } else {
            goodsMeiJFenQiDdfgjRfdfgListAdapter.replaceData(mData);
        }
    }
}
