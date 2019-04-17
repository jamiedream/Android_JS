package com.followme.webapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.followme.webapplication.IEventListener;
import com.followme.webapplication.OKHttp.BrowserPreloader;
import com.followme.webapplication.R;

import java.io.File;
import java.lang.ref.WeakReference;

public class LoadWebViewUtil {

    private String TAG = LoadWebViewUtil.class.getSimpleName();

    public void getWebView(WeakReference<Context> contextWeakReference, String cacheName, Class<?> intentTo){

        //Set custom listener?
        IEventListener<Boolean> listener = new IEventListener<Boolean>() {
            @Override
            public void onDataResult(Boolean result) {
                LogUtil.logD(TAG, result);
            }
        };

        String cacheLocation = contextWeakReference.get().getCacheDir().getAbsolutePath() + File.separator + cacheName + ".html";
        BrowserPreloader.getInstance().loadUrl(contextWeakReference.get().getString(R.string.url), cacheLocation, listener);
        contextWeakReference.get().startActivity(new Intent(contextWeakReference.get(), intentTo));
    }

    public void closeWebView(WeakReference<Activity> webViewActivity){
        webViewActivity.get().finish();
    }

}
