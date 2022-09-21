package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi.RetrofitJiuJiHdfnfhGDhsdManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdFragment;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.JiuJiHdfnfhGDhsdObserverManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdBaseModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.GoodsJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.GoodsDetailsJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertadapter.JiuJiHdfnfhGDhsdGoodsListAdapter;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.StaticJiuJiHdfnfhGDhsdUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainJiuJiHdfnfhGDhsdFragment extends BaseJiuJiHdfnfhGDhsdFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private JiuJiHdfnfhGDhsdGoodsListAdapter jiuJiHdfnfhGDhsdGoodsListAdapter;
    private Bundle bundle;
    private GoodsJiuJiHdfnfhGDhsdModel mGoodsJiuJiHdfnfhGDhsdModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiu_ji_fdher_reytjyh_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsJiuJiHdfnfhGDhsdModel));
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
        mobileType = JiuJiHdfnfhGDhsdSharePreferencesUtil.getInt("mobileType");
        phone = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("phone");
        mGoodsJiuJiHdfnfhGDhsdModel = null;
        Observable<JiuJiHdfnfhGDhsdBaseModel<List<GoodsJiuJiHdfnfhGDhsdModel>>> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel<List<GoodsJiuJiHdfnfhGDhsdModel>>>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel<List<GoodsJiuJiHdfnfhGDhsdModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsJiuJiHdfnfhGDhsdModel = model.getData().get(0);
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
                        if (jiuJiHdfnfhGDhsdGoodsListAdapter == null){
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

    private void productClick(GoodsJiuJiHdfnfhGDhsdModel goodsJiuJiHdfnfhGDhsdModel) {
        phone = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("phone");
        Observable<JiuJiHdfnfhGDhsdBaseModel> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().
                getApiService().productClick(goodsJiuJiHdfnfhGDhsdModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsJiuJiHdfnfhGDhsdModel.getProductName());
                        bundle.putString("url", goodsJiuJiHdfnfhGDhsdModel.getUrl());
                        StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), GoodsDetailsJiuJiHdfnfhGDhsdActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsJiuJiHdfnfhGDhsdModel> mData){
        if (jiuJiHdfnfhGDhsdGoodsListAdapter == null){
            jiuJiHdfnfhGDhsdGoodsListAdapter = new JiuJiHdfnfhGDhsdGoodsListAdapter(R.layout.adapter_goods_list_layout_jiu_ji_fdher_reytjyh, mData);
            jiuJiHdfnfhGDhsdGoodsListAdapter.setHasStableIds(true);
            jiuJiHdfnfhGDhsdGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(jiuJiHdfnfhGDhsdGoodsListAdapter);
        } else {
            jiuJiHdfnfhGDhsdGoodsListAdapter.replaceData(mData);
        }
    }
}
