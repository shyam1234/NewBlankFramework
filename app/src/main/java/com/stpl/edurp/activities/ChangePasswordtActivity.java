package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 11-12-2016.
 */
public class ChangePasswordtActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ChangePasswordtActivity.class.getName();
    private ImageView mImageViewBack;
    private EditText mEditTextEmail;
    private EditText mEditTextNewPassword;
    private EditText mEditTextConfirmPassword;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initView();
    }

    private void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mEditTextEmail = (EditText) findViewById(R.id.edittext_email);
        mEditTextNewPassword = (EditText) findViewById(R.id.edittext_new_password);
        mEditTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);

        setListener();
    }

    private void setListener() {
        mImageViewBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                if(InternetManager.isInternetConnected(this)) {
                    doChangePassword();
                }
                break;
        }
    }

    private void doChangePassword() {
        if (Utils.validateChangePassword(mEditTextEmail, mEditTextNewPassword, mEditTextConfirmPassword)) {
            mBtnSubmit.setText(getResources().getString(R.string.proceeding));
            mBtnSubmit.setEnabled(false);
            //call to WS and validate given credential----
            Map<String, String> param = new HashMap<>();
            param.put(WSContant.TAG_EMIALADDRESS, mEditTextEmail.getText().toString());
            param.put(WSContant.TAG_PASSWORD, mEditTextConfirmPassword.getText().toString());
            String url = WSContant.URL_CHANGEPASSWORD + WSContant.TAG_EMIALADDRESS
                    + "=" + mEditTextEmail.getText().toString()
                    + "&" + WSContant.TAG_PASSWORD + "="
                    + mEditTextConfirmPassword.getText().toString();
            WSRequest.getInstance().requestWithParam(WSRequest.POST, url, null, null, WSContant.TAG_CHANGE_PASSWORD, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //--parsing logic------------------------------------------------------------------
                    AppLog.log(TAG, "response: " + response);
                    //--------------------------------------------------------------------
                    mBtnSubmit.setText(getResources().getString(R.string.success));
                    mBtnSubmit.setEnabled(false);
                    onBackPressed();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    reset();
                }
            });
            //------------------------------------------------
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ChangePasswordtActivity.this);
    }

    private void reset() {
        mBtnSubmit.setText(getResources().getString(R.string.btn_submit));
        mBtnSubmit.setEnabled(true);
    }
}
