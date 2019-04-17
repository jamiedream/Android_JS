package com.followme.webapplication.OKHttp;

import android.view.textclassifier.TextLinks;

import com.followme.webapplication.IEventListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class BrowserPreloader {

    private String TAG = BrowserPreloader.class.getSimpleName();

    private HashMap<String, String> mCachedPageMapper = new HashMap<>();
    private static BrowserPreloader instance;

    public static BrowserPreloader getInstance(){
        if(instance == null){
            synchronized (new Object()){
                instance = new BrowserPreloader();
            }
        }
        return instance;
    }

    public void loadUrl(final String remoteUrl, final String cacheLocation, final IEventListener<Boolean> listener){

        Request request = new Request.Builder().url(remoteUrl).get().build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Call call = client.newCall(request);
        call.equals(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDataResult(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    File file = new File(cacheLocation);
                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    sink.writeAll(response.body().source());
                    sink.close();

                    mCachedPageMapper.put(remoteUrl, cacheLocation);
                    listener.onDataResult(true);
                }

            }
        });

    }

    public String getCacheLocation(String url){

        if(mCachedPageMapper.containsKey(url)){
            return mCachedPageMapper.get(url);
        }else{
            return ""; }
    }

}
