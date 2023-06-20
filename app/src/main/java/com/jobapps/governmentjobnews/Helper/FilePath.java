package com.jobapps.governmentjobnews.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FilePath {

    public static void setFilePathWithImage(Activity activity, CropImage.ActivityResult result, String type, HashMap<String, String> upFileMap, ImageView imageView) {
        String filePath = result.getUriFilePath(activity, true);
        upFileMap.put(type, filePath);
        Glide.with(activity).load(filePath).into(imageView);
        Log.d("imageError", filePath);
    }

    public static void crop(Activity activity, Uri imageUri) {
        try {
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setOutputCompressQuality(80)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .start(activity);
        } catch (Exception e) {
            Log.d("imageError", e.getMessage());
        }
    }

}
