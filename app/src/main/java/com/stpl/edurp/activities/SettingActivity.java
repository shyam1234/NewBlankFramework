package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageViewBack;
    private TextView mTextViewTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        ImageView image = (ImageView)findViewById(R.id.imageview_profile);
        image.setVisibility(View.INVISIBLE);
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(getResources().getString(R.string.setting));
        mImageViewBack.setVisibility(View.VISIBLE);
        mImageViewBack.setOnClickListener(this);
        //---------------------------------------------------------

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(SettingActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
        }
    }
}
