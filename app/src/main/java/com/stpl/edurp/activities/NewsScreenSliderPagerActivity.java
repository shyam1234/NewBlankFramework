package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.adapters.CustomPagerAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.models.TableDocumentMasterDataModel;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;
import com.stpl.edurp.utils.ZoomOutPageTransformer;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 23508 on 2/28/2017.
 */

public class NewsScreenSliderPagerActivity extends BaseActivity implements View.OnClickListener {


    private ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private ArrayList<TableDocumentMasterDataModel> mDocument;
    private int mSelectedImagePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_screen_slide);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.getSerializable(Constant.TAG_HOLDER)!=null){
                mDocument = (ArrayList<TableDocumentMasterDataModel>)bundle.getSerializable(Constant.TAG_HOLDER);
            }
           mSelectedImagePosition = bundle.getInt(Constant.TAG_POSITION);
        }
        initView();
    }

    private void initView() {
        //-----------------------------------
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.title_news_details);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new CustomPagerAdapter(this, mDocument,this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mSelectedImagePosition);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //------------------------------------
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView:
                int posi = (Integer)v.getTag();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Utils.animLeftToRight(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        JCVideoPlayer.releaseAllVideos();
    }

}
