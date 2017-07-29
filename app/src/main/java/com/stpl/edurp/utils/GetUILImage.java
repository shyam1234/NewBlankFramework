package com.stpl.edurp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.stpl.edurp.R;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;

/**
 * Created by Admin on 26-11-2016.
 */

public class GetUILImage {
    private static final String TAG = "GetUILImage";
    private volatile static GetUILImage mInstance;

    private GetUILImage() {
        init();
    }

    public static synchronized GetUILImage getInstance() {
        if (mInstance == null) {
            mInstance = new GetUILImage();
        }
        return mInstance;
    }

    private void init() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyApplication.getInstance().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }


    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public synchronized void setImage(Context pContext, String imgURL, ImageView imageView, int defaultHolder) {
        try {
            AppLog.log(" setImage from UIL: ", imgURL);
            if (imgURL != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(false)
                        .showImageForEmptyUri(defaultHolder)
                        .showImageOnFail(defaultHolder)
                        .cacheInMemory(true)
                        .showImageOnLoading(defaultHolder).build();
                //download and display image from url
                imageLoader.displayImage(imgURL, imageView, options);
            } else {
                imageView.setImageResource(defaultHolder);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setImage : " + e.getMessage());
        }
    }


    public synchronized void setGallaryImage(Context pContext, String imgURL, ImageView imageView, int defualtHolder) {
        try {
            AppLog.log(" setImage from UIL: ", imgURL);
            if (imgURL != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(false)
                        .showImageForEmptyUri(defualtHolder)
                        .showImageOnFail(defualtHolder)
                        .cacheInMemory(true)
                        .showImageOnLoading(defualtHolder).build();
                //download and display image from url
                imageLoader.displayImage(imgURL, imageView, options);
            } else {
                imageView.setImageResource(defualtHolder);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setImage : " + e.getMessage());
        }
    }


    /**
     * this method renders circle image by using picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public synchronized void setCircleImage(Context pContext, String imgURL, ImageView imageView ) {
        try {
            int defaultHolder = R.drawable.male_profile_holder;
            AppLog.log("setCircleImage: ", imgURL);
            AppLog.log("selectedStudentGender: ", UserInfo.selectedStudentGender);
            try {
                if (UserInfo.selectedStudentGender!=null && UserInfo.selectedStudentGender.equalsIgnoreCase(WSContant.TAG_FEMALE)) {
                    defaultHolder = R.drawable.female_profile_holder;
                } else {
                    defaultHolder = R.drawable.male_profile_holder;
                }
            }catch (Exception e){}

            if (imgURL != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(false)
                        .showImageForEmptyUri(defaultHolder)
                        .showImageOnFail(defaultHolder)
                        .cacheInMemory(true)
                        .displayer(new CircleBitmapDisplayer())
                        .showImageOnLoading(defaultHolder).build();
                //download and display image from url
                imageLoader.displayImage(imgURL, imageView, options);
            } else {
                imageView.setImageResource(defaultHolder);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setCircleImage : " + e.getMessage());
        }
    }


    public synchronized void setCircleImage(Context pContext, String imgURL, ImageView imageView, int defaultHolder) {
        try {
            AppLog.log("setCircleImage: ", imgURL);
            if (imgURL != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(false)
                        .showImageForEmptyUri(defaultHolder)
                        .showImageOnFail(defaultHolder)
                        .cacheInMemory(true)
                        .displayer(new CircleBitmapDisplayer())
                        .showImageOnLoading(defaultHolder).build();
                //download and display image from url
                imageLoader.displayImage(imgURL, imageView, options);
            } else {
                imageView.setImageResource(defaultHolder);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setCircleImage : " + e.getMessage());
        }
    }

    /**
     * this method renders square image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public synchronized void setSquareImage(Context pContext, String imgURL, ImageView imageView, int defaultHolder) {
        try {
            AppLog.log("setSquareImage: ", imgURL);
            if (imgURL != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(false)
                        .showImageForEmptyUri(defaultHolder)
                        .showImageOnFail(defaultHolder)
                        .cacheInMemory(true)
                        .displayer(new CircleBitmapDisplayer(25))
                        .showImageOnLoading(defaultHolder).build();
                //download and display image from url
                imageLoader.displayImage(imgURL, imageView, options);
            } else {
                imageView.setImageResource(defaultHolder);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setSquareImage : " + e.getMessage());
        }
    }

}
