package com.youjiegherh.pocketqwrh.youjiewetdfhui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.youjiegherh.pocketqwrh.R;
import com.youjiegherh.pocketqwrh.youjiewetdfhapi.NewCodeXiaoNiuKuaiRetrofitManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.BaseNewCodeXiaoNiuKuaiActivity;
import com.youjiegherh.pocketqwrh.youjiewetdfhbase.NewCodeXiaoNiuKuaiObserverManager;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.BaseYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.ConfigYouJieSDjdfiModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhmodel.YouJieSDjdfiLoginModel;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhadapter.YouJieSDjdfiBaseFragmentAdapter;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhfragment.YouJieSDjdfiGodsListFragment;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhfragment.MainYouJieSDjdfiFragment;
import com.youjiegherh.pocketqwrh.youjiewetdfhui.youjiewetdfhfragment.MineYouJieSDjdfiFragment;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiSharePreferencesUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.YouJieSDjdfiStatusBarUtil;
import com.youjiegherh.pocketqwrh.youjiewetdfhutil.ToastYouJieSDjdfiUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainYouJieSDjdfiActivity extends BaseNewCodeXiaoNiuKuaiActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.mnmzxzeryh, R.drawable.wwegzdh, R.drawable.cvhdfghaz};
    private int[] checkedIcon = {R.drawable.rrtxfhg, R.drawable.trhfgx, R.drawable.ifthjgvj};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_you_jie_iejbvr_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        YouJieSDjdfiStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainYouJieSDjdfiFragment());
        mFragments.add(new YouJieSDjdfiGodsListFragment());
        mFragments.add(new MineYouJieSDjdfiFragment());

        homeViewPager.setAdapter(new YouJieSDjdfiBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastYouJieSDjdfiUtil.showShort("再按一次退出程序");
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
        phone = YouJieSDjdfiSharePreferencesUtil.getString("phone");
        ip = YouJieSDjdfiSharePreferencesUtil.getString("ip");
        Observable<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<YouJieSDjdfiLoginModel> model) {

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
        Observable<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>> observable = NewCodeXiaoNiuKuaiRetrofitManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new NewCodeXiaoNiuKuaiObserverManager<BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel>>() {
                    @Override
                    public void onSuccess(BaseYouJieSDjdfiModel<ConfigYouJieSDjdfiModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigYouJieSDjdfiModel configYouJieSDjdfiModel = model.getData();
                        if (configYouJieSDjdfiModel != null) {
                            YouJieSDjdfiSharePreferencesUtil.saveBool("NO_RECORD", !configYouJieSDjdfiModel.getVideoTape().equals("0"));
                            if (YouJieSDjdfiSharePreferencesUtil.getBool("NO_RECORD")) {
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