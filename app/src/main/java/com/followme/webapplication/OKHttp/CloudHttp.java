package com.followme.webapplication.OKHttp;

import android.content.Context;

import com.followme.webapplication.R;

import java.io.InputStream;

public class CloudHttp {

    private String TAG = CloudHttp.class.getSimpleName();
    private OKHttp httpUtil = new OKHttp().getInstance();

    public CloudHttp(Context context){

        InputStream inputStream = context.getResources().openRawResource(R.raw.kvbinvite);
        try {
            httpUtil.setTrustFileStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
