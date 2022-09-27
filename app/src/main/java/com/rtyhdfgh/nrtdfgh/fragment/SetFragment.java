package com.rtyhdfgh.nrtdfgh.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtyhdfgh.nrtdfgh.R;
import com.rtyhdfgh.nrtdfgh.activity.GuanYuUsActivity;
import com.rtyhdfgh.nrtdfgh.activity.LoginActivity;
import com.rtyhdfgh.nrtdfgh.activity.UserAgreementActivity;
import com.rtyhdfgh.nrtdfgh.activity.ZhuXiaoZhangHaoActivity;
import com.rtyhdfgh.nrtdfgh.adapter.SetItemAdapter;
import com.rtyhdfgh.nrtdfgh.entity.BaseEntity;
import com.rtyhdfgh.nrtdfgh.entity.ConfigEntity;
import com.rtyhdfgh.nrtdfgh.entity.PersonalCenterEntity;
import com.rtyhdfgh.nrtdfgh.http.MainApi;
import com.rtyhdfgh.nrtdfgh.util.CommonUtil;
import com.rtyhdfgh.nrtdfgh.util.GeneralDialog;
import com.rtyhdfgh.nrtdfgh.util.MyPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SetFragment extends RxFragment {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.set_rcy)
    RecyclerView setRcv;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private GeneralDialog generalDialog;
    private String mailStr = "", phoneStr;

    private ClipboardManager clipboard;

    private ClipData clipData;
    private SetItemAdapter setItemAdapter;
    private List<PersonalCenterEntity> mList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_set, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = MyPreferences.getString("app_mail");
        phoneStr = MyPreferences.getString("phone");
        mList = new ArrayList<>();
        PersonalCenterEntity model = new PersonalCenterEntity(R.drawable.aerty, "关于我们");
        PersonalCenterEntity model1 = new PersonalCenterEntity(R.drawable.xvbrt, "注册协议");
        PersonalCenterEntity model2 = new PersonalCenterEntity(R.drawable.urtyj, "隐私协议");
        PersonalCenterEntity model3 = new PersonalCenterEntity(R.drawable.etyjsd, "投诉邮箱");
        PersonalCenterEntity model4 = new PersonalCenterEntity(R.drawable.weery, "个性化推送");
        PersonalCenterEntity model5 = new PersonalCenterEntity(R.drawable.szbs, "注销账号");
        PersonalCenterEntity model6 = new PersonalCenterEntity(R.drawable.drtuf, "退出登录");
        mList.add(model);
        mList.add(model1);
        mList.add(model2);
        mList.add(model3);
        mList.add(model4);
        mList.add(model5);
        mList.add(model6);
        setItemAdapter = new SetItemAdapter(getActivity(), mList);
        setItemAdapter.setHasStableIds(true);
        setItemAdapter.setOnItemClickListener(new SetItemAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position){
                    case 0:
                        CommonUtil.startActivity(getActivity(), GuanYuUsActivity.class, null);
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putString("title", "注册协议");
                        bundle.putString("url", MainApi.ZCXY);
                        CommonUtil.startActivity(getActivity(), UserAgreementActivity.class, bundle);
                        break;
                    case 2:
                        bundle = new Bundle();
                        bundle.putString("title", "隐私协议");
                        bundle.putString("url", MainApi.YSXY);
                        CommonUtil.startActivity(getActivity(), UserAgreementActivity.class, bundle);
                        break;
                    case 3:
                        getConfigValue();
                        break;
                    case 4:
                        generalDialog = new GeneralDialog(getActivity(), "温馨提示", "关闭或开启推送");
                        generalDialog.setBtnClickListener(new GeneralDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                Toast.makeText(getActivity(), "开启成功", Toast.LENGTH_SHORT).show();
                                generalDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                Toast.makeText(getActivity(), "关闭成功", Toast.LENGTH_SHORT).show();
                                generalDialog.dismiss();
                            }
                        });
                        generalDialog.show();
                        generalDialog.setBtnStr("开启", "关闭");
                        break;
                    case 5:
                        CommonUtil.startActivity(getActivity(), ZhuXiaoZhangHaoActivity.class, null);
                        break;
                    case 6:
                        generalDialog = new GeneralDialog(getActivity(), "温馨提示", "确定退出当前登录");
                        generalDialog.setBtnClickListener(new GeneralDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                generalDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                generalDialog.dismiss();
                                MyPreferences.saveString("phone", "");
                                CommonUtil.startActivity(getActivity(), LoginActivity.class, null);
                                getActivity().finish();
                            }
                        });
                        generalDialog.show();
                        generalDialog.setBtnStr("取消", "退出");
                        break;
                }
            }
        });
        setRcv.setHasFixedSize(true);
        setRcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setRcv.setAdapter(setItemAdapter);
    }

    private void getConfigValue(){
        MainApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseEntity<ConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<ConfigEntity>> call, retrofit2.Response<BaseEntity<ConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                ConfigEntity entity = response.body().getData();
                if (entity != null) {
                    MyPreferences.saveString("app_mail", entity.getAppMail());
                    generalDialog = new GeneralDialog(getActivity(), "温馨提示", entity.getAppMail());
                    generalDialog.setBtnClickListener(new GeneralDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            generalDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            clipData = ClipData.newPlainText(null, entity.getAppMail());
                            clipboard.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                            generalDialog.dismiss();
                        }
                    });
                    generalDialog.show();
                    generalDialog.setBtnStr("取消", "复制");
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<ConfigEntity>> call, Throwable t) {

            }
        });
    }

}
