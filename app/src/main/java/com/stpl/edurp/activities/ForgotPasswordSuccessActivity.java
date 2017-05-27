package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 31-12-2016.
 */

public class ForgotPasswordSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnBackToLoginPage;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_successful);
        initView();
    }

    private void initView() {
        mBtnBackToLoginPage = (Button) findViewById(R.id.btnSubmit);
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mImageViewBack.setVisibility(View.INVISIBLE);
        setListener();

    }

    private void setListener() {
        mBtnBackToLoginPage.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        TextView pTextViewTitle = (TextView) findViewById(R.id.textview_title);
        pTextViewTitle.setText(getResources().getString(R.string.forgot_password_success));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ForgotPasswordSuccessActivity.this);
    }
}
