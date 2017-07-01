package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageViewBack;
    private ImageView mImageViewProfile;
    private TextView mTextViewTitle;
    private ImageView mImageEdit;
    private TextView mProfileName;
    private TextView mProfileLocation;
    private ImageView mImgView;
    private TextView mProfileEmail;
    private TextView mProfilePhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }

    private void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mImageViewProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImageViewProfile.setVisibility(View.INVISIBLE);
        mTextViewTitle.setText(getResources().getString(R.string.tab_profile));
        mImageViewBack.setVisibility(View.VISIBLE);
        mImageEdit = (ImageView) findViewById(R.id.imageView_edit);
        mImageEdit.setVisibility(View.VISIBLE);
        mImageEdit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        //--------------------------------------------
        mProfileName = (TextView) findViewById(R.id.textview_profile_header_name);
        mProfileLocation = (TextView) findViewById(R.id.textview_profile_header_location);
        mImgView = (ImageView) findViewById(R.id.imageview_profile_eye);
        mImgView.setVisibility(View.GONE);
        mProfileEmail = (TextView) findViewById(R.id.textview_email_value);
        mProfilePhone = (TextView) findViewById(R.id.textview_phone);
        bindDataWithUI();
    }

    private void bindDataWithUI() {
        if (UserInfo.currUserName == null) {
            mProfileName.setText("");
        } else {
            mProfileName.setText(UserInfo.currUserName);
        }
        if (UserInfo.currUserLoc == null) {
            mProfileLocation.setVisibility(View.INVISIBLE);
        } else {
            mProfileLocation.setText(UserInfo.currUserLoc);
        }
        if (UserInfo.currUserPhoneNumber == null) {
            mProfilePhone.setText("");
        } else {
            mProfilePhone.setText(UserInfo.currUserPhoneNumber);
        }
        if (UserInfo.currUserEmail == null) {
            mProfileEmail.setText("");
        } else {
            mProfileEmail.setText(UserInfo.currUserEmail);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ProfileActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView_edit:
                Toast.makeText(this, "profile coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
