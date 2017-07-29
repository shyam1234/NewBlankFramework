package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.TableDocumentMasterDataModel;
import com.stpl.edurp.utils.GetUILImage;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Admin on 25-02-2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TableDocumentMasterDataModel> mResources;
    private View.OnClickListener mListerner;
    private static final String TEST_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";


    public CustomPagerAdapter(Context context, ArrayList<TableDocumentMasterDataModel> documents, View.OnClickListener pListner) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = documents;
        mListerner = pListner;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        JCVideoPlayerStandard video = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        imageView.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_VIDEO)) {
            GetUILImage.getInstance().setImage(mContext, "", imageView,R.drawable.image_placeholder);
            video.setVisibility(View.VISIBLE);
            video.setUp(mResources.get(position).getFileURL(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,mResources.get(position).getDocumentname());
            //video.setUp(TEST_URL, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,mResources.get(position).getDocumentname());
            GetUILImage.getInstance().setImage(mContext,"http://steveladdmusic.com/wp-content/themes/americanaura/assets/images/default-video-thumbnail.jpg", video.thumbImageView,R.drawable.image_placeholder);
        }else if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_IMAGE)) {
            GetUILImage.getInstance().setImage(mContext, mResources.get(position).getFileURL(), imageView,R.drawable.image_placeholder);
            imageView.setVisibility(View.VISIBLE);
        }

        container.addView(itemView);
        imageView.setOnClickListener(mListerner);
        imageView.setTag(position);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
