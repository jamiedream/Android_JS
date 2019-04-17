package com.followme.webapplication.Utils;

import android.content.Context;
import android.support.v4.util.LogWriter;

import java.lang.ref.WeakReference;

public class CallbackValueUtil {

    private String TAG = CallbackValueUtil.class.getSimpleName();
    private MyCustomSharedPreference customSharedPreference;
    private String dbName = "ValueFromWebView";

    public CallbackValueUtil(WeakReference<Context> contextWeakReference){
        customSharedPreference =
                MyCustomSharedPreference.getInstance(contextWeakReference, dbName);
    }

    public void saveValue(String key, Object value){
        customSharedPreference.setValue(key, value);
    }

    public Object retrieveValue(String key, Object retrieveValue){
        return customSharedPreference.getValue(key, retrieveValue);
    }

    public void removeValue(String key){
        customSharedPreference.remove(key);
    }

}
