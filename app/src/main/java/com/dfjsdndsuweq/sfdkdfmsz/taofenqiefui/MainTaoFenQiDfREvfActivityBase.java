package com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dfjsdndsuweq.sfdkdfmsz.R;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefapi.BaseTaoFenQiDfREvfRetrofitManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseBaseTaoFenQiDfREvfActivity;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefbase.BaseTaoFenQiDfREvfObserverManager;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfConfigModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefmodel.BaseTaoFenQiDfREvfLoginModel;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqiefadapter.BaseTaoFenQiDfREvfBaseFragmentAdapter;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqieffragment.GodsListTaoFenQiDfREvfFragmentBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqieffragment.MainTaoFenQiDfREvfFragmentBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefui.taofenqieffragment.MineTaoFenQiDfREvfFragmentBase;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfSharePreferencesUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.BaseTaoFenQiDfREvfStatusBarUtil;
import com.dfjsdndsuweq.sfdkdfmsz.taofenqiefutil.ToastBaseTaoFenQiDfREvfUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainTaoFenQiDfREvfActivityBase extends BaseBaseTaoFenQiDfREvfActivity {

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
        return R.layout.activity_tao_fen_qi_rtgr_vbd_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        BaseTaoFenQiDfREvfStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainTaoFenQiDfREvfFragmentBase());
        mFragments.add(new GodsListTaoFenQiDfREvfFragmentBase());
        mFragments.add(new MineTaoFenQiDfREvfFragmentBase());

        homeViewPager.setAdapter(new BaseTaoFenQiDfREvfBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastBaseTaoFenQiDfREvfUtil.showShort("再按一次退出程序");
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
        phone = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("phone");
        ip = BaseTaoFenQiDfREvfSharePreferencesUtil.getString("ip");
        Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfLoginModel>> observable = BaseTaoFenQiDfREvfRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseTaoFenQiDfREvfObserverManager<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfLoginModel>>() {
                    @Override
                    public void onSuccess(BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfLoginModel> model) {

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
        Observable<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>> observable = BaseTaoFenQiDfREvfRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new BaseTaoFenQiDfREvfObserverManager<BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel>>() {
                    @Override
                    public void onSuccess(BaseTaoFenQiDfREvfModel<BaseTaoFenQiDfREvfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        BaseTaoFenQiDfREvfConfigModel baseTaoFenQiDfREvfConfigModel = model.getData();
                        if (baseTaoFenQiDfREvfConfigModel != null) {
                            BaseTaoFenQiDfREvfSharePreferencesUtil.saveBool("NO_RECORD", !baseTaoFenQiDfREvfConfigModel.getVideoTape().equals("0"));
                            if (BaseTaoFenQiDfREvfSharePreferencesUtil.getBool("NO_RECORD")) {
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