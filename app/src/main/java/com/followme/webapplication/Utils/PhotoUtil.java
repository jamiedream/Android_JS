package com.followme.webapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.ref.WeakReference;

public class PhotoUtil {

    public static final int CAMERA_PIC_REQUEST = 90;
    public static final int GALLERY_PIC_REQUEST = 110;

    public void photoFromCamera(FragmentActivity fragmentActivity){
        //open camera
        fragmentActivity.startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_PIC_REQUEST);
    }

    public void photoFromGallery(FragmentActivity fragmentActivity){
        //open gallery
        fragmentActivity.startActivityForResult(
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                GALLERY_PIC_REQUEST
        );
    }

    public void openImage(WeakReference<Context> context, ImageView imageView, Intent data){

        Glide.with(context.get())
                .load((Bitmap) data.getExtras().get("data"))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(MyCustomViewTarget.getInstance(imageView));
//                .into(new MyCustomViewTarget(imageView));

    }

    public void openGalleryImage(WeakReference<Context> context, ImageView imageView, Uri selectedImage){

        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.get().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Glide.with(context.get())
                .load(BitmapFactory.decodeFile(picturePath))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(MyCustomViewTarget.getInstance(imageView));

    }

}
