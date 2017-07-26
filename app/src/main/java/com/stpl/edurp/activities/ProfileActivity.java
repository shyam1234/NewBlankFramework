package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView mImageViewBack;
    private ImageView mImageViewProfile;
    private TextView mTextViewTitle;
    private ImageView mImageEdit;
    private TextView mProfileName;
    private TextView mProfileLocation;
    private ImageView mImgView;
    private TextView mProfileEmail;
    private TextView mProfilePhone;
    private Spinner mSpinner;

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
        mImageEdit.setVisibility(View.GONE);
        mImageEdit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mSpinner = (Spinner) findViewById(R.id.spinner_language);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_language, R.layout.simple_spinner_black_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        if (UserInfo.lang_pref.equalsIgnoreCase(WSContant.TAG_ENG)) {
            //mSwitchLang.setChecked(false);
            mSpinner.setSelection(0);
        } else if (UserInfo.lang_pref.equalsIgnoreCase(WSContant.TAG_BHASHA)) {
            mSpinner.setSelection(1);
        } else {
            mSpinner.setSelection(0);
        }
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
        if (UserInfo.studentId == 0) {
            mProfileLocation.setVisibility(View.INVISIBLE);
        } else {
          /*  switch (UserInfo.currUserType){
                case WSContant.TAG_USERTYPE_PARENT:
                    mProfileLocation.setText("Parent");
                    break;
                case WSContant.TAG_USERTYPE_STUDENT:
                    mProfileLocation.setText("Student");
                    break;
                default:
                    mProfileLocation.setText(UserInfo.currUserType);
                    break;
            }*/
            mProfileLocation.setText("" + UserInfo.studentId);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            SharedPreferencesApp.getInstance().saveLangSelection(WSContant.TAG_ENG);
        } else if (position == 1) {
            SharedPreferencesApp.getInstance().saveLangSelection(WSContant.TAG_BHASHA);
        }
        //setLangSelection();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
