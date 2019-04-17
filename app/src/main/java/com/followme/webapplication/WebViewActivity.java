package com.followme.webapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.followme.webapplication.OKHttp.PreloadClient;
import com.followme.webapplication.Utils.LogUtil;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = WebViewActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new PreloadClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl(getString(R.string.url));
        LogUtil.logD(TAG, getString(R.string.url));
    }
}
