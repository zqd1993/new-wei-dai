package com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyfragment;

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

import com.dfgjtruymsdf.ytjermgfjjgut.R;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyapi.JzjqianHdhrtYhdRetrofitManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.BaseJzjqianHdhrtYhdFragment;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtybase.JzjqianHdhrtYhdObserverManager;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.BaseJzjqianHdhrtYhdModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdConfigModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtymodel.JzjqianHdhrtYhdMineItemModel;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.AppInfoJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.CancellationJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.LoginJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.UserYsxyJzjqianHdhrtYhdActivity;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyui.jzjqianfgheyrtyadapter.JzjqianHdhrtYhdMineItemAdapter;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.RemindJzjqianHdhrtYhdDialog;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.JzjqianHdhrtYhdSharePreferencesUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.StaticJzjqianHdhrtYhdUtil;
import com.dfgjtruymsdf.ytjermgfjjgut.jzjqianfgheyrtyutil.ToastJzjqianHdhrtYhdUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineJzjqianHdhrtYhdFragment extends BaseJzjqianHdhrtYhdFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private JzjqianHdhrtYhdMineItemAdapter jzjqianHdhrtYhdMineItemAdapter, jzjqianHdhrtYhdMineItemAdapter1;
    private List<JzjqianHdhrtYhdMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.vxcbnsrt, R.drawable.rftgn, R.drawable.lpjfg,
            R.drawable.kxfbn, R.drawable.erygj, R.drawable.lphk};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindJzjqianHdhrtYhdDialog mRemindJzjqianHdhrtYhdDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jzjqian_sdhr_utryn_mine;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        customerMobileTv = rootView.findViewById(R.id.customer_mobile_tv);
        mineList = rootView.findViewById(R.id.mine_list);
        logoutBtn = rootView.findViewById(R.id.logout_btn);
        mineList1 = rootView.findViewById(R.id.mine_list_1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mobileStr = JzjqianHdhrtYhdSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            JzjqianHdhrtYhdMineItemModel model = new JzjqianHdhrtYhdMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTitle(tvRes[i]);
            if (i < 3){
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
            mRemindJzjqianHdhrtYhdDialog = new RemindJzjqianHdhrtYhdDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindJzjqianHdhrtYhdDialog.setBtnClickListener(new RemindJzjqianHdhrtYhdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJzjqianHdhrtYhdDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindJzjqianHdhrtYhdDialog.dismiss();
                    JzjqianHdhrtYhdSharePreferencesUtil.saveString("phone", "");
                    StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), LoginJzjqianHdhrtYhdActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindJzjqianHdhrtYhdDialog.show();
            mRemindJzjqianHdhrtYhdDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        jzjqianHdhrtYhdMineItemAdapter =  new JzjqianHdhrtYhdMineItemAdapter(R.layout.adapter_mine_list_layout_jzjqian_sdhr_utryn, list);
        jzjqianHdhrtYhdMineItemAdapter.setHasStableIds(true);
        jzjqianHdhrtYhdMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.ZCXY);
                    StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", JzjqianHdhrtYhdRetrofitManager.YSXY);
                    StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), UserYsxyJzjqianHdhrtYhdActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(jzjqianHdhrtYhdMineItemAdapter);
        jzjqianHdhrtYhdMineItemAdapter1 =  new JzjqianHdhrtYhdMineItemAdapter(R.layout.adapter_mine_list_layout_1_jzjqian_sdhr_utryn, list1);
        jzjqianHdhrtYhdMineItemAdapter1.setHasStableIds(true);
        jzjqianHdhrtYhdMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), AppInfoJzjqianHdhrtYhdActivity.class, null);
                    break;
                case 1:
                    mRemindJzjqianHdhrtYhdDialog = new RemindJzjqianHdhrtYhdDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindJzjqianHdhrtYhdDialog.setBtnClickListener(new RemindJzjqianHdhrtYhdDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastJzjqianHdhrtYhdUtil.showShort("开启成功");
                            mRemindJzjqianHdhrtYhdDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastJzjqianHdhrtYhdUtil.showShort("关闭成功");
                            mRemindJzjqianHdhrtYhdDialog.dismiss();
                        }
                    });
                    mRemindJzjqianHdhrtYhdDialog.show();
                    mRemindJzjqianHdhrtYhdDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticJzjqianHdhrtYhdUtil.startActivity(getActivity(), CancellationJzjqianHdhrtYhdActivity.class, null);
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(jzjqianHdhrtYhdMineItemAdapter1);
    }

    private void getConfig() {
        Observable<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>> observable = JzjqianHdhrtYhdRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JzjqianHdhrtYhdObserverManager<BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJzjqianHdhrtYhdModel<JzjqianHdhrtYhdConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JzjqianHdhrtYhdConfigModel jzjqianHdhrtYhdConfigModel = model.getData();
                        if (jzjqianHdhrtYhdConfigModel != null) {
                            JzjqianHdhrtYhdSharePreferencesUtil.saveString("APP_MAIL", jzjqianHdhrtYhdConfigModel.getAppMail());
                            mRemindJzjqianHdhrtYhdDialog = new RemindJzjqianHdhrtYhdDialog(getActivity(), "温馨提示", jzjqianHdhrtYhdConfigModel.getAppMail(), false);
                            mRemindJzjqianHdhrtYhdDialog.setBtnClickListener(new RemindJzjqianHdhrtYhdDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindJzjqianHdhrtYhdDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, jzjqianHdhrtYhdConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastJzjqianHdhrtYhdUtil.showShort("复制成功");
                                    mRemindJzjqianHdhrtYhdDialog.dismiss();
                                }
                            });
                            mRemindJzjqianHdhrtYhdDialog.show();
                            mRemindJzjqianHdhrtYhdDialog.setBtnStr("取消", "复制");
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
