package com.fghjtuytjuj.drtysghjertyh.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.fghjtuytjuj.drtysghjertyh.bean.BaseModel;
import com.fghjtuytjuj.drtysghjertyh.bean.ProductBean;
import com.fghjtuytjuj.drtysghjertyh.common.SharePreferencesUtil;
import com.fghjtuytjuj.drtysghjertyh.common.StaticCommon;
import com.fghjtuytjuj.drtysghjertyh.net.NetApi;
import com.fjsdkqwj.pfdioewjnsd.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WorkFragment extends RxFragment {

    private LinearLayout goodsListLl;
    private View noDataLl, parentFl, zhanweiView, work_bg_iv_1;
    private SwipeRefreshLayout refreshLayout;
    private ImageView workBgIv;

    private ProductBean productBean;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private int mobileType, type;
    private String phone;

    public static WorkFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_work, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
        }
        goodsListLl = rootView.findViewById(R.id.goods_list_ll);
        noDataLl = rootView.findViewById(R.id.no_data_ll);
        parentFl = rootView.findViewById(R.id.parent_fl);
        workBgIv = rootView.findViewById(R.id.work_bg_iv);
        zhanweiView = rootView.findViewById(R.id.zhanwei_view);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        work_bg_iv_1 = rootView.findViewById(R.id.work_bg_iv_1);
        if (type == 2){
            zhanweiView.setVisibility(View.GONE);
            work_bg_iv_1.setVisibility(View.VISIBLE);
        } else {
            zhanweiView.setVisibility(View.VISIBLE);
            work_bg_iv_1.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(() -> getGoodsList());
        parentFl.setOnClickListener(v -> productClick(productBean));
        noDataLl.setOnClickListener(v -> getGoodsList());
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = SharePreferencesUtil.getInt("mobileType");
        phone = SharePreferencesUtil.getString("phone");
        productBean = null;
        NetApi.getNetApi().getNetInterface().getGoodsList(mobileType, phone).enqueue(new Callback<BaseModel<List<ProductBean>>>() {
            @Override
            public void onResponse(Call<BaseModel<List<ProductBean>>> call, retrofit2.Response<BaseModel<List<ProductBean>>> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null){
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsListLl.setVisibility(View.GONE);
                    return;
                }
                List<ProductBean> entity = response.body().getData();
                if (entity != null && entity.size() > 0){
                    noDataLl.setVisibility(View.GONE);
                    goodsListLl.setVisibility(View.VISIBLE);
                    productBean = entity.get(0);
                    addProductView(entity);
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsListLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseModel<List<ProductBean>>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void addProductView(List<ProductBean> mList) {
        goodsListLl.removeAllViews();
        for (ProductBean model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_layout, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView date_tv = view.findViewById(R.id.date_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View parentFl = view.findViewById(R.id.parent_ll);
            Glide.with(getActivity()).load(NetApi.API_URL + model.getProductLogo()).into(pic);
            product_name_tv.setText(model.getProductName());
            date_tv.setText(model.getDes());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }


    private void productClick(ProductBean productBean){
        if (productBean == null){
            return;
        }
        phone = SharePreferencesUtil.getString("phone");
        NetApi.getNetApi().getNetInterface().productClick(productBean.getId(), phone).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                bundle = new Bundle();
                bundle.putString("name", productBean.getProductName());
                bundle.putString("url", productBean.getUrl());
                StaticCommon.startActivity(getActivity(), ShowH5Activity.class, bundle);
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                bundle = new Bundle();
                bundle.putString("name", productBean.getProductName());
                bundle.putString("url", productBean.getUrl());
                StaticCommon.startActivity(getActivity(), ShowH5Activity.class, bundle);
            }
        });
    }
}
