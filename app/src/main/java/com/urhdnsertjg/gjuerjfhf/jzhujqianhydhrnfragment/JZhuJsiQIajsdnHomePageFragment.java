package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity.JZhuJsiQIajsdnShowGoogsInfoActivity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnadapter.JZhuJsiQIajsdnGoodsInfoItemAdapter;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.BaseJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnGoodsEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp.JZhuJsiQIajsdnMainApi;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnCommonUtil;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class JZhuJsiQIajsdnHomePageFragment extends RxFragment {

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
    private JZhuJsiQIajsdnGoodsEntity JZhuJsiQIajsdnGoodsEntity;
    private int mobileType, type;
    private String phone;
    private JZhuJsiQIajsdnGoodsInfoItemAdapter JZhuJsiQIajsdnGoodsInfoItemAdapter;

    public static JZhuJsiQIajsdnHomePageFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        JZhuJsiQIajsdnHomePageFragment fragment = new JZhuJsiQIajsdnHomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page_jin_zhu_jqi_djrufn_dfke, null);
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
        parentCl.setOnClickListener(v -> productClick(JZhuJsiQIajsdnGoodsEntity));
        noDataLl.setOnClickListener(v -> getGoodsList());
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoodsList();
    }

    private void getGoodsList() {
        mobileType = MyJZhuJsiQIajsdnPreferences.getInt("mobileType");
        phone = MyJZhuJsiQIajsdnPreferences.getString("phone");
        JZhuJsiQIajsdnGoodsEntity = null;
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().getGoodsList(mobileType, phone).enqueue(new Callback<BaseJZhuJsiQIajsdnEntity<List<JZhuJsiQIajsdnGoodsEntity>>>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity<List<JZhuJsiQIajsdnGoodsEntity>>> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity<List<JZhuJsiQIajsdnGoodsEntity>>> response) {
                if (response.body() == null){
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                    return;
                }
                List<JZhuJsiQIajsdnGoodsEntity> entity = response.body().getData();
                if (entity != null && entity.size() > 0){
                    noDataLl.setVisibility(View.GONE);
                    goodsList.setVisibility(View.VISIBLE);
                    JZhuJsiQIajsdnGoodsEntity = entity.get(0);
                    JZhuJsiQIajsdnGoodsInfoItemAdapter = new JZhuJsiQIajsdnGoodsInfoItemAdapter(getActivity(), entity);
                    JZhuJsiQIajsdnGoodsInfoItemAdapter.setHasStableIds(true);
                    JZhuJsiQIajsdnGoodsInfoItemAdapter.setOnItemClickListener(new JZhuJsiQIajsdnGoodsInfoItemAdapter.OnItemClickListener() {
                        @Override
                        public void itemClicked(JZhuJsiQIajsdnGoodsEntity JZhuJsiQIajsdnGoodsEntity) {
                            productClick(JZhuJsiQIajsdnGoodsEntity);
                        }
                    });
                    goodsList.setHasFixedSize(true);
                    goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    goodsList.setAdapter(JZhuJsiQIajsdnGoodsInfoItemAdapter);
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                    goodsList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity<List<JZhuJsiQIajsdnGoodsEntity>>> call, Throwable t) {

            }
        });
    }

    private void productClick(JZhuJsiQIajsdnGoodsEntity JZhuJsiQIajsdnGoodsEntity){
        phone = MyJZhuJsiQIajsdnPreferences.getString("phone");
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().productClick(JZhuJsiQIajsdnGoodsEntity.getId(), phone).enqueue(new Callback<BaseJZhuJsiQIajsdnEntity>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity> response) {
                bundle = new Bundle();
                bundle.putString("name", JZhuJsiQIajsdnGoodsEntity.getProductName());
                bundle.putString("url", JZhuJsiQIajsdnGoodsEntity.getUrl());
                JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), JZhuJsiQIajsdnShowGoogsInfoActivity.class, bundle);
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity> call, Throwable t) {
                bundle = new Bundle();
                bundle.putString("name", JZhuJsiQIajsdnGoodsEntity.getProductName());
                bundle.putString("url", JZhuJsiQIajsdnGoodsEntity.getUrl());
                JZhuJsiQIajsdnCommonUtil.startActivity(getActivity(), JZhuJsiQIajsdnShowGoogsInfoActivity.class, bundle);
            }
        });
    }

}
