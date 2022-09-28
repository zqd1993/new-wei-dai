package com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ueruzdfgnh.urngfhag.R;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernapi.RetrofitWuYFenQiHuysdjDshryManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.BaseWuYFenQiHuysdjDshryFragment;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernbase.WuYFenQiHuysdjDshryObserverManager;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryBaseModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.ConfigWuYFenQiHuysdjDshryModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernmodel.WuYFenQiHuysdjDshryMineItemModel;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.AppInfoWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.CancellationWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.LoginWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.UserYsxyWuYFenQiHuysdjDshryActivity;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernui.wuyfenqjkuahdyernadapter.MineItemWuYFenQiHuysdjDshryAdapter;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.RemindWuYFenQiHuysdjDshryDialog;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.ToastWuYFenQiHuysdjDshryUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.WuYFenQiHuysdjDshrySharePreferencesUtil;
import com.ueruzdfgnh.urngfhag.wuyfenqjkuahdyernutil.StaticWuYFenQiHuysdjDshryUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineWuYFenQiHuysdjDshryFragment extends BaseWuYFenQiHuysdjDshryFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn, zhuxiao_tv;

    private String mobileStr;
    private MineItemWuYFenQiHuysdjDshryAdapter mineItemWuYFenQiHuysdjDshryAdapter, mineItemWuYFenQiHuysdjDshryAdapter1;
    private List<WuYFenQiHuysdjDshryMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.rtgh, R.drawable.dfgjvbn, R.drawable.srtyhfgx,
            R.drawable.vbnsr, R.drawable.zdfhx};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐"};
    private Bundle bundle;
    private RemindWuYFenQiHuysdjDshryDialog mRemindWuYFenQiHuysdjDshryDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wu_yfen_qijai_dfjrt_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        zhuxiao_tv = rootView.findViewById(R.id.zhuxiao_tv);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = WuYFenQiHuysdjDshrySharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            WuYFenQiHuysdjDshryMineItemModel model = new WuYFenQiHuysdjDshryMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3) {
                list.add(model);
            } else {
                list1.add(model);
            };
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindWuYFenQiHuysdjDshryDialog = new RemindWuYFenQiHuysdjDshryDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindWuYFenQiHuysdjDshryDialog.setBtnClickListener(new RemindWuYFenQiHuysdjDshryDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                    WuYFenQiHuysdjDshrySharePreferencesUtil.saveString("phone", "");
                    StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), LoginWuYFenQiHuysdjDshryActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindWuYFenQiHuysdjDshryDialog.show();
            mRemindWuYFenQiHuysdjDshryDialog.setBtnStr("取消", "退出");
        });
        zhuxiao_tv.setOnClickListener(v -> {
            StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), CancellationWuYFenQiHuysdjDshryActivity.class, null);
        });
    }

    private void setMineData(){
        mineItemWuYFenQiHuysdjDshryAdapter =  new MineItemWuYFenQiHuysdjDshryAdapter(R.layout.adapter_mine_list_layout_wu_yfen_qijai_dfjrt, list);
        mineItemWuYFenQiHuysdjDshryAdapter.setHasStableIds(true);
        mineItemWuYFenQiHuysdjDshryAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.ZCXY);
                    StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitWuYFenQiHuysdjDshryManager.YSXY);
                    StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), UserYsxyWuYFenQiHuysdjDshryActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemWuYFenQiHuysdjDshryAdapter);
        mineItemWuYFenQiHuysdjDshryAdapter1 =  new MineItemWuYFenQiHuysdjDshryAdapter(R.layout.adapter_mine_list_layout_1_wu_yfen_qijai_dfjrt, list1);
        mineItemWuYFenQiHuysdjDshryAdapter1.setHasStableIds(true);
        mineItemWuYFenQiHuysdjDshryAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticWuYFenQiHuysdjDshryUtil.startActivity(getActivity(), AppInfoWuYFenQiHuysdjDshryActivity.class, null);
                    break;
                case 1:
                    mRemindWuYFenQiHuysdjDshryDialog = new RemindWuYFenQiHuysdjDshryDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindWuYFenQiHuysdjDshryDialog.setBtnClickListener(new RemindWuYFenQiHuysdjDshryDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastWuYFenQiHuysdjDshryUtil.showShort("开启成功");
                            mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastWuYFenQiHuysdjDshryUtil.showShort("关闭成功");
                            mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                        }
                    });
                    mRemindWuYFenQiHuysdjDshryDialog.show();
                    mRemindWuYFenQiHuysdjDshryDialog.setBtnStr("开启", "关闭");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemWuYFenQiHuysdjDshryAdapter1);
    }

    private void getConfig() {
        Observable<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>> observable = RetrofitWuYFenQiHuysdjDshryManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new WuYFenQiHuysdjDshryObserverManager<WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel>>() {
                    @Override
                    public void onSuccess(WuYFenQiHuysdjDshryBaseModel<ConfigWuYFenQiHuysdjDshryModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigWuYFenQiHuysdjDshryModel configWuYFenQiHuysdjDshryModel = model.getData();
                        if (configWuYFenQiHuysdjDshryModel != null) {
                            WuYFenQiHuysdjDshrySharePreferencesUtil.saveString("APP_MAIL", configWuYFenQiHuysdjDshryModel.getAppMail());
                            mRemindWuYFenQiHuysdjDshryDialog = new RemindWuYFenQiHuysdjDshryDialog(getActivity(), "温馨提示", configWuYFenQiHuysdjDshryModel.getAppMail(), false);
                            mRemindWuYFenQiHuysdjDshryDialog.setBtnClickListener(new RemindWuYFenQiHuysdjDshryDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configWuYFenQiHuysdjDshryModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastWuYFenQiHuysdjDshryUtil.showShort("复制成功");
                                    mRemindWuYFenQiHuysdjDshryDialog.dismiss();
                                }
                            });
                            mRemindWuYFenQiHuysdjDshryDialog.show();
                            mRemindWuYFenQiHuysdjDshryDialog.setBtnStr("取消", "复制");
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
