package com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dqlsdjddfser.fdhqwendsfe.R;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgapi.TaoJieHauHsyaJhsdyRetrofitManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.BaseTaoJieHauHsyaJhsdyFragment;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgbase.TaoJieHauHsyaJhsdyObserverManager;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyBaseModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgmodel.TaoJieHauHsyaJhsdyGoodsModel;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.GoodsDetailsTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.MainTaoJieHauHsyaJhsdyActivity;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgui.rongjiesdfgwretadapter.TaoJieHauHsyaJhsdyGoodsListAdapter;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.SharePreferencesUtilTaoJieHauHsyaJhsdy;
import com.dqlsdjddfser.fdhqwendsfe.taojiehuaefnfhshsdfgutil.StaticTaoJieHauHsyaJhsdyUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainTaoJieHauHsyaJhsdyFragment extends BaseTaoJieHauHsyaJhsdyFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private TaoJieHauHsyaJhsdyGoodsListAdapter taoJieHauHsyaJhsdyGoodsListAdapter;
    private Bundle bundle;
    private TaoJieHauHsyaJhsdyGoodsModel mTaoJieHauHsyaJhsdyGoodsModel;
    private View moreTv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_tao_jie_hua_djheru_fhentj;
    }

    @Override
    public void initData() {
        goodsList = rootView.findViewById(R.id.goods_list);
        noDataLl = rootView.findViewById(R.id.no_data_ll);
        rootLl = rootView.findViewById(R.id.root_ll);
        moreTv = rootView.findViewById(R.id.more_tv);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> getGoodsList());
        rootLl.setOnClickListener(v -> productClick(mTaoJieHauHsyaJhsdyGoodsModel));
        noDataLl.setOnClickListener(v -> {
            getGoodsList();
        });
        moreTv.setOnClickListener(v -> {
            ((MainTaoJieHauHsyaJhsdyActivity)getActivity()).changePage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = SharePreferencesUtilTaoJieHauHsyaJhsdy.getInt("mobileType");
        phone = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("phone");
        mTaoJieHauHsyaJhsdyGoodsModel = null;
        Observable<TaoJieHauHsyaJhsdyBaseModel<List<TaoJieHauHsyaJhsdyGoodsModel>>> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel<List<TaoJieHauHsyaJhsdyGoodsModel>>>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel<List<TaoJieHauHsyaJhsdyGoodsModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mTaoJieHauHsyaJhsdyGoodsModel = model.getData().get(0);
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
                        if (taoJieHauHsyaJhsdyGoodsListAdapter == null){
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

    private void productClick(TaoJieHauHsyaJhsdyGoodsModel taoJieHauHsyaJhsdyGoodsModel) {
        phone = SharePreferencesUtilTaoJieHauHsyaJhsdy.getString("phone");
        Observable<TaoJieHauHsyaJhsdyBaseModel> observable = TaoJieHauHsyaJhsdyRetrofitManager.getRetrofitManager().
                getApiService().productClick(taoJieHauHsyaJhsdyGoodsModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new TaoJieHauHsyaJhsdyObserverManager<TaoJieHauHsyaJhsdyBaseModel>() {
                    @Override
                    public void onSuccess(TaoJieHauHsyaJhsdyBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", taoJieHauHsyaJhsdyGoodsModel.getProductName());
                        bundle.putString("url", taoJieHauHsyaJhsdyGoodsModel.getUrl());
                        StaticTaoJieHauHsyaJhsdyUtil.startActivity(getActivity(), GoodsDetailsTaoJieHauHsyaJhsdyActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<TaoJieHauHsyaJhsdyGoodsModel> mData){
        if (taoJieHauHsyaJhsdyGoodsListAdapter == null){
            taoJieHauHsyaJhsdyGoodsListAdapter = new TaoJieHauHsyaJhsdyGoodsListAdapter(R.layout.adapter_goods_list_layout_tao_jie_hua_djheru_fhentj, mData);
            taoJieHauHsyaJhsdyGoodsListAdapter.setHasStableIds(true);
            taoJieHauHsyaJhsdyGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(taoJieHauHsyaJhsdyGoodsListAdapter);
        } else {
            taoJieHauHsyaJhsdyGoodsListAdapter.replaceData(mData);
        }
    }
}
