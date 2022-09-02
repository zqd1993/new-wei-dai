package com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dqlsdjdhwmg.fdhqwenhwmg.R;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwapi.MangGuoHwRetrofitManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.BaseMangGuoHwFragment;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwbase.ObserverMangGuoHwManager;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.BaseMangGuoHwModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwmodel.MangGuoHwGoodsModel;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.GoodsDetailsMangGuoHwActivity;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwui.mangguohwadapter.MangGuoHwGoodsListAdapter;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.MangGuoHwSharePreferencesUtils;
import com.dqlsdjdhwmg.fdhqwenhwmg.mangguohwutil.StaticMangGuoHwUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListMangGuoHwFragment extends BaseMangGuoHwFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private MangGuoHwGoodsListAdapter mangGuoHwGoodsListAdapter;
    private Bundle bundle;
    private MangGuoHwGoodsModel mMangGuoHwGoodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mang_guo_hw_goods_list;
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
        rootLl.setOnClickListener(v -> productClick(mMangGuoHwGoodsModel));
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
        mobileType = MangGuoHwSharePreferencesUtils.getInt("mobileType");
        phone = MangGuoHwSharePreferencesUtils.getString("phone");
        mMangGuoHwGoodsModel = null;
        Observable<BaseMangGuoHwModel<List<MangGuoHwGoodsModel>>> observable = MangGuoHwRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel<List<MangGuoHwGoodsModel>>>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel<List<MangGuoHwGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mMangGuoHwGoodsModel = model.getData().get(0);
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
                        if (mangGuoHwGoodsListAdapter == null){
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

    private void productClick(MangGuoHwGoodsModel mangGuoHwGoodsModel) {
        phone = MangGuoHwSharePreferencesUtils.getString("phone");
        Observable<BaseMangGuoHwModel> observable = MangGuoHwRetrofitManager.getRetrofitManager().
                getApiService().productClick(mangGuoHwGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverMangGuoHwManager<BaseMangGuoHwModel>() {
                    @Override
                    public void onSuccess(BaseMangGuoHwModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", mangGuoHwGoodsModel.getUrl());
                        StaticMangGuoHwUtil.startActivity(getActivity(), GoodsDetailsMangGuoHwActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<MangGuoHwGoodsModel> mData){
        if (mangGuoHwGoodsListAdapter == null){
            mangGuoHwGoodsListAdapter = new MangGuoHwGoodsListAdapter(R.layout.adapter_goods_list_layout_mang_guo_hw, mData);
            mangGuoHwGoodsListAdapter.setHasStableIds(true);
            mangGuoHwGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goodsList.setAdapter(mangGuoHwGoodsListAdapter);
        } else {
            mangGuoHwGoodsListAdapter.replaceData(mData);
        }
    }
}
