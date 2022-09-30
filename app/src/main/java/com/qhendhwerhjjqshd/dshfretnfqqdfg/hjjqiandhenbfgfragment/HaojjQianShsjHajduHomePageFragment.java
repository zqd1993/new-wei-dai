package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity.HaojjQianShsjHajduShowGoogsInfoActivity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter.HaojjQianShsjHajduGoodsInfoItemAdapter;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.BaseHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.GoodsHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.CommonHaojjQianShsjHajduUtil;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class HaojjQianShsjHajduHomePageFragment extends RxFragment {

    @BindView(R.id.goods_list)
    RecyclerView goodsList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.no_data_ll)
    View noDataLl;
    @BindView(R.id.parent_cl)
    View parentCl;
    @BindView(R.id.tishi_fl)
    View tishiFl;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private GoodsHaojjQianShsjHajduEntity goodsHaojjQianShsjHajduEntity;
    private int mobileType, type;
    private String phone;
    private HaojjQianShsjHajduGoodsInfoItemAdapter haojjQianShsjHajduGoodsInfoItemAdapter;

    public static HaojjQianShsjHajduHomePageFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        HaojjQianShsjHajduHomePageFragment fragment = new HaojjQianShsjHajduHomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hao_jie_she_qtdhfery_home_page, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
        }
        if (type == 2){
            tishiFl.setVisibility(View.GONE);
        } else {
            tishiFl.setVisibility(View.VISIBLE);
        }
        refreshLayout.setOnRefreshListener(() -> getGoodsList());
        parentCl.setOnClickListener(v -> productClick(goodsHaojjQianShsjHajduEntity));
        noDataLl.setOnClickListener(v -> getGoodsList());
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = HaojjQianShsjHajduMyPreferences.getInt("mobileType");
        phone = HaojjQianShsjHajduMyPreferences.getString("phone");
        goodsHaojjQianShsjHajduEntity = null;
        MainHaojjQianShsjHajduApi.getRetrofitManager().getApiService().getGoodsList(mobileType, phone).enqueue(new Callback<BaseHaojjQianShsjHajduEntity<List<GoodsHaojjQianShsjHajduEntity>>>() {
            @Override
            public void onResponse(Call<BaseHaojjQianShsjHajduEntity<List<GoodsHaojjQianShsjHajduEntity>>> call, retrofit2.Response<BaseHaojjQianShsjHajduEntity<List<GoodsHaojjQianShsjHajduEntity>>> response) {
                if (response.body() == null){
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                    return;
                }
                List<GoodsHaojjQianShsjHajduEntity> entity = response.body().getData();
                if (entity != null && entity.size() > 0){
                    noDataLl.setVisibility(View.GONE);
                    goodsList.setVisibility(View.VISIBLE);
                    goodsHaojjQianShsjHajduEntity = entity.get(0);
                    haojjQianShsjHajduGoodsInfoItemAdapter = new HaojjQianShsjHajduGoodsInfoItemAdapter(getActivity(), entity);
                    haojjQianShsjHajduGoodsInfoItemAdapter.setHasStableIds(true);
                    haojjQianShsjHajduGoodsInfoItemAdapter.setOnItemClickListener(new HaojjQianShsjHajduGoodsInfoItemAdapter.OnItemClickListener() {
                        @Override
                        public void itemClicked(GoodsHaojjQianShsjHajduEntity goodsHaojjQianShsjHajduEntity) {
                            productClick(goodsHaojjQianShsjHajduEntity);
                        }
                    });
                    goodsList.setHasFixedSize(true);
                    goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    goodsList.setAdapter(haojjQianShsjHajduGoodsInfoItemAdapter);
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseHaojjQianShsjHajduEntity<List<GoodsHaojjQianShsjHajduEntity>>> call, Throwable t) {

            }
        });
    }

    private void productClick(GoodsHaojjQianShsjHajduEntity goodsHaojjQianShsjHajduEntity){
        phone = HaojjQianShsjHajduMyPreferences.getString("phone");
        MainHaojjQianShsjHajduApi.getRetrofitManager().getApiService().productClick(goodsHaojjQianShsjHajduEntity.getId(), phone).enqueue(new Callback<BaseHaojjQianShsjHajduEntity>() {
            @Override
            public void onResponse(Call<BaseHaojjQianShsjHajduEntity> call, retrofit2.Response<BaseHaojjQianShsjHajduEntity> response) {
                bundle = new Bundle();
                bundle.putString("name", goodsHaojjQianShsjHajduEntity.getProductName());
                bundle.putString("url", goodsHaojjQianShsjHajduEntity.getUrl());
                CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), HaojjQianShsjHajduShowGoogsInfoActivity.class, bundle);
            }

            @Override
            public void onFailure(Call<BaseHaojjQianShsjHajduEntity> call, Throwable t) {
                bundle = new Bundle();
                bundle.putString("name", goodsHaojjQianShsjHajduEntity.getProductName());
                bundle.putString("url", goodsHaojjQianShsjHajduEntity.getUrl());
                CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), HaojjQianShsjHajduShowGoogsInfoActivity.class, bundle);
            }
        });
    }

}
