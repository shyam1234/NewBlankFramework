package com.stpl.edurp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.ProfileActivity;
import com.stpl.edurp.activities.SettingActivity;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class OptionsFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout relShareTab;
    private RelativeLayout relSettingTab;
    private RelativeLayout relLogoutTab;
    private RelativeLayout relMyProfileTab;
    private TextView mTextViewTitle;
    private ImageView mImgProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_options, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void init() {
       /* DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.obj){
                    case 2:
                        initView();
                        return true;
                }
                return false;
            }
        });*/
    }

    private void initView() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_options);
        mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        //-----------------------------------------------------------
        relShareTab = (RelativeLayout) getView().findViewById(R.id.rel_options_share_app);
        relSettingTab = (RelativeLayout) getView().findViewById(R.id.rel_options_setting);
        relLogoutTab = (RelativeLayout) getView().findViewById(R.id.rel_options_logout);
        relMyProfileTab = (RelativeLayout) getView().findViewById(R.id.rel_options_profile);
        setListener();
        setLangSelection();
    }

    private void setListener() {
        relShareTab.setOnClickListener(this);
        relSettingTab.setOnClickListener(this);
        relLogoutTab.setOnClickListener(this);
        relMyProfileTab.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_options_share_app:
                break;
            case R.id.rel_options_setting:
                navigateToNextPage(SettingActivity.class);
                break;
            case R.id.rel_options_logout:
                UserInfo.logout(R.string.msg_logout);
                break;
            case R.id.rel_options_profile:
                navigateToNextPage(ProfileActivity.class);
                break;
        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


    public void setLangSelection() {
        ((TextView) getView().findViewById(R.id.textview_title)).setText(Utils.getLangConversion(WSContant.TAG_LANG_OPTIONS, getString(R.string.tab_options), UserInfo.lang_pref));
        ((TextView) getView().findViewById(R.id.textview_options_share_title)).setText(Utils.getLangConversion(WSContant.TAG_LANG_SHARE_THIS_APP, getString(R.string.share_this_app), UserInfo.lang_pref));
        ((TextView) getView().findViewById(R.id.textview_options_profile)).setText(Utils.getLangConversion( WSContant.TAG_LANG_MYPREFERENCE, getString(R.string.tab_profile),  UserInfo.lang_pref));
        ((TextView) getView().findViewById(R.id.textview_options_setting)).setText(Utils.getLangConversion(WSContant.TAG_LANG_SETTING, getString(R.string.setting),  UserInfo.lang_pref));
        ((TextView) getView().findViewById(R.id.textview_options_logout)).setText(Utils.getLangConversion(WSContant.TAG_LANG_LOGOUT, getString(R.string.logout),UserInfo.lang_pref));

//        Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_title)), WSContant.TAG_LANG_OPTIONS, getString(R.string.tab_options), UserInfo.lang_pref);
//        Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_options_share_title)), WSContant.TAG_LANG_SHARE_THIS_APP, getString(R.string.share_this_app), UserInfo.lang_pref);
//        Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_options_profile)), WSContant.TAG_LANG_MYPREFERENCE, getString(R.string.tab_profile), UserInfo.lang_pref);
//        Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_options_setting)), WSContant.TAG_LANG_SETTING, getString(R.string.setting), UserInfo.lang_pref);
//        Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_options_logout)), WSContant.TAG_LANG_LOGOUT, getString(R.string.logout), UserInfo.lang_pref);
    }

}
