package com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.ruyidaivhernrffragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruyidaivhernrf.etdfharefg.R;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfapi.RuYiDaiKidunRetrofitManager;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfbase.BaseRuYiDaiKidunFragment;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfbase.RuYiDaiKidunObserverManager;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.BaseRuYiDaiKidunModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.ConfigRuYiDaiKidunModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfmodel.RuYiDaiKidunMineItemModel;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.AppInfoRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.CancellationRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.LoginRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.UserYsxyRuYiDaiKidunActivity;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfui.ruyidaivhernrfadapter.MineItemRuYiDaiKidunAdapter;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RemindRuYiDaiKidunDialog;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.RuYiDaiKidunSharePreferencesUtil;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.StaticRuYiDaiKidunUtil;
import com.ruyidaivhernrf.etdfharefg.ruyidaivhernrfutil.ToastRuYiDaiKidunUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineRuYiDaiKidunFragment extends BaseRuYiDaiKidunFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList;
    private View logoutBtn;

    private String mobileStr;
    private MineItemRuYiDaiKidunAdapter mineItemRuYiDaiKidunAdapter;
    private List<RuYiDaiKidunMineItemModel> list;
    private int[] imgRes = {R.drawable.isrtygzd, R.drawable.ikjhzdf, R.drawable.zdhj,
            R.drawable.zxcvbnae, R.drawable.aweyhfgj, R.drawable.zn};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindRuYiDaiKidunDialog mRemindRuYiDaiKidunDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ru_yi_dai_dfu_eng_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        mobileStr = RuYiDaiKidunSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            RuYiDaiKidunMineItemModel model = new RuYiDaiKidunMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            list.add(model);
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mRemindRuYiDaiKidunDialog = new RemindRuYiDaiKidunDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindRuYiDaiKidunDialog.setBtnClickListener(new RemindRuYiDaiKidunDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindRuYiDaiKidunDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindRuYiDaiKidunDialog.dismiss();
                    RuYiDaiKidunSharePreferencesUtil.saveString("phone", "");
                    StaticRuYiDaiKidunUtil.startActivity(getActivity(), LoginRuYiDaiKidunActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindRuYiDaiKidunDialog.show();
            mRemindRuYiDaiKidunDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        mineItemRuYiDaiKidunAdapter =  new MineItemRuYiDaiKidunAdapter(R.layout.adapter_mine_list_layout_ru_yi_dai_dfu_eng, list);
        mineItemRuYiDaiKidunAdapter.setHasStableIds(true);
        mineItemRuYiDaiKidunAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RuYiDaiKidunRetrofitManager.ZCXY);
                    StaticRuYiDaiKidunUtil.startActivity(getActivity(), UserYsxyRuYiDaiKidunActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RuYiDaiKidunRetrofitManager.YSXY);
                    StaticRuYiDaiKidunUtil.startActivity(getActivity(), UserYsxyRuYiDaiKidunActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    StaticRuYiDaiKidunUtil.startActivity(getActivity(), AppInfoRuYiDaiKidunActivity.class, null);
                    break;
                case 4:
                    mRemindRuYiDaiKidunDialog = new RemindRuYiDaiKidunDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindRuYiDaiKidunDialog.setBtnClickListener(new RemindRuYiDaiKidunDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastRuYiDaiKidunUtil.showShort("开启成功");
                            mRemindRuYiDaiKidunDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastRuYiDaiKidunUtil.showShort("关闭成功");
                            mRemindRuYiDaiKidunDialog.dismiss();
                        }
                    });
                    mRemindRuYiDaiKidunDialog.show();
                    mRemindRuYiDaiKidunDialog.setBtnStr("开启", "关闭");
                    break;
                case 5:
                    StaticRuYiDaiKidunUtil.startActivity(getActivity(), CancellationRuYiDaiKidunActivity.class, null);
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemRuYiDaiKidunAdapter);
    }

    private void getConfig() {
        Observable<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>> observable = RuYiDaiKidunRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RuYiDaiKidunObserverManager<BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel>>() {
                    @Override
                    public void onSuccess(BaseRuYiDaiKidunModel<ConfigRuYiDaiKidunModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigRuYiDaiKidunModel configRuYiDaiKidunModel = model.getData();
                        if (configRuYiDaiKidunModel != null) {
                            RuYiDaiKidunSharePreferencesUtil.saveString("APP_MAIL", configRuYiDaiKidunModel.getAppMail());
                            mRemindRuYiDaiKidunDialog = new RemindRuYiDaiKidunDialog(getActivity(), "温馨提示", configRuYiDaiKidunModel.getAppMail(), false);
                            mRemindRuYiDaiKidunDialog.setBtnClickListener(new RemindRuYiDaiKidunDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindRuYiDaiKidunDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configRuYiDaiKidunModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastRuYiDaiKidunUtil.showShort("复制成功");
                                    mRemindRuYiDaiKidunDialog.dismiss();
                                }
                            });
                            mRemindRuYiDaiKidunDialog.show();
                            mRemindRuYiDaiKidunDialog.setBtnStr("取消", "复制");
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
