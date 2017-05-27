package com.stpl.edurp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ForgotActivity.class.getName();
    private EditText mEditTextUserName;
    private Button mBtnSubmit;
    private ImageView mImageViewBack;
    private TextView mTextViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initView();
    }

    private void initView() {
        mEditTextUserName = (EditText) findViewById(R.id.edittext_email);
        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
        mImageViewBack = (ImageView) findViewById(R.id.imageview_back);
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(getResources().getString(R.string.forgot_password));
        setListener();
        setLangSelection();

    }

    private void setListener() {
        mBtnSubmit.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                if(Utils.isInternetConnected(this)) {
                    doForgotPassword();
                }
                break;
        }
    }

    private void doForgotPassword() {
        if (Utils.validateEmail(mEditTextUserName)) {
            mBtnSubmit.setText(getResources().getString(R.string.proceeding));
            mBtnSubmit.setEnabled(false);

            //call to WS and validate given credential----
//            Map<String, String> param = new HashMap<>();
//            param.put(WSContant.TAG_EMIALADDRESS, mEditTextEmail.getText().toString());
//            param.put(WSContant.TAG_PASSWORD, mEditTextConfirmPassword.getText().toString());
            String url = WSContant.URL_FORGET + WSContant.TAG_EMIALADDRESS
                    + "=" + mEditTextUserName.getText().toString();
            WSRequest.getInstance().requestWithParam(WSRequest.POST, url, null, null, WSContant.TAG_CHANGE_PASSWORD, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //--parsing logic------------------------------------------------------------------
                    AppLog.log(TAG, "response: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(WSContant.TAAG_DATA).equalsIgnoreCase(WSContant.TAG_SUCCESS)) {
                            mBtnSubmit.setText(getResources().getString(R.string.success));
                            mBtnSubmit.setEnabled(false);
                            navigateToSuccessPage();
                        } else {
                            mBtnSubmit.setText(getResources().getString(R.string.btn_submit));
                            mBtnSubmit.setEnabled(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //--------------------------------------------------------------------

                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    reset();
                }
            });
            //------------------------------------------------
        }
    }

    private void navigateToSuccessPage() {
        Intent i = new Intent(ForgotActivity.this, ForgotPasswordSuccessActivity.class);
       // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
        Utils.animRightToLeft(ForgotActivity.this);
    }

    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(ForgotActivity.this);
    }

    private void reset() {
        mBtnSubmit.setText(getResources().getString(R.string.btn_submit));
        mBtnSubmit.setEnabled(true);
    }

    public void setLangSelection() {
        Utils.langConversion(ForgotActivity.this, mTextViewTitle, WSContant.TAG_LANG_FORGOTPASSWORD, getString(R.string.forgot_password), UserInfo.lang_pref);
        Utils.langConversion(ForgotActivity.this, ((TextView)findViewById(R.id.textview1)), WSContant.TAG_LANG_FORGOTPASSWORDTITLE, getString(R.string.title_forgot_password), UserInfo.lang_pref);
        Utils.langConversion(ForgotActivity.this, mEditTextUserName, WSContant.TAG_LANG_EMAIL, getString(R.string.email), UserInfo.lang_pref);
        Utils.langConversion(ForgotActivity.this, mBtnSubmit, WSContant.TAG_LANG_SUBMIT, getString(R.string.btn_submit), UserInfo.lang_pref);
    }
}
