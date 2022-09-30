package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgfragment;

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

import com.qhendhwerhjjqshd.dshfretnfqqdfg.R;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity.GuanYuHaojjQianShsjHajduUsActivity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity.LoginHaojjQianShsjHajduActivity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity.UserAgreementActivityHaojjQianShsjHajdu;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgactivity.ZhuXiaoZhangHaoActivityHaojjQianShsjHajdu;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgadapter.SetItemHaojjQianShsjHajduAdapter;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.BaseHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.HaojjQianShsjHajduConfigEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgentity.PersonalCenterHaojjQianShsjHajduEntity;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfghttp.MainHaojjQianShsjHajduApi;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.CommonHaojjQianShsjHajduUtil;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.GeneralHaojjQianShsjHajduDialog;
import com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil.HaojjQianShsjHajduMyPreferences;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SetHaojjQianShsjHajduFragment extends RxFragment {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.set_rcy)
    RecyclerView setRcv;

    public View rootView;
    protected LayoutInflater layoutInflater;
    private Bundle bundle;
    private GeneralHaojjQianShsjHajduDialog generalHaojjQianShsjHajduDialog;
    private String mailStr = "", phoneStr;

    private ClipboardManager clipboard;

    private ClipData clipData;
    private SetItemHaojjQianShsjHajduAdapter setItemHaojjQianShsjHajduAdapter;
    private List<PersonalCenterHaojjQianShsjHajduEntity> mList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hao_jie_she_qtdhfery_set, null);
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
        mailStr = HaojjQianShsjHajduMyPreferences.getString("app_mail");
        phoneStr = HaojjQianShsjHajduMyPreferences.getString("phone");
        phoneTv.setText(phoneStr);
        mList = new ArrayList<>();
        PersonalCenterHaojjQianShsjHajduEntity model = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.aerty, "关于我们");
        PersonalCenterHaojjQianShsjHajduEntity model1 = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.xvbrt, "注册协议");
        PersonalCenterHaojjQianShsjHajduEntity model2 = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.urtyj, "隐私协议");
        PersonalCenterHaojjQianShsjHajduEntity model3 = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.etyjsd, "联系客服");
//        PersonalCenterEntity model4 = new PersonalCenterEntity(R.drawable.weery, "个性化推送");
        PersonalCenterHaojjQianShsjHajduEntity model5 = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.szbs, "注销账号");
        PersonalCenterHaojjQianShsjHajduEntity model6 = new PersonalCenterHaojjQianShsjHajduEntity(R.drawable.drtuf, "退出登录");
        mList.add(model);
        mList.add(model1);
        mList.add(model2);
        mList.add(model3);
//        mList.add(model4);
        mList.add(model5);
        mList.add(model6);
        setItemHaojjQianShsjHajduAdapter = new SetItemHaojjQianShsjHajduAdapter(getActivity(), mList);
        setItemHaojjQianShsjHajduAdapter.setHasStableIds(true);
        setItemHaojjQianShsjHajduAdapter.setOnItemClickListener(new SetItemHaojjQianShsjHajduAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position){
                    case 0:
                        CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), GuanYuHaojjQianShsjHajduUsActivity.class, null);
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putString("title", "注册协议");
                        bundle.putString("url", MainHaojjQianShsjHajduApi.ZCXY);
                        CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), UserAgreementActivityHaojjQianShsjHajdu.class, bundle);
                        break;
                    case 2:
                        bundle = new Bundle();
                        bundle.putString("title", "隐私协议");
                        bundle.putString("url", MainHaojjQianShsjHajduApi.YSXY);
                        CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), UserAgreementActivityHaojjQianShsjHajdu.class, bundle);
                        break;
                    case 3:
                        getConfigValue();
                        break;
//                    case 4:
//                        generalDialog = new GeneralDialog(getActivity(), "温馨提示", "关闭或开启推送");
//                        generalDialog.setBtnClickListener(new GeneralDialog.BtnClickListener() {
//                            @Override
//                            public void leftClicked() {
//                                Toast.makeText(getActivity(), "开启成功", Toast.LENGTH_SHORT).show();
//                                generalDialog.dismiss();
//                            }
//
//                            @Override
//                            public void rightClicked() {
//                                Toast.makeText(getActivity(), "关闭成功", Toast.LENGTH_SHORT).show();
//                                generalDialog.dismiss();
//                            }
//                        });
//                        generalDialog.show();
//                        generalDialog.setBtnStr("开启", "关闭");
//                        break;
                    case 4:
                        CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), ZhuXiaoZhangHaoActivityHaojjQianShsjHajdu.class, null);
                        break;
                    case 5:
                        generalHaojjQianShsjHajduDialog = new GeneralHaojjQianShsjHajduDialog(getActivity(), "温馨提示", "确定退出当前登录");
                        generalHaojjQianShsjHajduDialog.setBtnClickListener(new GeneralHaojjQianShsjHajduDialog.BtnClickListener() {
                            @Override
                            public void leftClicked() {
                                generalHaojjQianShsjHajduDialog.dismiss();
                            }

                            @Override
                            public void rightClicked() {
                                generalHaojjQianShsjHajduDialog.dismiss();
                                HaojjQianShsjHajduMyPreferences.saveString("phone", "");
                                CommonHaojjQianShsjHajduUtil.startActivity(getActivity(), LoginHaojjQianShsjHajduActivity.class, null);
                                getActivity().finish();
                            }
                        });
                        generalHaojjQianShsjHajduDialog.show();
                        generalHaojjQianShsjHajduDialog.setBtnStr("取消", "退出");
                        break;
                }
            }
        });
        setRcv.setHasFixedSize(true);
        setRcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setRcv.setAdapter(setItemHaojjQianShsjHajduAdapter);
    }

    private void getConfigValue(){
        MainHaojjQianShsjHajduApi.getRetrofitManager().getApiService().getConfig().enqueue(new Callback<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>>() {
            @Override
            public void onResponse(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, retrofit2.Response<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> response) {
                if (response.body() == null){
                    return;
                }
                HaojjQianShsjHajduConfigEntity entity = response.body().getData();
                if (entity != null) {
                    HaojjQianShsjHajduMyPreferences.saveString("app_mail", entity.getAppMail());
                    generalHaojjQianShsjHajduDialog = new GeneralHaojjQianShsjHajduDialog(getActivity(), "温馨提示", entity.getAppMail());
                    generalHaojjQianShsjHajduDialog.setBtnClickListener(new GeneralHaojjQianShsjHajduDialog.BtnClickListener() {
                        @Override
                        public void leftClicked() {
                            generalHaojjQianShsjHajduDialog.dismiss();
                        }

                        @Override
                        public void rightClicked() {
                            clipData = ClipData.newPlainText(null, entity.getAppMail());
                            clipboard.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                            generalHaojjQianShsjHajduDialog.dismiss();
                        }
                    });
                    generalHaojjQianShsjHajduDialog.show();
                    generalHaojjQianShsjHajduDialog.setBtnStr("取消", "复制");
                }
            }

            @Override
            public void onFailure(Call<BaseHaojjQianShsjHajduEntity<HaojjQianShsjHajduConfigEntity>> call, Throwable t) {

            }
        });
    }

}
