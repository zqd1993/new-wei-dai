package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyapi.CaiJieTongYshVdjrytRetrofitManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.BaseCaiJieTongYshVdjrytFragment;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytObserverManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytBaseModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytGoodsModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.GoodsDetailsActivityCaiJieTongYshVdjryt;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyadapter.GoodsCaiJieTongYshVdjrytListAdapter;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.StaticCaiJieTongYshVdjrytUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GodsListCaiJieTongYshVdjrytFragment extends BaseCaiJieTongYshVdjrytFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private GoodsCaiJieTongYshVdjrytListAdapter goodsCaiJieTongYshVdjrytListAdapter;
    private Bundle bundle;
    private CaiJieTongYshVdjrytGoodsModel mCaiJieTongYshVdjrytGoodsModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cai_jie_tong_drt_etfnh_goods_list;
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
        rootLl.setOnClickListener(v -> productClick(mCaiJieTongYshVdjrytGoodsModel));
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
        mobileType = CaiJieTongYshVdjrytSharePreferencesUtil.getInt("mobileType");
        phone = CaiJieTongYshVdjrytSharePreferencesUtil.getString("phone");
        mCaiJieTongYshVdjrytGoodsModel = null;
        Observable<CaiJieTongYshVdjrytBaseModel<List<CaiJieTongYshVdjrytGoodsModel>>> observable = CaiJieTongYshVdjrytRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new CaiJieTongYshVdjrytObserverManager<CaiJieTongYshVdjrytBaseModel<List<CaiJieTongYshVdjrytGoodsModel>>>() {
                    @Override
                    public void onSuccess(CaiJieTongYshVdjrytBaseModel<List<CaiJieTongYshVdjrytGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mCaiJieTongYshVdjrytGoodsModel = model.getData().get(0);
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
                        if (goodsCaiJieTongYshVdjrytListAdapter == null){
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

    private void productClick(CaiJieTongYshVdjrytGoodsModel caiJieTongYshVdjrytGoodsModel) {
        phone = CaiJieTongYshVdjrytSharePreferencesUtil.getString("phone");
        Observable<CaiJieTongYshVdjrytBaseModel> observable = CaiJieTongYshVdjrytRetrofitManager.getRetrofitManager().
                getApiService().productClick(caiJieTongYshVdjrytGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new CaiJieTongYshVdjrytObserverManager<CaiJieTongYshVdjrytBaseModel>() {
                    @Override
                    public void onSuccess(CaiJieTongYshVdjrytBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", caiJieTongYshVdjrytGoodsModel.getProductName());
                        bundle.putString("url", caiJieTongYshVdjrytGoodsModel.getUrl());
                        StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), GoodsDetailsActivityCaiJieTongYshVdjryt.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<CaiJieTongYshVdjrytGoodsModel> mData){
        if (goodsCaiJieTongYshVdjrytListAdapter == null){
            goodsCaiJieTongYshVdjrytListAdapter = new GoodsCaiJieTongYshVdjrytListAdapter(R.layout.adapter_goods_list_layout_cai_jie_tong_drt_etfnh, mData);
            goodsCaiJieTongYshVdjrytListAdapter.setHasStableIds(true);
            goodsCaiJieTongYshVdjrytListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(goodsCaiJieTongYshVdjrytListAdapter);
        } else {
            goodsCaiJieTongYshVdjrytListAdapter.replaceData(mData);
        }
    }
}
