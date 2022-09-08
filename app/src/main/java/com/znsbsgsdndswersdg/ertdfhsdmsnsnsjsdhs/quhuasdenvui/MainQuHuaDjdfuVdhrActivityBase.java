package com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.R;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvapi.BaseQuHuaDjdfuVdhrRetrofitManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseBaseQuHuaDjdfuVdhrActivity;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvbase.BaseQuHuaDjdfuVdhrObserverManager;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrConfigModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvmodel.BaseQuHuaDjdfuVdhrLoginModel;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvadapter.BaseQuHuaDjdfuVdhrBaseFragmentAdapter;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvfragment.GodsListQuHuaDjdfuVdhrFragmentBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvfragment.MainQuHuaDjdfuVdhrFragmentBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvui.quhuasdenvfragment.MineQuHuaDjdfuVdhrFragmentBase;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrSharePreferencesUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.BaseQuHuaDjdfuVdhrStatusBarUtil;
import com.znsbsgsdndswersdg.ertdfhsdmsnsnsjsdhs.quhuasdenvutil.ToastBaseQuHuaDjdfuVdhrUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainQuHuaDjdfuVdhrActivityBase extends BaseBaseQuHuaDjdfuVdhrActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.adfh, R.drawable.vbzxdrt, R.drawable.nsrtyhxf};
    private int[] checkedIcon = {R.drawable.lpyukd, R.drawable.utryxf, R.drawable.bdr};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qu_hua_hua_erf_engh_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseQuHuaDjdfuVdhrStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainQuHuaDjdfuVdhrFragmentBase());
        mFragments.add(new GodsListQuHuaDjdfuVdhrFragmentBase());
        mFragments.add(new MineQuHuaDjdfuVdhrFragmentBase());

        homeViewPager.setAdapter(new BaseQuHuaDjdfuVdhrBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastBaseQuHuaDjdfuVdhrUtil.showShort("再按一次退出程序");
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
        phone = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("phone");
        ip = BaseQuHuaDjdfuVdhrSharePreferencesUtil.getString("ip");
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrLoginModel> model) {

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
        Observable<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>> observable = BaseQuHuaDjdfuVdhrRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseQuHuaDjdfuVdhrObserverManager<BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel>>() {
                    @Override
                    public void onSuccess(BaseQuHuaDjdfuVdhrModel<BaseQuHuaDjdfuVdhrConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseQuHuaDjdfuVdhrConfigModel baseQuHuaDjdfuVdhrConfigModel = model.getData();
                        if (baseQuHuaDjdfuVdhrConfigModel != null) {
                            BaseQuHuaDjdfuVdhrSharePreferencesUtil.saveBool("NO_RECORD", !baseQuHuaDjdfuVdhrConfigModel.getVideoTape().equals("0"));
                            if (BaseQuHuaDjdfuVdhrSharePreferencesUtil.getBool("NO_RECORD")) {
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