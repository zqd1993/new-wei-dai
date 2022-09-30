package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter.HaojjQianShsjHajduBottomTabAdapter;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter.HomeFragmentHaojjQianShsjHajduAdapter;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.BaseHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.HaojjQianShsjHajduConfigEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgfragment.HaojjQianShsjHajduHomePageFragment;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgfragment.SetHaojjQianShsjHajduFragment;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduStatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class HomePageHaojjQianShsjHajduActivity extends RxAppCompatActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.daohang_rvy)
    RecyclerView daohangRvy;

    private List<TabEntity> tabModels;

    private HaojjQianShsjHajduBottomTabAdapter haojjQianShsjHajduBottomTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_hao_jie_she_qtdhfery);
        ButterKnife.bind(this);
        HaojjQianShsjHajduStatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabEntity tabEntity = new TabEntity();
        tabEntity.setIcon(R.drawable.bnsrtu);
        tabEntity.setSelectedIcon(R.drawable.hgnty);
        tabEntity.setName("首页");
        tabEntity.setChecked(true);
        TabEntity tabEntity1 = new TabEntity();
        tabEntity1.setIcon(R.drawable.xvbtr);
        tabEntity1.setSelectedIcon(R.drawable.lpukif);
        tabEntity1.setName("产品");
        tabEntity1.setChecked(false);
        TabEntity tabEntity2 = new TabEntity();
        tabEntity2.setIcon(R.drawable.rtsd);
        tabEntity2.setSelectedIcon(R.drawable.cvhk);
        tabEntity2.setName("我的");
        tabEntity2.setChecked(false);
        tabModels.add(tabEntity);
        tabModels.add(tabEntity1);
        tabModels.add(tabEntity2);
        initAdapter(tabModels);
        fragments.add(HaojjQianShsjHajduHomePageFragment.newInstance(1));
        fragments.add(HaojjQianShsjHajduHomePageFragment.newInstance(2));
        fragments.add(new SetHaojjQianShsjHajduFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new HomeFragmentHaojjQianShsjHajduAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomePageHaojjQianShsjHajduActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAdapter(List<TabEntity> mList){
        haojjQianShsjHajduBottomTabAdapter = new HaojjQianShsjHajduBottomTabAdapter(this, mList);
        haojjQianShsjHajduBottomTabAdapter.setHasStableIds(true);
        haojjQianShsjHajduBottomTabAdapter.setOnItemClickListener(new HaojjQianShsjHajduBottomTabAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                mainViewPager.setCurrentItem(position, false);
            }
        });
        daohangRvy.setLayoutManager(new GridLayoutManager(this, 3));
        daohangRvy.setHasFixedSize(true);
        daohangRvy.setAdapter(haojjQianShsjHajduBottomTabAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfigValue();
    }

    private void getConfigValue(){
        MainHaojjQianShsjHajduApi.getRetrofitManager().getApiService().getValue("VIDEOTAPE").enqueue(new Callback<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, retrofit2.Response<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    HaojjQianShsjHajduMyPreferences.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (HaojjQianShsjHajduMyPreferences.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, Throwable t) {

            }
        });
    }

    public class TabEntity{
        private int icon;

        private int selectedIcon;

        private String name;

        private boolean isChecked;

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
