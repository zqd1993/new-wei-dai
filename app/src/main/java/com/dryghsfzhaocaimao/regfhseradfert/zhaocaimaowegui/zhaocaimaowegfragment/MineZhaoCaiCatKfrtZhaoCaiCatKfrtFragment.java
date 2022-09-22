package com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegfragment;

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

import com.dryghsfzhaocaimao.regfhseradfert.R;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegapi.RetrofitZhaoCaiCatKfrtManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.BaseZhaoCaiCatKfrtFragment;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegbase.ZhaoCaiCatKfrtObserverManager;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtBaseModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ConfigZhaoCaiCatKfrtModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegmodel.ZhaoCaiCatKfrtMineItemModel;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.ZhaoCaiCatKfrtAppInfoZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.ZhaoCaiCatKfrtCancellationZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegui.zhaocaimaowegadapter.MineItemZhaoCaiCatKfrtAdapter;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.RemindZhaoCaiCatKfrtDialog;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ToastZhaoCaiCatKfrtUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.ZhaoCaiCatKfrtSharePreferencesUtil;
import com.dryghsfzhaocaimao.regfhseradfert.zhaocaimaowegutil.StaticZhaoCaiCatKfrtUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineZhaoCaiCatKfrtZhaoCaiCatKfrtFragment extends BaseZhaoCaiCatKfrtFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn, zhuxiao_tv;

    private String mobileStr;
    private MineItemZhaoCaiCatKfrtAdapter mineItemZhaoCaiCatKfrtAdapter, mineItemZhaoCaiCatKfrtAdapter1;
    private List<ZhaoCaiCatKfrtMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.rtgh, R.drawable.dfgjvbn, R.drawable.srtyhfgx,
            R.drawable.vbnsr, R.drawable.zdfhx};
    private String[] tvRes = {"注册协议", "隐私协议", "投诉邮箱", "关于我们", "个性化推荐"};
    private Bundle bundle;
    private RemindZhaoCaiCatKfrtDialog mRemindZhaoCaiCatKfrtDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhao_cai_mao_dfget_mine;
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
        mobileStr = ZhaoCaiCatKfrtSharePreferencesUtil.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 5; i++) {
            ZhaoCaiCatKfrtMineItemModel model = new ZhaoCaiCatKfrtMineItemModel();
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
            mRemindZhaoCaiCatKfrtDialog = new RemindZhaoCaiCatKfrtDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindZhaoCaiCatKfrtDialog.setBtnClickListener(new RemindZhaoCaiCatKfrtDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindZhaoCaiCatKfrtDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindZhaoCaiCatKfrtDialog.dismiss();
                    ZhaoCaiCatKfrtSharePreferencesUtil.saveString("phone", "");
                    StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), LoginZhaoCaiCatKfrtZhaoCaiCatKfrtActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindZhaoCaiCatKfrtDialog.show();
            mRemindZhaoCaiCatKfrtDialog.setBtnStr("取消", "退出");
        });
        zhuxiao_tv.setOnClickListener(v -> {
            StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), ZhaoCaiCatKfrtCancellationZhaoCaiCatKfrtActivity.class, null);
        });
    }

    private void setMineData(){
        mineItemZhaoCaiCatKfrtAdapter =  new MineItemZhaoCaiCatKfrtAdapter(R.layout.adapter_mine_list_layout_zhao_cai_mao_dfget, list);
        mineItemZhaoCaiCatKfrtAdapter.setHasStableIds(true);
        mineItemZhaoCaiCatKfrtAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.ZCXY);
                    StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RetrofitZhaoCaiCatKfrtManager.YSXY);
                    StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), ZhaoCaiCatKfrtUserYsxyZhaoCaiCatKfrtActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(mineItemZhaoCaiCatKfrtAdapter);
        mineItemZhaoCaiCatKfrtAdapter1 =  new MineItemZhaoCaiCatKfrtAdapter(R.layout.adapter_mine_list_layout_1_zhao_cai_mao_dfget, list1);
        mineItemZhaoCaiCatKfrtAdapter1.setHasStableIds(true);
        mineItemZhaoCaiCatKfrtAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticZhaoCaiCatKfrtUtil.startActivity(getActivity(), ZhaoCaiCatKfrtAppInfoZhaoCaiCatKfrtActivity.class, null);
                    break;
                case 1:
                    mRemindZhaoCaiCatKfrtDialog = new RemindZhaoCaiCatKfrtDialog(getActivity(), "温馨提示", "关闭或开启推送", false);
                    mRemindZhaoCaiCatKfrtDialog.setBtnClickListener(new RemindZhaoCaiCatKfrtDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            ToastZhaoCaiCatKfrtUtil.showShort("开启成功");
                            mRemindZhaoCaiCatKfrtDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            ToastZhaoCaiCatKfrtUtil.showShort("关闭成功");
                            mRemindZhaoCaiCatKfrtDialog.dismiss();
                        }
                    });
                    mRemindZhaoCaiCatKfrtDialog.show();
                    mRemindZhaoCaiCatKfrtDialog.setBtnStr("开启", "关闭");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(mineItemZhaoCaiCatKfrtAdapter1);
    }

    private void getConfig() {
        Observable<ZhaoCaiCatKfrtBaseModel<ConfigZhaoCaiCatKfrtModel>> observable = RetrofitZhaoCaiCatKfrtManager.getRetrofitManager().getApiService().getConfig();

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
                            ZhaoCaiCatKfrtSharePreferencesUtil.saveString("APP_MAIL", configZhaoCaiCatKfrtModel.getAppMail());
                            mRemindZhaoCaiCatKfrtDialog = new RemindZhaoCaiCatKfrtDialog(getActivity(), "温馨提示", configZhaoCaiCatKfrtModel.getAppMail(), false);
                            mRemindZhaoCaiCatKfrtDialog.setBtnClickListener(new RemindZhaoCaiCatKfrtDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindZhaoCaiCatKfrtDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configZhaoCaiCatKfrtModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastZhaoCaiCatKfrtUtil.showShort("复制成功");
                                    mRemindZhaoCaiCatKfrtDialog.dismiss();
                                }
                            });
                            mRemindZhaoCaiCatKfrtDialog.show();
                            mRemindZhaoCaiCatKfrtDialog.setBtnStr("取消", "复制");
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
