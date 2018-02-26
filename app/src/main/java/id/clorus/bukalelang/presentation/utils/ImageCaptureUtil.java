package id.clorus.bukalelang.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import id.clorus.bukalelang.R;
import id.clorus.bukalelang.presentation.config.AppConfig;


public class ImageCaptureUtil {

    public static void createSelectImageCaptureDialog(final Activity activity, final String tag) {
        final String[] items = activity.getResources().getStringArray(R.array.image_source);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                switch (item) {
                    case 0:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra("tag", tag);
                        activity.startActivityForResult(cameraIntent, AppConfig.SELECT_PHOTO_CAMERA_REQUEST_CODE);
                        break;
                    case 1:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.putExtra("tag", tag);
                        activity.startActivityForResult(galleryIntent, AppConfig.SELECT_PHOTO_GALLERY_REQUEST_CODE);
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static void createSelectImageCaptureDialog(final Fragment fragment, final String tag) {
        final String[] items = fragment.getActivity().getResources().getStringArray(R.array.image_source);
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                switch (item) {
                    case 0:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra("tag", tag);
                        fragment.startActivityForResult(cameraIntent, AppConfig.SELECT_PHOTO_CAMERA_REQUEST_CODE);
                        break;
                    case 1:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.putExtra("tag", tag);
                        fragment.startActivityForResult(galleryIntent, AppConfig.SELECT_PHOTO_GALLERY_REQUEST_CODE);
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static Bitmap getBitmapFromResultData(Activity activity, int requestCode, Intent data) {
        Bitmap bitmap = null;
        switch (requestCode) {
            case AppConfig.SELECT_PHOTO_CAMERA_REQUEST_CODE:
                bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                    int width = 512;
                    int height = (int) (bitmap.getHeight() * ((float) width / bitmap.getWidth()));
                    bitmap = BitmapUtil.scaleBitmap(bitmap, width, height);
                }
                break;
            case AppConfig.SELECT_PHOTO_GALLERY_REQUEST_CODE:
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(imagePath);
                }
                break;
        }
        /*
        if (bitmap != null) {
            int width = 512;
            int height = (int) (bitmap.getHeight() * ((float) width / bitmap.getWidth()));
            bitmap = BitmapUtil.scaleBitmap(bitmap, width, height);
        }*/
        return bitmap;
    }

    public static Bitmap getBitmapFromResultData(Fragment fragment, int requestCode, Intent data) {
        Bitmap bitmap = null;
        switch (requestCode) {
            case AppConfig.SELECT_PHOTO_CAMERA_REQUEST_CODE:
                bitmap = (Bitmap) data.getExtras().get("data");

                break;
            case AppConfig.SELECT_PHOTO_GALLERY_REQUEST_CODE:
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = fragment.getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(imagePath);
                }
                break;
        }
        /*
        if (bitmap != null) {
            int width = 512;
            int height = (int) (bitmap.getHeight() * ((float) width / bitmap.getWidth()));
            bitmap = BitmapUtil.scaleBitmap(bitmap, width, height);
        }*/
        return bitmap;
    }

    public static String location(Bitmap bitmap, String filename){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String saveImageToInternalStorage(Bitmap image, String filename, Context context){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("image", Context.MODE_PRIVATE);
        File file =new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e("ImageCaptureUtil", e.getMessage());
            }
        }
        return file.getAbsolutePath();
    }


}
