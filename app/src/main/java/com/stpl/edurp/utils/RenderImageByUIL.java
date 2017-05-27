package com.stpl.edurp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Admin on 26-11-2016.
 */

public class RenderImageByUIL {
    private volatile static RenderImageByUIL mInstance;

    private RenderImageByUIL(Context pContext) {
        init(pContext);
    }

    public static synchronized RenderImageByUIL getInstance(Context pContext) {
        if (mInstance == null) {
            mInstance = new RenderImageByUIL(pContext);
        }
        return mInstance;
    }

    private void init(Context pContext) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(pContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }


    /**
     * this method renders image by using UIL
     *
     * @param imgURL
     * @param imageView
     * @param fallback
     * @param imgLoader
     */
    public void setImageByURL(String imgURL, ImageView imageView, int fallback, int imgLoader) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .cacheInMemory(true)
                .showImageOnLoading(imgLoader).build();
        //download and display image from url
        imageLoader.displayImage(imgURL, imageView, options);
    }
}
