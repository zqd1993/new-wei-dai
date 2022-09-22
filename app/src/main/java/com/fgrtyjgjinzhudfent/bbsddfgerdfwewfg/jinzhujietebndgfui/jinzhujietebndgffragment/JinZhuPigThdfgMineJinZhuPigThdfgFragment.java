package com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgffragment;

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

import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.R;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfapi.JinZhuPigThdfgRetrofitManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.BaseJinZhuPigThdfgFragment;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfbase.JinZhuPigThdfgObserverManager;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.BaseJinZhuPigThdfgModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgConfigModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfmodel.JinZhuPigThdfgMineItemModel;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.AppInfoJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.CancellationJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.LoginJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.UserYsxyJinZhuPigThdfgActivity;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfui.jinzhujietebndgfadapter.JinZhuPigThdfgMineItemAdapter;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.RemindJinZhuPigThdfgDialog;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.JinZhuPigThdfgSharePreferencesUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.StaticJinZhuPigThdfgUtil;
import com.fgrtyjgjinzhudfent.bbsddfgerdfwewfg.jinzhujietebndgfutil.ToastJinZhuPigThdfgUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JinZhuPigThdfgMineJinZhuPigThdfgFragment extends BaseJinZhuPigThdfgFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private JinZhuPigThdfgMineItemAdapter jinZhuPigThdfgMineItemAdapter, jinZhuPigThdfgMineItemAdapter1;
    private List<JinZhuPigThdfgMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.vxcbnsrt, R.drawable.rftgn, R.drawable.lpjfg,
            R.drawable.kxfbn, R.drawable.erygj, R.drawable.lphk};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐", "注销账户"};
    private Bundle bundle;
    private RemindJinZhuPigThdfgDialog mRemindJinZhuPigThdfgDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jin_zhu_asf_pig_mine;
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
        mobileStr = JinZhuPigThdfgSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            JinZhuPigThdfgMineItemModel model = new JinZhuPigThdfgMineItemModel();
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
            mRemindJinZhuPigThdfgDialog = new RemindJinZhuPigThdfgDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindJinZhuPigThdfgDialog.setBtnClickListener(new RemindJinZhuPigThdfgDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindJinZhuPigThdfgDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindJinZhuPigThdfgDialog.dismiss();
                    JinZhuPigThdfgSharePreferencesUtil.saveString("phone", "");
                    StaticJinZhuPigThdfgUtil.startActivity(getActivity(), LoginJinZhuPigThdfgActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindJinZhuPigThdfgDialog.show();
            mRemindJinZhuPigThdfgDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        jinZhuPigThdfgMineItemAdapter =  new JinZhuPigThdfgMineItemAdapter(R.layout.adapter_mine_list_layout_jin_zhu_asf_pig, list);
        jinZhuPigThdfgMineItemAdapter.setHasStableIds(true);
        jinZhuPigThdfgMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", JinZhuPigThdfgRetrofitManager.ZCXY);
                    StaticJinZhuPigThdfgUtil.startActivity(getActivity(), UserYsxyJinZhuPigThdfgActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", JinZhuPigThdfgRetrofitManager.YSXY);
                    StaticJinZhuPigThdfgUtil.startActivity(getActivity(), UserYsxyJinZhuPigThdfgActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(jinZhuPigThdfgMineItemAdapter);
        jinZhuPigThdfgMineItemAdapter1 =  new JinZhuPigThdfgMineItemAdapter(R.layout.adapter_mine_list_layout_1_jin_zhu_asf_pig, list1);
        jinZhuPigThdfgMineItemAdapter1.setHasStableIds(true);
        jinZhuPigThdfgMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticJinZhuPigThdfgUtil.startActivity(getActivity(), AppInfoJinZhuPigThdfgActivity.class, null);
                    break;
                case 1:
                    mRemindJinZhuPigThdfgDialog = new RemindJinZhuPigThdfgDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindJinZhuPigThdfgDialog.setBtnClickListener(new RemindJinZhuPigThdfgDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastJinZhuPigThdfgUtil.showShort("开启成功");
                            mRemindJinZhuPigThdfgDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastJinZhuPigThdfgUtil.showShort("关闭成功");
                            mRemindJinZhuPigThdfgDialog.dismiss();
                        }
                    });
                    mRemindJinZhuPigThdfgDialog.show();
                    mRemindJinZhuPigThdfgDialog.setBtnStr("开启", "关闭");
                    break;
                case 2:
                    StaticJinZhuPigThdfgUtil.startActivity(getActivity(), CancellationJinZhuPigThdfgActivity.class, null);
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(jinZhuPigThdfgMineItemAdapter1);
    }

    private void getConfig() {
        Observable<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>> observable = JinZhuPigThdfgRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new JinZhuPigThdfgObserverManager<BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel>>() {
                    @Override
                    public void onSuccess(BaseJinZhuPigThdfgModel<JinZhuPigThdfgConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        JinZhuPigThdfgConfigModel jinZhuPigThdfgConfigModel = model.getData();
                        if (jinZhuPigThdfgConfigModel != null) {
                            JinZhuPigThdfgSharePreferencesUtil.saveString("APP_MAIL", jinZhuPigThdfgConfigModel.getAppMail());
                            mRemindJinZhuPigThdfgDialog = new RemindJinZhuPigThdfgDialog(getActivity(), "温馨提示", jinZhuPigThdfgConfigModel.getAppMail(), false);
                            mRemindJinZhuPigThdfgDialog.setBtnClickListener(new RemindJinZhuPigThdfgDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindJinZhuPigThdfgDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, jinZhuPigThdfgConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastJinZhuPigThdfgUtil.showShort("复制成功");
                                    mRemindJinZhuPigThdfgDialog.dismiss();
                                }
                            });
                            mRemindJinZhuPigThdfgDialog.show();
                            mRemindJinZhuPigThdfgDialog.setBtnStr("取消", "复制");
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
