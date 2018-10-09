package hirtz.florian.matura.ksa.studnetz.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Florian Hirtz on 24.06.2018.
 */

public class ProfilePictureModel {

    private static final int MAX_QUALITY = 100;

    private boolean success = false;
    private Context mContext;
    private Bitmap imageBitmap;
    private String BASE64, path;
    private File tempFile;

    public ProfilePictureModel(Context context, Uri imageUri, int quality) {
        //...//
        this.mContext = context;

        String path = getPath(imageUri);
        String filetype = getFileType(path);

        if(filetype.equals("img") || filetype.equals("jpeg") || filetype.equals("jpg") || filetype.equals("png")) {
            this.imageBitmap = getCompressedBitmap(path, quality);
            this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
            createTemp(this.imageBitmap, MAX_QUALITY);
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public ProfilePictureModel(Context context, Bitmap imageBitmap) {
        this.mContext = context;
        this.imageBitmap = imageBitmap;
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
        createTemp(imageBitmap, MAX_QUALITY);
    }

    public ProfilePictureModel(Context context, String BASE64) {
        //...//
        this.mContext = context;
        this.BASE64 = BASE64;
        this.imageBitmap = decodeBASE64(this.BASE64);
        createTemp(this.imageBitmap, MAX_QUALITY);
        this.success = true;
    }

    public ProfilePictureModel(Context context, File tempFile) {
        this.mContext = context;
        this.tempFile = tempFile;
        this.path = tempFile.getPath();
        this.imageBitmap = getBitmap(this.path);
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
    }

    public void update(Uri imageUri) {
        //...//
        String path = getPath(imageUri);
        String filetype = getFileType(path);

        if(filetype.equals("img") || filetype.equals("jpeg") || filetype.equals("jpg") || filetype.equals("png")) {
            this.imageBitmap = getBitmap(path);
            this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
            createTemp(this.imageBitmap, MAX_QUALITY);
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public  void updateWithPath(String path) {
        String filetype = getFileType(path);
        if(filetype.equals("img") || filetype.equals("jpeg") || filetype.equals("jpg") || filetype.equals("png")) {
            this.imageBitmap = getBitmap(path);
            this.imageBitmap.setHasAlpha(true);
            this.tempFile = new File(path);
            this.path = path;
            this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public void update() {
        this.imageBitmap = getBitmap(this.path);
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
    }

    public void update(String BASE64) {
        //...//
        this.BASE64 = BASE64;
        this.imageBitmap = decodeBASE64(this.BASE64);
        createTemp(this.imageBitmap, MAX_QUALITY);
        this.success = true;
    }

    public void update(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        createTemp(this.imageBitmap, MAX_QUALITY);
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
    }

    public void compress(int quality) {
        updateTemp(this.imageBitmap, quality);
        this.imageBitmap = getBitmap(this.path);
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
    }

    //https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
    private String encodeBASE64(Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        imageBitmap.compress(format, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private Bitmap decodeBASE64(String BASE64) {
        byte[] decodedInput = Base64.decode(BASE64, 0);
        return BitmapFactory.decodeByteArray(decodedInput, 0, decodedInput.length);
    }




    /***************************************************************************************
     *    Title: Android Upload Image to Server Tutorial - Creating Android Studio Project #2
     *    Author: Belal Khan
     *    Year: 2017
     *    Availability: https://www.youtube.com/watch?v=odmC3aa210Q
     *
     ***************************************************************************************/

    private String getPath(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String documentID = cursor.getString(0);
        documentID = documentID.substring(documentID.lastIndexOf(":") + 1);
        cursor.close();

        cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media._ID + " = ?",
                new String[]{documentID},
                null
        );

        if(cursor != null && cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        } else {
            System.out.println("Cursor failed:: " + cursor);
        }
        return null;
    }



    /***************************************************************************************
     *    Title: Android convert image uri to byte array
     *    Author: colinyeoh
     *    Year: 2012
     *    Availability: https://colinyeoh.wordpress.com/2012/05/18/android-convert-image-uri-to-byte-array/
     *
     ***************************************************************************************/
    private byte[] getByte(Uri uri, int quality) {
        byte[] data = null;
        try {
            ContentResolver cr = mContext.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    private Bitmap getCompressedBitmap(String path, int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, outstream);
        byte[] byteArray = outstream.toByteArray();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        return compressedBitmap;
    }

    private String getFileType(String path) {
        String filetype = path.substring(path.lastIndexOf(".") + 1);
        return filetype;
    }

    private void createTemp(Bitmap bitmap, int quality) {
        FileOutputStream out = null;

        File cacheDir = mContext.getCacheDir();

        try {
            tempFile = File.createTempFile("file" + String.valueOf(System.currentTimeMillis()), ".png", cacheDir);
        } catch (IOException e) {
            e.printStackTrace();
            tempFile = new File(cacheDir,"file" + String.valueOf(System.currentTimeMillis()) + ".png");
        }

        try {
            out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);

            if(out != null) {
                out.close();
                System.out.println("::temp file successfully created::");

                this.path = tempFile.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTemp(Bitmap bitmap, int quality) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);

            if(out != null) {
                out.close();
                System.out.println("::temp file successfully updated::");

                this.path = tempFile.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downscale(int scale) {
        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();

        float ratio = width / height;
        if(ratio > 1) {
            width = scale;
            height = Math.round(width / scale);
        } else {
            height = scale;
            width = Math.round(ratio * height);
        }

        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, true);
        updateTemp(this.imageBitmap, MAX_QUALITY);
        this.BASE64 = encodeBASE64(Bitmap.CompressFormat.PNG, MAX_QUALITY);
    }

    public boolean isSuccess() {
        return success;
    }

    public android.content.Context getContext() {
        return mContext;
    }

    public Bitmap getImageBitmap() {
        return CropImage.toOvalBitmap(imageBitmap);
    }

    public String getBASE64() {
        return BASE64;
    }

    public String getPath() { return path; }

    public File getTempFile() { return tempFile; }
}
