package com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.R;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyapi.CaiJieTongYshVdjrytRetrofitManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.BaseCaiJieTongYshVdjrytFragment;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhybase.CaiJieTongYshVdjrytObserverManager;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytBaseModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.ConfigCaiJieTongYshVdjrytModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhymodel.CaiJieTongYshVdjrytMineItemModel;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.AppInfoActivityCaiJieTongYshVdjryt;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.CancellationActivityCaiJieTongYshVdjryt;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.LoginActivityCaiJieTongYshVdjryt;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.UserYsxyActivityCaiJieTongYshVdjryt;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyui.caijietongshrtnhyadapter.CaiJieTongYshVdjrytMineItemAdapter;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytRemindDialog;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.CaiJieTongYshVdjrytSharePreferencesUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.StaticCaiJieTongYshVdjrytUtil;
import com.zasashqwhdssdfert.msdjsdhfhsdnasdfwert.caijietongshrtnhyutil.ToastCaiJieTongYshVdjrytUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineCaiJieTongYshVdjrytFragment extends BaseCaiJieTongYshVdjrytFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private CaiJieTongYshVdjrytMineItemAdapter caiJieTongYshVdjrytMineItemAdapter, caiJieTongYshVdjrytMineItemAdapter1;
    private List<CaiJieTongYshVdjrytMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xvbnsrt, R.drawable.aegnghk, R.drawable.zxvcbndf,
            R.drawable.lguipf, R.drawable.fgdr, R.drawable.kfyuod};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private CaiJieTongYshVdjrytRemindDialog mCaiJieTongYshVdjrytRemindDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cai_jie_tong_drt_etfnh_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = CaiJieTongYshVdjrytSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            CaiJieTongYshVdjrytMineItemModel model = new CaiJieTongYshVdjrytMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        setMineData();
    }

    @Override
    public void initListener() {
        logoutBtn.setOnClickListener(v -> {
            mCaiJieTongYshVdjrytRemindDialog = new CaiJieTongYshVdjrytRemindDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mCaiJieTongYshVdjrytRemindDialog.setBtnClickListener(new CaiJieTongYshVdjrytRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
                    CaiJieTongYshVdjrytSharePreferencesUtil.saveString("phone", "");
                    StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), LoginActivityCaiJieTongYshVdjryt.class, null);
                    getActivity().finish();
                }
            });
            mCaiJieTongYshVdjrytRemindDialog.show();
            mCaiJieTongYshVdjrytRemindDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        caiJieTongYshVdjrytMineItemAdapter =  new CaiJieTongYshVdjrytMineItemAdapter(R.layout.adapter_cai_jie_tong_drt_etfnh_mine_list_layout, list);
        caiJieTongYshVdjrytMineItemAdapter.setHasStableIds(true);
        caiJieTongYshVdjrytMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", CaiJieTongYshVdjrytRetrofitManager.ZCXY);
                    StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), UserYsxyActivityCaiJieTongYshVdjryt.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", CaiJieTongYshVdjrytRetrofitManager.YSXY);
                    StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), UserYsxyActivityCaiJieTongYshVdjryt.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(caiJieTongYshVdjrytMineItemAdapter);
        caiJieTongYshVdjrytMineItemAdapter1 =  new CaiJieTongYshVdjrytMineItemAdapter(R.layout.adapter_cai_jie_tong_drt_etfnh_mine_list_layout, list1);
        caiJieTongYshVdjrytMineItemAdapter1.setHasStableIds(true);
        caiJieTongYshVdjrytMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), AppInfoActivityCaiJieTongYshVdjryt.class, null);
                    break;
                case 1:
                    mCaiJieTongYshVdjrytRemindDialog = new CaiJieTongYshVdjrytRemindDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mCaiJieTongYshVdjrytRemindDialog.setBtnClickListener(new CaiJieTongYshVdjrytRemindDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastCaiJieTongYshVdjrytUtil.showShort("开启成功");
                            mCaiJieTongYshVdjrytRemindDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastCaiJieTongYshVdjrytUtil.showShort("关闭成功");
                            mCaiJieTongYshVdjrytRemindDialog.dismiss();
                        }
                    });
                    mCaiJieTongYshVdjrytRemindDialog.show();
                    mCaiJieTongYshVdjrytRemindDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticCaiJieTongYshVdjrytUtil.startActivity(getActivity(), CancellationActivityCaiJieTongYshVdjryt.class, null);
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList1.setAdapter(caiJieTongYshVdjrytMineItemAdapter1);
    }

    private void getConfig() {
        Observable<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>> observable = CaiJieTongYshVdjrytRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new CaiJieTongYshVdjrytObserverManager<CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel>>() {
                    @Override
                    public void onSuccess(CaiJieTongYshVdjrytBaseModel<ConfigCaiJieTongYshVdjrytModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigCaiJieTongYshVdjrytModel configCaiJieTongYshVdjrytModel = model.getData();
                        if (configCaiJieTongYshVdjrytModel != null) {
                            CaiJieTongYshVdjrytSharePreferencesUtil.saveString("APP_MAIL", configCaiJieTongYshVdjrytModel.getAppMail());
                            mCaiJieTongYshVdjrytRemindDialog = new CaiJieTongYshVdjrytRemindDialog(getActivity(), "温馨提示", configCaiJieTongYshVdjrytModel.getAppMail(), false);
                            mCaiJieTongYshVdjrytRemindDialog.setBtnClickListener(new CaiJieTongYshVdjrytRemindDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configCaiJieTongYshVdjrytModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastCaiJieTongYshVdjrytUtil.showShort("复制成功");
                                    mCaiJieTongYshVdjrytRemindDialog.dismiss();
                                }
                            });
                            mCaiJieTongYshVdjrytRemindDialog.show();
                            mCaiJieTongYshVdjrytRemindDialog.setBtnStr("取消", "复制");
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
