package com.MomoDev.CryptPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Encryption3 extends AppCompatActivity {

    private static int REQUEST_CODE = 101;
    ImageView img;
    Button save,share;
    //public byte[] steg_image;
    public Bitmap stegoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption3);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encryption");

        img = findViewById(R.id.imageViewStego);
        save = findViewById(R.id.saveBtn);
        share = findViewById(R.id.shareBtn);

        stegoImage= BitmapTransfer.getBitmap();
        img.setImageBitmap(stegoImage);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Encryption3.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    SaveImage();

                }else {
                    askPermission();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

    }
    private void askPermission() {

        ActivityCompat.requestPermissions(Encryption3.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);

    }

    private void SaveImage()
    {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap finalBitmap = bitmapDrawable.getBitmap();
        String root = Environment.getExternalStorageDirectory().toString();
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
           // String root = Environment.getStorageDirectory().toString();
            //File myDir = new File(root + "/CryptPhoto");

            File myDir = new File(root + "/CryptPhoto");
            boolean wasSuccessful = myDir.mkdirs();
            if (!wasSuccessful) {
                System.out.println("was not successful.");
            }
            String rNum = String.valueOf(System.currentTimeMillis());
            String fname = "CryptPhoto" + rNum + ".jpeg";
            File file = new File(myDir, fname);
            if (file.exists()) file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(Encryption3.this, "Image Saved", Toast.LENGTH_SHORT).show();
       // }
    }
    private void shareImage(){
        Uri contentUri = getContentUri(getApplicationContext());
        Intent shareint =new Intent(Intent.ACTION_SEND);
        shareint.setType("image/*");
        shareint.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        shareint.putExtra(Intent.EXTRA_STREAM,contentUri);
        shareint.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareint,"Share With"));
    }

    private Uri getContentUri(Context inContext)
    {
        BitmapDrawable Drawable= (BitmapDrawable) img.getDrawable();
        Bitmap bitmap= Drawable.getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),bitmap,"Title",null);
        return Uri.parse(path);
    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}