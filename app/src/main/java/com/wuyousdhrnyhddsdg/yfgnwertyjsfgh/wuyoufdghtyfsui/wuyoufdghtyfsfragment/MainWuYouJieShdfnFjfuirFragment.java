package com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.R;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsapi.RetrofitWuYouJieShdfnFjfuirManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.BaseWuYouJieShdfnFjfuirFragment;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsbase.WuYouJieShdfnFjfuirObserverManager;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.WuYouJieShdfnFjfuirBaseModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsmodel.GoodsWuYouJieShdfnFjfuirModel;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.GoodsDetailsWuYouJieShdfnFjfuirActivity;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsui.wuyoufdghtyfsadapter.WuYouJieShdfnFjfuirGoodsListAdapter;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.WuYouJieShdfnFjfuirSharePreferencesUtil;
import com.wuyousdhrnyhddsdg.yfgnwertyjsfgh.wuyoufdghtyfsutil.StaticWuYouJieShdfnFjfuirUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainWuYouJieShdfnFjfuirFragment extends BaseWuYouJieShdfnFjfuirFragment {

    private RecyclerView goodsList;
    private View noDataLl, rootLl;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mobileType;
    private String phone;
    private WuYouJieShdfnFjfuirGoodsListAdapter wuYouJieShdfnFjfuirGoodsListAdapter;
    private Bundle bundle;
    private GoodsWuYouJieShdfnFjfuirModel mGoodsWuYouJieShdfnFjfuirModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wu_you_jie_jdf_eryj_main;
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
        rootLl.setOnClickListener(v -> productClick(mGoodsWuYouJieShdfnFjfuirModel));
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
        mobileType = WuYouJieShdfnFjfuirSharePreferencesUtil.getInt("mobileType");
        phone = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("phone");
        mGoodsWuYouJieShdfnFjfuirModel = null;
        Observable<WuYouJieShdfnFjfuirBaseModel<List<GoodsWuYouJieShdfnFjfuirModel>>> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().
                getApiService().getGoodsList(mobileType, phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel<List<GoodsWuYouJieShdfnFjfuirModel>>>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel<List<GoodsWuYouJieShdfnFjfuirModel>> model) {
                        if (model != null){
                            if (model.getData() != null && model.getData().size() > 0){
                                noDataLl.setVisibility(View.GONE);
                                goodsList.setVisibility(View.VISIBLE);
                                mGoodsWuYouJieShdfnFjfuirModel = model.getData().get(0);
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
                        if (wuYouJieShdfnFjfuirGoodsListAdapter == null){
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

    private void productClick(GoodsWuYouJieShdfnFjfuirModel goodsWuYouJieShdfnFjfuirModel) {
        phone = WuYouJieShdfnFjfuirSharePreferencesUtil.getString("phone");
        Observable<WuYouJieShdfnFjfuirBaseModel> observable = RetrofitWuYouJieShdfnFjfuirManager.getRetrofitManager().
                getApiService().productClick(goodsWuYouJieShdfnFjfuirModel.getId(), phone);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYouJieShdfnFjfuirObserverManager<WuYouJieShdfnFjfuirBaseModel>() {
                    @Override
                    public void onSuccess(WuYouJieShdfnFjfuirBaseModel model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {
                        bundle = new Bundle();
                        bundle.putString("title", goodsWuYouJieShdfnFjfuirModel.getProductName());
                        bundle.putString("url", goodsWuYouJieShdfnFjfuirModel.getUrl());
                        StaticWuYouJieShdfnFjfuirUtil.startActivity(getActivity(), GoodsDetailsWuYouJieShdfnFjfuirActivity.class, bundle);
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    private void setListData(List<GoodsWuYouJieShdfnFjfuirModel> mData){
        if (wuYouJieShdfnFjfuirGoodsListAdapter == null){
            wuYouJieShdfnFjfuirGoodsListAdapter = new WuYouJieShdfnFjfuirGoodsListAdapter(R.layout.adapter_goods_list_layout_wu_you_jie_jdf_eryj, mData);
            wuYouJieShdfnFjfuirGoodsListAdapter.setHasStableIds(true);
            wuYouJieShdfnFjfuirGoodsListAdapter.setItemClickListener(item -> productClick(item));
            goodsList.setHasFixedSize(true);
            goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
            goodsList.setAdapter(wuYouJieShdfnFjfuirGoodsListAdapter);
        } else {
            wuYouJieShdfnFjfuirGoodsListAdapter.replaceData(mData);
        }
    }
}
