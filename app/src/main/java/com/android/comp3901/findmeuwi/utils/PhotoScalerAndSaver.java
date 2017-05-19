package com.android.comp3901.findmeuwi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.String.format;

/**
 * Created by Kyzer on 5/18/2017.
 */

public class PhotoScalerAndSaver {
    private static final String TAG = "com.android.comp3901";

//    public static main(String[] args) {
//        //TODO: create your photo here
//
//        //Convert your photo to a bitmap
//        Bitmap photoBm = (Bitmap) "your Bitmap image";
//
//        //scale and save the photo to a file on the directory
//        saveScaledPhotoToFile(Bitmap photoBm)
//    }

    public static void saveScaledPhotoToFile(Bitmap photoBm, String image_name, Context c) {
        //get its orginal dimensions
        int bmOriginalWidth = photoBm.getWidth();
        int bmOriginalHeight = photoBm.getHeight();
        double originalWidthToHeightRatio =  1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio =  1.0 * bmOriginalHeight / bmOriginalWidth;
        //choose a maximum height
        int maxHeight = 1024;
        //choose a max width
        int maxWidth = 1024;
        //call the method to get the scaled bitmap
        photoBm = getScaledBitmap(photoBm, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);

        /**********THE REST OF THIS IS FROM Prabu's answer*******/
        //create a byte array output stream to hold the photo's bytes
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //compress the photo's bytes into the byte array output stream
        photoBm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        //construct a File object to save the scaled file to
        File f = new File(c.getFilesDir(),
               image_name + ".jpg");
        File exF = new File(Environment.getExternalStorageDirectory()
                + File.separator + image_name +".jpg");
        try {
            //TODO delete exteral file out put
            //create the file
            f.createNewFile();
            exF.createNewFile();


            //create an FileOutputStream on the created file
            FileOutputStream fo = new FileOutputStream(f);
            FileOutputStream exFo = new FileOutputStream((exF));
            //write the photo's bytes to the file

            fo.write(bytes.toByteArray());
            exFo.write(bytes.toByteArray());

            //finish by closing the FileOutputStream
            fo.close();
            exFo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Bitmap getScaledBitmap(Bitmap bm, int bmOriginalWidth, int bmOriginalHeight, double originalWidthToHeightRatio, double originalHeightToWidthRatio, int maxHeight, int maxWidth) {
        if(bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {
            Log.v(TAG, format("RESIZING bitmap FROM %sx%s ", bmOriginalWidth, bmOriginalHeight));

            if(bmOriginalWidth > bmOriginalHeight) {
                bm = scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight, originalHeightToWidthRatio);
            } else if (bmOriginalHeight > bmOriginalWidth){
                bm = scaleDeminsFromHeight(bm, maxHeight, bmOriginalHeight, originalWidthToHeightRatio);
            }

            Log.v(TAG, format("RESIZED bitmap TO %sx%s ", bm.getWidth(), bm.getHeight()));
        }
        return bm;
    }



    private static Bitmap scaleDeminsFromHeight(Bitmap bm, int maxHeight, int bmOriginalHeight, double originalWidthToHeightRatio) {
        int newHeight = (int) Math.max(maxHeight, bmOriginalHeight * .55);
        int newWidth = (int) (newHeight * originalWidthToHeightRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    private static Bitmap scaleDeminsFromWidth(Bitmap bm, int maxWidth, int bmOriginalWidth, double originalHeightToWidthRatio) {
        //scale the width
        int newWidth = (int) Math.max(maxWidth, bmOriginalWidth * .75);
        int newHeight = (int) (newWidth * originalHeightToWidthRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }
}