package com.followme.webapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import java.lang.ref.WeakReference;

public class ShareUtil {

    public void shareImage(WeakReference<Context> contextWeakReference, String msgTitle, String textMsg, Uri uri){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, textMsg);
        shareIntent.setType("image/text/plain");
        contextWeakReference.get().startActivity(Intent.createChooser(shareIntent,msgTitle));
    }

}