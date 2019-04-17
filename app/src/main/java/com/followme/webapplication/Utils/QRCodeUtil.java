package com.followme.webapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.followme.webapplication.R;
import com.followme.zxing_android.IntentIntegrator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.lang.ref.WeakReference;

public class QRCodeUtil {

    /**
     * Decode
     *
     * Parameters refer to Intents.java final class SCAN
     * */
    private final String ACTION = "com.google.zxing.client.android.SCAN";
    private final int SCAN_CODE = 30;
    private final String SCAN_RESULT = "SCAN_RESULT";
    private final String SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";

    public void intentToInstalledApp(WeakReference<Activity> activity){
        IntentIntegrator integrator = new IntentIntegrator(activity.get());
        integrator.initiateScan();
    }

    public void openApp(WeakReference<Activity> activity){
        Intent intent = new Intent(ACTION);
        activity.get().startActivityForResult(intent, SCAN_CODE);
    }

    public int getScanCode(){
        return SCAN_CODE;
    }

    public String getScaneResult(Intent intent){
        return intent.getStringExtra(SCAN_RESULT);
    }

    public String getScaneResultFormat(Intent intent){
        return intent.getStringExtra(SCAN_RESULT_FORMAT);
    }

    /**
     * Encode
     * */

    public Bitmap textToImageEncode(WeakReference<Context> context, String value){

        int width = 150;
        int height = 150;
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.QR_CODE,
                    width,
                    height);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int[] pixels = new int[width * height];
        for(int numY = 0; numY < height; numY ++){
            int offset = numY * width;
            for(int numX = 0; numX < width; numX ++){
                pixels[offset + numX] = ContextCompat.getColor(context.get(), bitMatrix.get(numX, numY)? R.color.black : R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
