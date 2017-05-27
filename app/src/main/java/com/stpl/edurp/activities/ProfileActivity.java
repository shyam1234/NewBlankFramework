package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewBack;
    private ImageView mImageViewProfile;
    private TextView mTextViewTitle;
    private ImageView mImageEdit;

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
        mImageEdit = (ImageView)findViewById(R.id.imageView_edit);
        mImageEdit.setVisibility(View.VISIBLE);
        mImageEdit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
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
