package com.followme.webapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    /**
     * 表單的數據信息
     */
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    /**
     * 表單的結果回調</span>
     */
    private final static int FILECHOOSER_RESULTCODE = 1;
    private Uri imageUri;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/index.html");

//        webView.addJavascriptInterface(MainActivity.this,"javaMethod");
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Log.e(TAG, error.toString());
//            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.d(TAG,"shouldOverrideUrlLoading" + request.getUrl());
//                if(request.getUrl().toString().startsWith("haleyaction://")) {
//                    Log.d(TAG,"shouldOverrideUrlLoading start");
//                    Toast.makeText(MainActivity.this, "Custom protocol call", Toast.LENGTH_LONG).show();
//                    return true;
//                }else return false;
//            }
//        });


//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webView.evaluateJavascript("openCamera()", new ValueCallback<String>() {
//
//                    @Override
//                    public void onReceiveValue(String value) {
//                        try {
//                            JSONObject jo = new JSONObject(value);
////                            Toast.makeText(MainActivity.this, "返回值 "+jo.optString("callback"), Toast.LENGTH_SHORT).show();
//                            if(jo.optString("callback").equals("demoCallback")){
//                                Log.d(TAG, "jo.optString(\"callback\").equals(\"demoCallback\")");
//                                webView.setWebChromeClient(new OpenFileChromeClient());
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////                        Toast.makeText(MainActivity.this, "返回值"+value, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
    }

    @JavascriptInterface
    public void javaMethod(){
        Toast.makeText(MainActivity.this, "javascript调用了java方法！", Toast.LENGTH_SHORT).show();
    }

    public class OpenFileChromeClient extends WebChromeClient {

        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            take();
            return true;
        }

        /**
         * Android > 4.1.1 調用這個方法
         *
         * @param uploadMsg
         * @param acceptType
         * @param capture
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            take();
        }
    }


//    private void initWebView() {
//        WebSettings setting = webView.getSettings();
//        /**支持Js**/
//        setting.setJavaScriptEnabled(true);
//
//        /**設置自適應屏幕，兩者合用**/
//        //將圖片調整到適合webview的大小
//        setting.setUseWideViewPort(true);
//        // 縮放至屏幕的大小
//        setting.setLoadWithOverviewMode(true);
//
//        /**縮放操作**/
//        // 是否支持畫面縮放，默認不支持
//        setting.setBuiltInZoomControls(true);
//        setting.setSupportZoom(true);
//        // 是否顯示縮放圖標，默認顯示
//        setting.setDisplayZoomControls(false);
//        // 設置網頁內容自適應屏幕大小
//        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        /**設置允許JS彈窗**/
//        setting.setJavaScriptCanOpenWindowsAutomatically(true);
//        setting.setDomStorageEnabled(true);
//
//
//        /**關閉webview中緩存**/
//        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        /**設置可以訪問文件 **/
//        setting.setAllowFileAccess(true);
//        setting.setAllowFileAccessFromFileURLs(true);
//        setting.setAllowUniversalAccessFromFileURLs(true);
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {

//                if (result != null) {
//                    String path = getPath(getApplicationContext(),
//                            result);
//                    Uri uri = Uri.fromFile(new File(path));
//                    mUploadMessage
//                            .onReceiveValue(uri);
//                } else {
                    mUploadMessage.onReceiveValue(imageUri);
//                }
                mUploadMessage = null;


            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.BASE)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;

        if (resultCode == Activity.RESULT_OK) {

            if (data == null) {

                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }


    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        MainActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

//    @SuppressLint("NewApi")
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public static String getPath(final Context context, final Uri uri) {
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//
//                // TODO handle non-primary volumes
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{split[1]};
//
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//
//        return null;
//    }

}
