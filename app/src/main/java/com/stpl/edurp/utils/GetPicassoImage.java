package com.stpl.edurp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.stpl.edurp.R;

/**
 * Created by Admin on 26-11-2016.
 */

public class GetPicassoImage {
    private final String TAG = "GetPicassoImage";
    private static GetPicassoImage mInstance;

    private GetPicassoImage() {
    }

    public synchronized static GetPicassoImage getInstance() {
        if (mInstance == null) {
            mInstance = new GetPicassoImage();
        }
        return mInstance;
    }


    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public synchronized void setImage(Context pContext, String imgURL, ImageView imageView) {
        try {
            AppLog.log(" setImage Picasso: ", imgURL);
            if (imgURL != null) {
                PicassoCache.getPicassoInstance(pContext).with(pContext)
                        .load(imgURL)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setImage Picasso : " + e.getMessage());
        }
    }


    /**
     * this method renders circle image by using picasso
     *
     * @param pContext
     * @param url
     * @param imageView
     */
    public synchronized void setCircleImage(Context pContext, String url, ImageView imageView) {
        try {
            AppLog.log("setCircleImage: Picasso ", url);
            if (url != null) {
                 PicassoCache.getPicassoInstance(pContext).with(pContext)
                        .load(url)
                        .fit()
                        .error(R.drawable.avater)
                        .placeholder(R.drawable.avater)
                        .transform(new ImageTransform())
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.avater);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setCircleImage Picasso : " + e.getMessage());
        }
    }

    /**
     * this method renders square image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public synchronized void setSquareImage(Context pContext, String imgURL, ImageView imageView) {
        try {
            AppLog.log(" setSquareImage Picasso: ", imgURL);
            if (imgURL != null) {
                 PicassoCache.getPicassoInstance(pContext).with(pContext)
                        .load(imgURL)
                        .fit()
                        .transform(new SquareImageTransform())
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "setSquareImage Picasso: " + e.getMessage());
        }
    }


}
