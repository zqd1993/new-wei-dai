package com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.urhdnsertjg.gjuerjfhf.R;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnadapter.BottomJZhuJsiQIajsdnTabAdapter;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnadapter.JZhuJsiQIajsdnHomeFragmentAdapter;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.BaseJZhuJsiQIajsdnEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnentity.JZhuJsiQIajsdnConfigEntity;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnfragment.JZhuJsiQIajsdnHomePageFragment;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnfragment.SetJZhuJsiQIajsdnFragment;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnhttp.JZhuJsiQIajsdnMainApi;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.MyJZhuJsiQIajsdnPreferences;
import com.urhdnsertjg.gjuerjfhf.jzhujqianhydhrnutil.JZhuJsiQIajsdnStatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class HomePageJZhuJsiQIajsdnActivity extends RxAppCompatActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.daohang_rvy)
    RecyclerView daohangRvy;

    private List<TabEntity> tabModels;

    private BottomJZhuJsiQIajsdnTabAdapter bottomJZhuJsiQIajsdnTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jin_zhu_jqi_djrufn_dfke_home_page);
        ButterKnife.bind(this);
        JZhuJsiQIajsdnStatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabEntity tabEntity = new TabEntity();
        tabEntity.setIcon(R.drawable.wer);
        tabEntity.setSelectedIcon(R.drawable.xcvb);
        tabEntity.setName("首页");
        tabEntity.setChecked(true);
        TabEntity tabEntity1 = new TabEntity();
        tabEntity1.setIcon(R.drawable.uyty);
        tabEntity1.setSelectedIcon(R.drawable.lpu);
        tabEntity1.setName("产品");
        tabEntity1.setChecked(false);
        TabEntity tabEntity2 = new TabEntity();
        tabEntity2.setIcon(R.drawable.ertgs);
        tabEntity2.setSelectedIcon(R.drawable.ewrtf);
        tabEntity2.setName("我的");
        tabEntity2.setChecked(false);
        tabModels.add(tabEntity);
        tabModels.add(tabEntity1);
        tabModels.add(tabEntity2);
        initAdapter(tabModels);
        fragments.add(JZhuJsiQIajsdnHomePageFragment.newInstance(1));
        fragments.add(JZhuJsiQIajsdnHomePageFragment.newInstance(2));
        fragments.add(new SetJZhuJsiQIajsdnFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new JZhuJsiQIajsdnHomeFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomePageJZhuJsiQIajsdnActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
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
        bottomJZhuJsiQIajsdnTabAdapter = new BottomJZhuJsiQIajsdnTabAdapter(this, mList);
        bottomJZhuJsiQIajsdnTabAdapter.setHasStableIds(true);
        bottomJZhuJsiQIajsdnTabAdapter.setOnItemClickListener(new BottomJZhuJsiQIajsdnTabAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                mainViewPager.setCurrentItem(position, false);
            }
        });
        daohangRvy.setLayoutManager(new GridLayoutManager(this, 3));
        daohangRvy.setHasFixedSize(true);
        daohangRvy.setAdapter(bottomJZhuJsiQIajsdnTabAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfigValue();
    }

    private void getConfigValue(){
        JZhuJsiQIajsdnMainApi.getRetrofitManager().getApiService().getValue("VIDEOTAPE").enqueue(new Callback<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, retrofit2.Response<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                if (response.body() != null) {
                    MyJZhuJsiQIajsdnPreferences.saveBool("NO_RECORD", !response.body().getData().getVideoTape().equals("0"));
                    if (MyJZhuJsiQIajsdnPreferences.getBool("NO_RECORD")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseJZhuJsiQIajsdnEntity<JZhuJsiQIajsdnConfigEntity>> call, Throwable t) {

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
