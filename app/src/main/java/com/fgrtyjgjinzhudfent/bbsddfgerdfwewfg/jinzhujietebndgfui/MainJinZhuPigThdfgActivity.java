package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.R;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfapi.JinZhuPigThdfgRetrofitManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.JinZhuPigThdfgObserverManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.BaseJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgConfigModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.LoginJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgfadapter.JinZhuPigThdfgBaseFragmentAdapter;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgffragment.JinZhuPigThdfgGodsListJinZhuPigThdfgFragment;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgffragment.JinZhuPigThdfgMainJinZhuPigThdfgFragment;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgffragment.JinZhuPigThdfgMineJinZhuPigThdfgFragment;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StatusJinZhuPigThdfgBarUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.ToastJinZhuPigThdfgUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainJinZhuPigThdfgActivity extends BaseJinZhuPigThdfgActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.qwetrgxf, R.drawable.ghfgn, R.drawable.zcvzbdry};
    private int[] checkedIcon = {R.drawable.rghjcfg, R.drawable.kltfyufgj, R.drawable.hjgn};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_jin_zhu_asf_pig_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StatusJinZhuPigThdfgBarUtil.setTransparent(this, false);
        logins();
        homeViewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 3; i++) {
            int position = i;
            CustomTabEntity customTabEntity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[position];
                }

                @Override
                public int getTabSelectedIcon() {
                    return checkedIcon[position];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return uncheckedIcon[position];
                }
            };
            customTabEntities.add(customTabEntity);
        }
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                homeViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(new JinZhuPigThdfgMainJinZhuPigThdfgFragment());
        mFragments.add(new JinZhuPigThdfgGodsListJinZhuPigThdfgFragment());
        mFragments.add(new JinZhuPigThdfgMineJinZhuPigThdfgFragment());

        homeViewPager.setAdapter(new JinZhuPigThdfgBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastJinZhuPigThdfgUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logins() {
        phone = JinZhuPigThdfgSharePreferencesUtil.getString("phone");
        ip = JinZhuPigThdfgSharePreferencesUtil.getString("ip");
        Observable<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<LoginJinZhuPigThdfgModel> model) {

                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getConfig();
    }

    private void getConfig() {
        Observable<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JinZhuPigThdfgConfigModel jinZhuPigThdfgConfigModel = model.getData();
                        if (jinZhuPigThdfgConfigModel != null) {
                            JinZhuPigThdfgSharePreferencesUtil.saveBool("NO_RECORD", !jinZhuPigThdfgConfigModel.getVideoTape().equals("0"));
                            if (JinZhuPigThdfgSharePreferencesUtil.getBool("NO_RECORD")) {
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        Log.e("Throwable", throwable.toString());
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDisposable(Disposable disposable) {
                    }
                });
    }
}