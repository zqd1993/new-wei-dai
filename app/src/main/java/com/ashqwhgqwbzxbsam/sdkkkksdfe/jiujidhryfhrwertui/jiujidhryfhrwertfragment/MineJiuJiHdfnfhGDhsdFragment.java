package com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertfragment;

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

import com.ashqwhgqwbzxbsam.sdkkkksdfe.R;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertapi.RetrofitJiuJiHdfnfhGDhsdManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.BaseJiuJiHdfnfhGDhsdFragment;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertbase.JiuJiHdfnfhGDhsdObserverManager;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdBaseModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.ConfigJiuJiHdfnfhGDhsdModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertmodel.JiuJiHdfnfhGDhsdMineItemModel;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.AppInfoJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.CancellationJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.LoginJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.UserYsxyJiuJiHdfnfhGDhsdActivity;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertui.jiujidhryfhrwertadapter.MineItemJiuJiHdfnfhGDhsdAdapter;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.RemindJiuJiHdfnfhGDhsdDialog;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.ToastJiuJiHdfnfhGDhsdUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.JiuJiHdfnfhGDhsdSharePreferencesUtil;
import com.ashqwhgqwbzxbsam.sdkkkksdfe.jiujidhryfhrwertutil.StaticJiuJiHdfnfhGDhsdUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineJiuJiHdfnfhGDhsdFragment extends BaseJiuJiHdfnfhGDhsdFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn, zhuxiao_tv;

    private String mobileStr;
    private MineItemJiuJiHdfnfhGDhsdAdapter mineItemJiuJiHdfnfhGDhsdAdapter, mineItemJiuJiHdfnfhGDhsdAdapter1;
    private List<JiuJiHdfnfhGDhsdMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.rtgh, R.drawable.dfgjvbn, R.drawable.srtyhfgx,
            R.drawable.vbnsr, R.drawable.zdfhx};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐"};
    private Bundle bundle;
    private RemindJiuJiHdfnfhGDhsdDialog mRemindJiuJiHdfnfhGDhsdDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiu_ji_fdher_reytjyh_mine;
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
        mobileStr = JiuJiHdfnfhGDhsdSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            JiuJiHdfnfhGDhsdMineItemModel model = new JiuJiHdfnfhGDhsdMineItemModel();
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
            mRemindJiuJiHdfnfhGDhsdDialog = new RemindJiuJiHdfnfhGDhsdDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindJiuJiHdfnfhGDhsdDialog.setBtnClickListener(new RemindJiuJiHdfnfhGDhsdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                    JiuJiHdfnfhGDhsdSharePreferencesUtil.saveString("phone", "");
                    StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), LoginJiuJiHdfnfhGDhsdActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindJiuJiHdfnfhGDhsdDialog.show();
            mRemindJiuJiHdfnfhGDhsdDialog.setBtnStr("取消", "退出");
        });
        zhuxiao_tv.setOnClickListener(v -> {
            StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), CancellationJiuJiHdfnfhGDhsdActivity.class, null);
        });
    }

    private void setMineData(){
        mineItemJiuJiHdfnfhGDhsdAdapter =  new MineItemJiuJiHdfnfhGDhsdAdapter(R.layout.adapter_mine_list_layout_jiu_ji_fdher_reytjyh, list);
        mineItemJiuJiHdfnfhGDhsdAdapter.setHasStableIds(true);
        mineItemJiuJiHdfnfhGDhsdAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitJiuJiHdfnfhGDhsdManager.ZCXY);
                    StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), UserYsxyJiuJiHdfnfhGDhsdActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitJiuJiHdfnfhGDhsdManager.YSXY);
                    StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), UserYsxyJiuJiHdfnfhGDhsdActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemJiuJiHdfnfhGDhsdAdapter);
        mineItemJiuJiHdfnfhGDhsdAdapter1 =  new MineItemJiuJiHdfnfhGDhsdAdapter(R.layout.adapter_mine_list_layout_1_jiu_ji_fdher_reytjyh, list1);
        mineItemJiuJiHdfnfhGDhsdAdapter1.setHasStableIds(true);
        mineItemJiuJiHdfnfhGDhsdAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticJiuJiHdfnfhGDhsdUtil.startActivity(getActivity(), AppInfoJiuJiHdfnfhGDhsdActivity.class, null);
                    break;
                case 1:
                    mRemindJiuJiHdfnfhGDhsdDialog = new RemindJiuJiHdfnfhGDhsdDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindJiuJiHdfnfhGDhsdDialog.setBtnClickListener(new RemindJiuJiHdfnfhGDhsdDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastJiuJiHdfnfhGDhsdUtil.showShort("开启成功");
                            mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastJiuJiHdfnfhGDhsdUtil.showShort("关闭成功");
                            mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                        }
                    });
                    mRemindJiuJiHdfnfhGDhsdDialog.show();
                    mRemindJiuJiHdfnfhGDhsdDialog.setBtnStr("开启", "关闭");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemJiuJiHdfnfhGDhsdAdapter1);
    }

    private void getConfig() {
        Observable<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>> observable = RetrofitJiuJiHdfnfhGDhsdManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JiuJiHdfnfhGDhsdObserverManager<JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel>>() {
                    @Override
                    public void onSuccess(JiuJiHdfnfhGDhsdBaseModel<ConfigJiuJiHdfnfhGDhsdModel> model) {
                        if (model == null) {
                            return;
                        }
                        ConfigJiuJiHdfnfhGDhsdModel configJiuJiHdfnfhGDhsdModel = model.getData();
                        if (configJiuJiHdfnfhGDhsdModel != null) {
                            JiuJiHdfnfhGDhsdSharePreferencesUtil.saveString("APP_MAIL", configJiuJiHdfnfhGDhsdModel.getAppMail());
                            mRemindJiuJiHdfnfhGDhsdDialog = new RemindJiuJiHdfnfhGDhsdDialog(getActivity(), "温馨提示", configJiuJiHdfnfhGDhsdModel.getAppMail(), false);
                            mRemindJiuJiHdfnfhGDhsdDialog.setBtnClickListener(new RemindJiuJiHdfnfhGDhsdDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configJiuJiHdfnfhGDhsdModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastJiuJiHdfnfhGDhsdUtil.showShort("复制成功");
                                    mRemindJiuJiHdfnfhGDhsdDialog.dismiss();
                                }
                            });
                            mRemindJiuJiHdfnfhGDhsdDialog.show();
                            mRemindJiuJiHdfnfhGDhsdDialog.setBtnStr("取消", "复制");
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
