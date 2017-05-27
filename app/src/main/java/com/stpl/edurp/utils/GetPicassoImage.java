package com.stpl.edurp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.stpl.edurp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 26-11-2016.
 */

public class GetPicassoImage {
    private static final String TAG = "GetPicassoImage";
    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     * @param placeHolder
     * @param errorHolder
     * @param resize
     */
//    public static void getImage(Context pContext, String imgURL, ImageView imageView, int placeHolder, int errorHolder, int resize) {
//        if (resize == 0) {
//            resize = 50; //default resize size
//        }
//        if (imgURL != null) {
//            Picasso.with(pContext)
//                    .load(imgURL)
//                    .resize(resize, resize)
//                    .centerCrop()
//                    .placeholder(placeHolder)
//                    .error(errorHolder)
//                    .into(imageView);
//        } else {
//            imageView.setImageResource(R.drawable.logo);
//        }
//    }

    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public static void getImage(Context pContext, String imgURL, ImageView imageView) {
        try {
            AppLog.log(" getImage Picasso: ", imgURL);
            if (imgURL != null) {
                Picasso.with(pContext)
                        .load(imgURL)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }
        }catch (Exception e){
            AppLog.errLog(TAG,"getImage : "+e.getMessage());
        }
    }


    /**
     * this method renders circle image by using picasso
     *
     * @param pContext
     * @param url
     * @param imageView
     */
    public static void setCircleImageByPicasso(Context pContext, String url, ImageView imageView) {
        try {
            AppLog.log("setCircleImageByPicasso: ", url);
            if (url != null) {
                Picasso.with(pContext)
                        .load(url)
                        .fit()
                        .error(R.drawable.avater)
                        .placeholder(R.drawable.avater)
                        .transform(new ImageTransform())
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.avater);
            }
        }catch (Exception e){
            AppLog.errLog(TAG,"setCircleImageByPicasso : "+e.getMessage());
        }
    }

    /**
     * this method renders square image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public static void setSquareImageByPicasso(Context pContext, String imgURL, ImageView imageView) {
        try {
            AppLog.log(" setSquareImageByPicasso Picasso: ", imgURL);
            if (imgURL != null) {
                Picasso.with(pContext)
                        .load(imgURL)
                        .fit()
                        .transform(new SquareImageTransform())
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }
        }catch (Exception e){
            AppLog.errLog(TAG,"setSquareImageByPicasso : "+e.getMessage());
        }
    }


}
