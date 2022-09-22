package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dryghsfzhaocaimao.regfhseradfert.R;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegapi.RetrofitZhaoCaiCatKfrtManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.ZhaoCaiCatKfrtObserverManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtBaseModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ConfigZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.LoginZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegadapter.ZhaoCaiCatKfrtBaseFragmentAdapter;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegfragment.ZhaoCaiCatKfrtGodsListZhaoCaiCatKfrtFragment;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegfragment.MainZhaoCaiCatKfrtZhaoCaiCatKfrtFragment;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegfragment.MineZhaoCaiCatKfrtZhaoCaiCatKfrtFragment;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtStatusBarUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ToastZhaoCaiCatKfrtUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainZhaoCaiCatKfrtZhaoCaiCatKfrtActivity extends BaseZhaoCaiCatKfrtActivity {

    private ViewPager2 homeViewPager;
    private CommonTabLayout tabLayout;

    private String phone, ip;
    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "精选", "我的"};
    private int[] uncheckedIcon = {R.drawable.srty, R.drawable.tyvbn, R.drawable.lpyuk};
    private int[] checkedIcon = {R.drawable.zvbtdf, R.drawable.lpyuj, R.drawable.reyfgh};
    private ArrayList<CustomTabEntity> customTabEntities;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhao_cai_mao_dfget_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ZhaoCaiCatKfrtStatusBarUtil.setTransparent(this, false);
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
        mFragments.add(new MainZhaoCaiCatKfrtZhaoCaiCatKfrtFragment());
        mFragments.add(new ZhaoCaiCatKfrtGodsListZhaoCaiCatKfrtFragment());
        mFragments.add(new MineZhaoCaiCatKfrtZhaoCaiCatKfrtFragment());

        homeViewPager.setAdapter(new ZhaoCaiCatKfrtBaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastZhaoCaiCatKfrtUtil.showShort("再按一次退出程序");
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
        phone = ZhaoCaiCatKfrtSharePreferencesUtil.getString("phone");
        ip = ZhaoCaiCatKfrtSharePreferencesUtil.getString("ip");
        Observable<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().
                getApiService().logins(phone, ip);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel>>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel<LoginZhaoCaiCatKfrtModel> model) {

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
        Observable<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().getApiService().getValue("VIDEOTAPE");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ZhaoCaiCatKfrtObserverManager<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>>() {
                    @Override
                    public void onSuccess(ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigZhaoCaiCatKfrtModel configZhaoCaiCatKfrtModel = model.getData();
                        if (configZhaoCaiCatKfrtModel != null) {
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveBool("NO_RECORD", !configZhaoCaiCatKfrtModel.getVideoTape().equals("0"));
                            if (ZhaoCaiCatKfrtSharePreferencesUtil.getBool("NO_RECORD")) {
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