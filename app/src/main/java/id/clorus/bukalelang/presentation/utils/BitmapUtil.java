package id.clorus.bukalelang.presentation.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import id.clorus.bukalelang.presentation.config.AppConfig;

/**
 * Created by masasdani on 10/16/15.
 */
public class BitmapUtil {

    public static String readToInternalStorage(Context context, String filename, Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(AppConfig.IMAGE_DIR, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap readFromInternalStorage(String filename) {
        try {
            File f = new File(AppConfig.IMAGE_DIR, filename);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap cropBitmapToCenter(Bitmap bitmap) {
        if (bitmap.getWidth() < bitmap.getHeight())
            return Bitmap.createBitmap(
                    bitmap, 0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                    bitmap.getWidth(), bitmap.getHeight() / 2 + bitmap.getWidth() / 2);
        else if (bitmap.getWidth() > bitmap.getHeight())
            return Bitmap.createBitmap(
                    bitmap, bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                    0, bitmap.getWidth() / 2 + bitmap.getHeight() / 2, bitmap.getHeight());
        else return bitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static int[][] getBitmapRGB(Bitmap bitmap) {
        int[][] rgb = new int[bitmap.getWidth()][bitmap.getHeight()];
        for (int i = 0; i < bitmap.getHeight(); i++) {
            for (int j = 0; j < bitmap.getWidth(); j++) {
                rgb[i][j] = bitmap.getPixel(i, j);
            }
        }
        return rgb;
    }

    public static Bitmap load(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static String write(Bitmap bitmap, String path) {
        File file = new File(path);
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static Bitmap cropBitmap(Bitmap src, int x, int y, int width, int height) {
        return Bitmap.createBitmap(src, x, y, width, height);
    }

}
