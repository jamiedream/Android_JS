package com.followme.webapplication.OKHttp;


import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;

import okio.BufferedSource;
import okio.Okio;

public class PreloadClient extends WebViewClient {

    private String TAG = PreloadClient.class.getSimpleName();

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        String url = request.getUrl().toString();
        String cacheLocation = new BrowserPreloader().getInstance().getCacheLocation(url);

        if(TextUtils.isEmpty(cacheLocation)){
            return super.shouldInterceptRequest(view, request);
        }

        File cache = new File(cacheLocation);

        try {
            BufferedSource source = (BufferedSource) Okio.buffer(Okio.sink(cache));
            return new WebResourceResponse("text/html", "utf-8", source.inputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return super.shouldInterceptRequest(view, request);

    }
}
