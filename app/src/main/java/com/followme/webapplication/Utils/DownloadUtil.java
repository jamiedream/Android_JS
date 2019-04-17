package com.followme.webapplication.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.ref.WeakReference;

public class DownloadUtil {

    private String TAG = DownloadUtil.class.getSimpleName();

    //Glide download image from url to app's cache
    public void downloadImage(WeakReference<Context> context, ImageView imageView, String url){

        Glide.with(context.get())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(MyCustomViewTarget.getInstance(imageView));

    }


}
