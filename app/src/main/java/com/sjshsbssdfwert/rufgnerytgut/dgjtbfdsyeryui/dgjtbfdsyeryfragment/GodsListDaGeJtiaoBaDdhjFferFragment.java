package com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sjshsbssdfwert.rufgnerytgut.R;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryapi.DaGeJtiaoBaDdhjFferRetrofitManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.BaseDaGeJtiaoBaDdhjFferFragment;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerybase.DaGeJtiaoBaDdhjFferObserverManager;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.BaseDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyerymodel.GoodsDaGeJtiaoBaDdhjFferModel;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.GoodsDetailsDaGeJtiaoBaDdhjFferActivity;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryui.dgjtbfdsyeryadapter.DaGeJtiaoBaDdhjFferGoodsListAdapter;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.DaGeJtiaoBaDdhjFferSharePreferencesUtil;
import com.sjshsbssdfwert.rufgnerytgut.dgjtbfdsyeryutil.StaticDaGeJtiaoBaDdhjFferUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListDaGeJtiaoBaDdhjFferFragment extends BaseDaGeJtiaoBaDdhjFferFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private DaGeJtiaoBaDdhjFferGoodsListAdapter daGeJtiaoBaDdhjFferGoodsListAdapter;
    private Bundle bundle;
    private GoodsDaGeJtiaoBaDdhjFferModel mGoodsDaGeJtiaoBaDdhjFferModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_list_da_ge_jdf_yrjf;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsDaGeJtiaoBaDdhjFferModel));
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
        mobileType = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getInt("mobileType");
        phone = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("phone");
        mGoodsDaGeJtiaoBaDdhjFferModel = null;
        Observable<BaseDaGeJtiaoBaDdhjFferModel<List<GoodsDaGeJtiaoBaDdhjFferModel>>> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel<List<GoodsDaGeJtiaoBaDdhjFferModel>>>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel<List<GoodsDaGeJtiaoBaDdhjFferModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsDaGeJtiaoBaDdhjFferModel = model.getData().get(0);
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
                        if (daGeJtiaoBaDdhjFferGoodsListAdapter == null){
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

    private void productClick(GoodsDaGeJtiaoBaDdhjFferModel goodsDaGeJtiaoBaDdhjFferModel) {
        phone = DaGeJtiaoBaDdhjFferSharePreferencesUtil.getString("phone");
        Observable<BaseDaGeJtiaoBaDdhjFferModel> observable = DaGeJtiaoBaDdhjFferRetrofitManager.getRetrofitManager().
                getApiService().productClick(goodsDaGeJtiaoBaDdhjFferModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new DaGeJtiaoBaDdhjFferObserverManager<BaseDaGeJtiaoBaDdhjFferModel>() {
                    @Override
                    public void onSuccess(BaseDaGeJtiaoBaDdhjFferModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsDaGeJtiaoBaDdhjFferModel.getProductName());
                        bundle.putString("url", goodsDaGeJtiaoBaDdhjFferModel.getUrl());
                        StaticDaGeJtiaoBaDdhjFferUtil.startActivity(getActivity(), GoodsDetailsDaGeJtiaoBaDdhjFferActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsDaGeJtiaoBaDdhjFferModel> mData){
        if (daGeJtiaoBaDdhjFferGoodsListAdapter == null){
            daGeJtiaoBaDdhjFferGoodsListAdapter = new DaGeJtiaoBaDdhjFferGoodsListAdapter(R.layout.adapter_goods_list_layout_da_ge_jdf_yrjf, mData);
            daGeJtiaoBaDdhjFferGoodsListAdapter.setHasStableIds(true);
            daGeJtiaoBaDdhjFferGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(daGeJtiaoBaDdhjFferGoodsListAdapter);
        } else {
            daGeJtiaoBaDdhjFferGoodsListAdapter.replaceData(mData);
        }
    }
}
