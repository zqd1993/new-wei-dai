package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdtefragment;

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

import com.sdldsjqwbaszbdskdf.dfpddfgert.R;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteapi.RongjieSfFgdfRetrofitManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.BaseRongjieSfFgdfFragment;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtebase.RongjieSfFgdfObserverManager;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfBaseModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfConfigModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdtemodel.RongjieSfFgdfMineItemModel;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.WeiFenQiadsfSsdAppInfoActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.WeiFenQiadsfSsdCancellationActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.WeiFenQiadsfSsdLoginActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.WeiFenQiadsfSsdUserYsxyActivity;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteui.weifenqjtsdteadapter.WeiFenQiadsfSsdMineItemAdapter;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.RemindWeiFenQiadsfSsdDialog;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.SharePreferencesUtilWeiFenQiadsfSsd;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.StaticWeiFenQiadsfSsdUtil;
import com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteutil.ToastWeiFenQiadsfSsdUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MineWeiFenQiadsfSsdFragment extends BaseRongjieSfFgdfFragment {

    private TextView customerMobileTv;
    private RecyclerView mineList, mineList1;
    private View logoutBtn;

    private String mobileStr;
    private WeiFenQiadsfSsdMineItemAdapter weiFenQiadsfSsdMineItemAdapter, weiFenQiadsfSsdMineItemAdapter1;
    private List<RongjieSfFgdfMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery,
            R.drawable.cvd, R.drawable.erfh, R.drawable.zcvzbery, R.drawable.zcvzbery};
    private String[] tvRes = {"注册协议", "隐私协议", "联系客服", "关于我们", "注销账户", "退出登录"};
    private Bundle bundle;
    private RemindWeiFenQiadsfSsdDialog mRemindWeiFenQiadsfSsdDialog;
    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wei_fen_qi_dfger_vjkrt_mine;
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
        mobileStr = SharePreferencesUtilWeiFenQiadsfSsd.getString("phone");
        customerMobileTv.setText(mobileStr);
        for (int i = 0; i < 6; i++) {
            RongjieSfFgdfMineItemModel model = new RongjieSfFgdfMineItemModel();
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
            mRemindWeiFenQiadsfSsdDialog = new RemindWeiFenQiadsfSsdDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
            mRemindWeiFenQiadsfSsdDialog.setBtnClickListener(new RemindWeiFenQiadsfSsdDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    mRemindWeiFenQiadsfSsdDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    mRemindWeiFenQiadsfSsdDialog.dismiss();
                    SharePreferencesUtilWeiFenQiadsfSsd.saveString("phone", "");
                    StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdLoginActivity.class, null);
                    getActivity().finish();
                }
            });
            mRemindWeiFenQiadsfSsdDialog.show();
            mRemindWeiFenQiadsfSsdDialog.setBtnStr("取消", "退出");
        });
    }

    private void setMineData(){
        weiFenQiadsfSsdMineItemAdapter =  new WeiFenQiadsfSsdMineItemAdapter(R.layout.adapter_mine_list_layout_wei_fen_qi_dfger_vjkrt, list);
        weiFenQiadsfSsdMineItemAdapter.setHasStableIds(true);
        weiFenQiadsfSsdMineItemAdapter.setItemClickListener(position -> {
            switch (position){
                case 0:
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", RongjieSfFgdfRetrofitManager.ZCXY);
                    StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", RongjieSfFgdfRetrofitManager.YSXY);
                    StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdUserYsxyActivity.class, bundle);
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        mineList.setHasFixedSize(true);
        mineList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mineList.setAdapter(weiFenQiadsfSsdMineItemAdapter);
        weiFenQiadsfSsdMineItemAdapter1 =  new WeiFenQiadsfSsdMineItemAdapter(R.layout.adapter_wei_fen_qi_dfger_vjkrt_mine_list_layout_hor, list1);
        weiFenQiadsfSsdMineItemAdapter1.setHasStableIds(true);
        weiFenQiadsfSsdMineItemAdapter1.setItemClickListener(position -> {
            switch (position){
                case 0:
                    StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdAppInfoActivity.class, null);
                    break;
                case 1:
                    StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdCancellationActivity.class, null);
                    break;
                case 2:
                    mRemindWeiFenQiadsfSsdDialog = new RemindWeiFenQiadsfSsdDialog(getActivity(), "温馨提示", "确定退出当前登录", false);
                    mRemindWeiFenQiadsfSsdDialog.setBtnClickListener(new RemindWeiFenQiadsfSsdDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            mRemindWeiFenQiadsfSsdDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            mRemindWeiFenQiadsfSsdDialog.dismiss();
                            SharePreferencesUtilWeiFenQiadsfSsd.saveString("phone", "");
                            StaticWeiFenQiadsfSsdUtil.startActivity(getActivity(), WeiFenQiadsfSsdLoginActivity.class, null);
                            getActivity().finish();
                        }
                    });
                    mRemindWeiFenQiadsfSsdDialog.show();
                    mRemindWeiFenQiadsfSsdDialog.setBtnStr("取消", "退出");
                    break;
            }
        });
        mineList1.setHasFixedSize(true);
        mineList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineList1.setAdapter(weiFenQiadsfSsdMineItemAdapter1);
    }

    private void getConfig() {
        Observable<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>> observable = RongjieSfFgdfRetrofitManager.getRetrofitManager().getApiService().getConfig();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new RongjieSfFgdfObserverManager<RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel>>() {
                    @Override
                    public void onSuccess(RongjieSfFgdfBaseModel<RongjieSfFgdfConfigModel> model) {
                        if (model == null) {
                            return;
                        }
                        RongjieSfFgdfConfigModel rongjieSfFgdfConfigModel = model.getData();
                        if (rongjieSfFgdfConfigModel != null) {
                            SharePreferencesUtilWeiFenQiadsfSsd.saveString("APP_MAIL", rongjieSfFgdfConfigModel.getAppMail());
                            mRemindWeiFenQiadsfSsdDialog = new RemindWeiFenQiadsfSsdDialog(getActivity(), "温馨提示", rongjieSfFgdfConfigModel.getAppMail(), false);
                            mRemindWeiFenQiadsfSsdDialog.setBtnClickListener(new RemindWeiFenQiadsfSsdDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    mRemindWeiFenQiadsfSsdDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, rongjieSfFgdfConfigModel.getAppMail());
                                    clipboard.setPrimaryClip(clipData);
                                    ToastWeiFenQiadsfSsdUtil.showShort("复制成功");
                                    mRemindWeiFenQiadsfSsdDialog.dismiss();
                                }
                            });
                            mRemindWeiFenQiadsfSsdDialog.show();
                            mRemindWeiFenQiadsfSsdDialog.setBtnStr("取消", "复制");
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
