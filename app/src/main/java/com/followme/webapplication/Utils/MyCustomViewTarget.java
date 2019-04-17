package com.followme.webapplication.Utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

public class MyCustomViewTarget extends CustomViewTarget<ImageView, Drawable> {

    private String TAG = MyCustomViewTarget.class.getSimpleName();
    private static MyCustomViewTarget instance;

    public static MyCustomViewTarget getInstance(ImageView iv){
        if(instance == null){
            synchronized (new Object()){
                instance = new MyCustomViewTarget(iv);
            }
        }
        return instance;
    }
    /**
     * Constructor that defaults {@code waitForLayout} to {@code false}.
     *
     * @param view
     */
    MyCustomViewTarget(@NonNull ImageView view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
        LogUtil.logD(TAG, "onResourceCleared");
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        LogUtil.logD(TAG, "onLoadFailed");
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        LogUtil.logD(TAG, "onResourceReady");
        view.setImageDrawable(resource);
    }
}
