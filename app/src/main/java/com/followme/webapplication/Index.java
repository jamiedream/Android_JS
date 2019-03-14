package com.followme.webapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;

public class Index extends AppCompatActivity {

    private String TAG = Index.class.getSimpleName();
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        final MyInterface myInterface = new MyInterface(this);
        webView.addJavascriptInterface(myInterface, "android");

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.d(TAG, "onJsAlert" + url + message + result);
                AlertDialog alertDialog = new AlertDialog.Builder(Index.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getFileToByte());
                webView.evaluateJavascript(myInterface.callbackFunctionName + "('', 'data:image/png;base64," + getFileToByte() + "')", new ValueCallback<String>() {
//                webView.evaluateJavascript(myInterface.callbackFunctionName + "('errorMsg', 'data:image/png;base64," + getFileToByte() + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(TAG, value);
                    }
                });
            }
        });
    }

    class MyInterface{

        Context context;
        String functionName;
        String callbackFunctionName;

        MyInterface(Context c){
            this.context = c;
        }

        @JavascriptInterface
        public void getMessage(final String callback, final String[] string){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, callback);
                    functionName = callback;
                    if(string == null){
                        Log.d(TAG, "string equals null.");

                    }else{

                        Log.d(TAG, string[0]);
                        callbackFunctionName = string[0];
//                        try {
//                            JSONObject jo = new JSONObject(string[0]);
//                            Log.d(TAG, jo.optString("proportion"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                }
            }, 0);
        }
    }

    // put the image file path into this method
//    public String getFileToByte(String filePath){
    public String getFileToByte(){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.cat);
//            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.NO_WRAP);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

}
