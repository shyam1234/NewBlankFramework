package com.stpl.edurp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stpl.edurp.R;

/**
 * Created by 23508 on 11/30/2016.
 */

public class CustomDialogbox extends Dialog {

    public final static int TYPE_OK = 1;
    public final static int TYPE_YES_NO = 2;
    public final static int TYPE_YES_NO_SKIP = 3;
    private final Context mContext;
    private RelativeLayout mRel;
    private int mType;
    private Button mBtnOK, mBtnYes, mBtnNo, mBtnSkip;
    private TextView mTextViewMessage;

    public CustomDialogbox(Context context, int pType) {
        super(context /*, R.style.CustomDialogAnimation*/);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        mType = pType;
        mContext = context;
        //for background transparency
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        switch (mType) {
            case TYPE_OK:
                setContentView(R.layout.dialog_ok);
                mRel = (RelativeLayout)findViewById(R.id.rel_dialog);
                mBtnOK = (Button)findViewById(R.id.btnOK);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
            case TYPE_YES_NO:
                setContentView(R.layout.dialog_yes_no);
                mRel = (RelativeLayout)findViewById(R.id.rel_dialog);
                mBtnYes = (Button)findViewById(R.id.btnYes);
                mBtnNo = (Button)findViewById(R.id.btnNo);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
            case TYPE_YES_NO_SKIP:
                setContentView(R.layout.dialog_yes_no_skip);
                mRel = (RelativeLayout)findViewById(R.id.rel_dialog);
                mBtnYes = (Button)findViewById(R.id.btnYes);
                mBtnNo = (Button)findViewById(R.id.btnNo);
                mBtnSkip = (Button)findViewById(R.id.btnSkip);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
        }
        setAnimation();
    }

    private void setAnimation() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.lin_lay);
        getmRel().clearAnimation();
        getmRel().startAnimation(anim);

        anim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_translate);
        anim.reset();
        getmRel().clearAnimation();
        getmRel().startAnimation(anim);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public Button getBtnOK() {
        return mBtnOK;
    }

    public Button getBtnYes() {
        return mBtnYes;
    }

    public Button getBtnNo() {
        return mBtnNo;
    }



    public void setTitle(String msg){
        super.setTitle(null);
        mTextViewMessage.setText(msg);
    }


    public RelativeLayout getmRel() {
        return mRel;
    }
}
