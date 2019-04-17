package com.followme.webapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;

import com.followme.webapplication.OKHttp.BrowserPreloader;
import com.followme.webapplication.Utils.CallbackValueUtil;
import com.followme.webapplication.Utils.DownloadUtil;
import com.followme.webapplication.Utils.LoadWebViewUtil;
import com.followme.webapplication.Utils.LogUtil;
import com.followme.webapplication.Utils.PermissionUtil;
import com.followme.webapplication.Utils.PhotoUtil;
import com.followme.webapplication.Utils.ShareUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

import static com.followme.webapplication.Utils.PhotoUtil.CAMERA_PIC_REQUEST;
import static com.followme.webapplication.Utils.PhotoUtil.GALLERY_PIC_REQUEST;

public class Test extends AppCompatActivity {

    private String TAG = Test.class.getSimpleName();
    private WebView webView;
    private final MyInterface myInterface = new MyInterface(this);
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new PermissionUtil().checkPermossion(
                new WeakReference<Activity>(this),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.os.Build.VERSION_CODES.M,
                PermissionUtil.PERMISSIONS_REQUEST_WRITE
                );

        imageView = findViewById(R.id.image);


//        new DownloadUtil().downloadImage(new WeakReference<Context>(this), imageView, "https://i.redd.it/lxf3ffcnmps21.jpg");

        webView = findViewById(R.id.web_view);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test.html");
        webView.addJavascriptInterface(myInterface, "android");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading" + request.getMethod());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG, "onReceivedError" + error.toString());
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG, "onJsPrompt" + url + message + result);
                AlertDialog alertDialog = new AlertDialog.Builder(Test.this).create();
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

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(TAG, "onReceivedTitle" + title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.d(TAG, "onJsAlert" + url + message + result);
                AlertDialog alertDialog = new AlertDialog.Builder(Test.this).create();
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
                Log.d(TAG, myInterface.callbackFunctionName);
                webView.evaluateJavascript(myInterface.callbackFunctionName + "('', 'data:image/png;base64," + getFileToByte() + "')", new ValueCallback<String>() {
                    //                webView.evaluateJavascript(myInterface.callbackFunctionName + "('errorMsg', 'data:image/png;base64," + getFileToByte() + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(TAG, "callback value" + value);
                    }
                });
            }
        });
    }

    private PhotoUtil photoUtil = new PhotoUtil();
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
                    Log.d(TAG, "callback" + callback + "string" + string);
                    functionName = callback;
                    if(functionName.contains("AppCall")){
//                        photoUtil.photoFromCamera(Test.this);
//                        photoUtil.photoFromGallery(Test.this);
//                        new LoadWebViewUtil().getWebView(new WeakReference<Context>(Test.this), "index", WebViewActivity.class);
//                        new LoadWebViewUtil().closeWebView(new WeakReference<Activity>(Test.this));

//                        Uri imgPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.lxf3ffcnmps21);
//                        new ShareUtil().shareImage(
//                                new WeakReference<Context>(Test.this),
//                                "Image Shared",
//                                "https://i.redd.it/lxf3ffcnmps21.jpg",
//                                imgPath
//                        );

//                        CallbackValueUtil callbackValueUtil = new CallbackValueUtil(new WeakReference<Context>(Test.this));
//                        callbackValueUtil.saveValue("test", 123);
//                        callbackValueUtil.removeValue("test");
//                        LogUtil.logD(TAG, callbackValueUtil.retrieveValue("test", -1));

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
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.NO_WRAP);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            WeakReference<Context> wc = new WeakReference<>(getApplicationContext());
            switch (requestCode){
                case CAMERA_PIC_REQUEST:
                    Log.i(TAG, "OPEN CAMERA");
                    try {
                        photoUtil.openImage(wc, imageView, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case GALLERY_PIC_REQUEST:
                    Log.i(TAG, "OPEN GALLERY");
                    photoUtil.openGalleryImage(wc, imageView, data.getData());


                    break;
            }

        }
    }

}
