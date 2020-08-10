package myApp.michal.crossNote.Code.Classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import myApp.michal.crossNote.Databases.DbHelper;

public class ShareWorkoutHandler {

    private Context context;
    private int permissionCheck;
    private BitmapBuilder bitmapBuilder;

    public ShareWorkoutHandler(Context context, int position, DbHelper dbHelper){
        this.context = context;
        bitmapBuilder = new BitmapBuilder(context, position, dbHelper);
        permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    public void permissionCheck(int permissionGranded) {
        if (permissionCheck != permissionGranded) {
            int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            shareImage();
        }
    }

    private String generateName(){
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        return "Image-"+ n +".jpg";
    }

    private void shareImage() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, doSth(bitmapBuilder.buildText()));
        context.startActivity(Intent.createChooser(sharingIntent, "Share workout"));
    }

    private Uri doSth(Bitmap finalBitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File (myDir, generateName());
        if (file.exists ()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            String s = MediaStore.Images.Media.insertImage(context.getContentResolver(), finalBitmap, generateName(), null);
            out.flush();
            out.close();
            return Uri.parse(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}