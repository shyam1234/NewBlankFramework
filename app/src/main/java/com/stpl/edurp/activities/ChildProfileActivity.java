package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class ChildProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageViewBack;
    private ImageView mImageViewProfile;
    private TextView mTextViewTitle;
    private ImageView mImageEdit;
    private TableStudentDetailsDataModel mModelTableStudentDetailsDataModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("list") != null) {
                mModelTableStudentDetailsDataModel = (TableStudentDetailsDataModel) bundle.getSerializable("list");
            }
        }
        initView();
    }

    private void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mImageViewProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImageViewProfile.setVisibility(View.INVISIBLE);
        mTextViewTitle.setText(getResources().getString(R.string.tab_profile));
        mImageViewBack.setVisibility(View.VISIBLE);
        mImageViewBack.setOnClickListener(this);
        mImageEdit = (ImageView) findViewById(R.id.imageView_edit);
        mImageEdit.setVisibility(View.VISIBLE);
        mImageEdit.setOnClickListener(this);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImageViewProfile);
        setDefaultStudentProfileInHeader();
    }


    private void setDefaultStudentProfileInHeader() {
        if (mModelTableStudentDetailsDataModel != null) {
            ImageView childImage = (ImageView) findViewById(R.id.imageview_profile_logo);
            ((ImageView) findViewById(R.id.imageview_profile_eye)).setVisibility(View.GONE);
            TextView childname = (TextView) findViewById(R.id.textview_profile_header_name);
            TextView childlocation = (TextView) findViewById(R.id.textview_profile_header_location);
            GetPicassoImage.setCircleImageByPicasso(this, mModelTableStudentDetailsDataModel.getImageurl(), childImage);
            childname.setText(mModelTableStudentDetailsDataModel.getFullName());
            childlocation.setText(mModelTableStudentDetailsDataModel.getCourseCode());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ChildProfileActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView_edit:
                Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
