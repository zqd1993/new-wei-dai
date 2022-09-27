package com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.rydqhfnerhtrfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtgjfjgwuett.rugjjdfgurj.R;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrapi.RYDQHdhtTsdhfrRetrofitManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.BaseRYDQHdhtTsdhfrFragment;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrbase.RYDQHdhtTsdhfrObserverManager;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.BaseRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.ConfigRYDQHdhtTsdhfrModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrmodel.RYDQHdhtTsdhfrMineItemModel;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.AppInfoRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.CancellationRRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.LoginRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.UserYsxyRYDQHdhtTsdhfrActivity;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrui.rydqhfnerhtradapter.MineItemRYDQHdhtTsdhfrAdapter;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RemindRYDQHdhtTsdhfrDialog;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.RYDQHdhtTsdhfrSharePreferencesUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.StaticRYDQHdhtTsdhfrUtil;
import com.rtgjfjgwuett.rugjjdfgurj.rydqhfnerhtrutil.ToastRYDQHdhtTsdhfrUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineRYDQHdhtTsdhfrFragment extends BaseRYDQHdhtTsdhfrFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MineItemRYDQHdhtTsdhfrAdapter mineItemRYDQHdhtTsdhfrAdapter;
    private List<RYDQHdhtTsdhfrMineItemModel> list;
    private int[] imgRes = {R.drawable.isrtygzd, R.drawable.ikjhzdf, R.drawable.zdhj,
            R.drawable.zxcvbnae, R.drawable.aweyhfgj, R.drawable.zn};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindRYDQHdhtTsdhfrDialog mRemindRYDQHdhtTsdhfrDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rydqh_fdhr_yrtehy_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = RYDQHdhtTsdhfrSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            RYDQHdhtTsdhfrMineItemModel model = new RYDQHdhtTsdhfrMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindRYDQHdhtTsdhfrDialog = new RemindRYDQHdhtTsdhfrDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindRYDQHdhtTsdhfrDialog.setBtnClickListener(new RemindRYDQHdhtTsdhfrDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
                    RYDQHdhtTsdhfrSharePreferencesUtil.saveString("phone", "");
                    StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), LoginRYDQHdhtTsdhfrActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindRYDQHdhtTsdhfrDialog.show();
            mRemindRYDQHdhtTsdhfrDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineItemRYDQHdhtTsdhfrAdapter =  new MineItemRYDQHdhtTsdhfrAdapter(R.layout.adapter_mine_list_layout_rydqh_fdhr_yrtehy, list);
        mineItemRYDQHdhtTsdhfrAdapter.setHasStableIds(true);
        mineItemRYDQHdhtTsdhfrAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.ZCXY);
                    StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RYDQHdhtTsdhfrRetrofitManager.YSXY);
                    StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), UserYsxyRYDQHdhtTsdhfrActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), AppInfoRYDQHdhtTsdhfrActivity.class, null);
                    break;
                case 4:
                    mRemindRYDQHdhtTsdhfrDialog = new RemindRYDQHdhtTsdhfrDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindRYDQHdhtTsdhfrDialog.setBtnClickListener(new RemindRYDQHdhtTsdhfrDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastRYDQHdhtTsdhfrUtil.showShort("开启成功");
                            mRemindRYDQHdhtTsdhfrDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastRYDQHdhtTsdhfrUtil.showShort("关闭成功");
                            mRemindRYDQHdhtTsdhfrDialog.dismiss();
                        }
                    });
                    mRemindRYDQHdhtTsdhfrDialog.show();
                    mRemindRYDQHdhtTsdhfrDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticRYDQHdhtTsdhfrUtil.startActivity(getActivity(), CancellationRRYDQHdhtTsdhfrActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemRYDQHdhtTsdhfrAdapter);
    }

    private void getConfig() {
        Observable<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>> observable = RYDQHdhtTsdhfrRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RYDQHdhtTsdhfrObserverManager<BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel>>() {
                    @Override
                    public void onSuccess(BaseRYDQHdhtTsdhfrModel<ConfigRYDQHdhtTsdhfrModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigRYDQHdhtTsdhfrModel configRYDQHdhtTsdhfrModel = model.getData();
                        if (configRYDQHdhtTsdhfrModel != null) {
                            RYDQHdhtTsdhfrSharePreferencesUtil.saveString("APP_MAIL", configRYDQHdhtTsdhfrModel.getAppMail());
                            mRemindRYDQHdhtTsdhfrDialog = new RemindRYDQHdhtTsdhfrDialog(getActivity(), "温馨提示", configRYDQHdhtTsdhfrModel.getAppMail(), false);
                            mRemindRYDQHdhtTsdhfrDialog.setBtnClickListener(new RemindRYDQHdhtTsdhfrDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configRYDQHdhtTsdhfrModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastRYDQHdhtTsdhfrUtil.showShort("复制成功");
                                    mRemindRYDQHdhtTsdhfrDialog.dismiss();
                                }
                            });
                            mRemindRYDQHdhtTsdhfrDialog.show();
                            mRemindRYDQHdhtTsdhfrDialog.setBtnStr("取消", "复制");
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
