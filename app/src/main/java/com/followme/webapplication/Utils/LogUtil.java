package com.followme.webapplication.Utils;

import android.util.Log;

public class LogUtil {

    private static String TAG = LogUtil.class.getSimpleName();

    public static void logD(String tag, Object msg){
        Log.d(TAG, tag + ": " + msg);
    }

    public static void logI(String tag, Object msg){
        Log.i(TAG, tag + ": " + msg);
    }

    public static void logW(String tag, Object msg){
        Log.w(TAG, tag + ": " + msg);
    }

    public static void logE(String tag, Object msg){
        Log.e(TAG, tag + ": " + msg);
    }
}
