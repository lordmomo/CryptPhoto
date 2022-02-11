package com.MomoDev.CryptPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Encryption3 extends AppCompatActivity {

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
                SaveImage();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

    }

    private void SaveImage()
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap finalBitmap = bitmapDrawable.getBitmap();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/CryptPhotoV2");
        boolean wasSuccessful= myDir.mkdirs();
        if (!wasSuccessful) {
            System.out.println("was not successful.");
        }
        String rNum= String.valueOf(System.currentTimeMillis());
        String fname = "CryptPhoto"+rNum+".jpeg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(Encryption3.this,"Image Saved",Toast.LENGTH_SHORT).show();

    }
    private void shareImage(){
        Uri contentUri = getContentUri();
        Intent shareint =new Intent(Intent.ACTION_SEND);
        shareint.setType("image/*");
        shareint.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        shareint.putExtra(Intent.EXTRA_STREAM,contentUri);
        shareint.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareint,"Share With"));
    }

    private Uri getContentUri()
    {
        BitmapDrawable Drawable= (BitmapDrawable) img.getDrawable();
        Bitmap bitmap= Drawable.getBitmap();

        File imagesFolder = new File(getCacheDir(),"images");
        Uri contentUri = null;
        try{
            imagesFolder.mkdirs();
            File file = new File(imagesFolder,"shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
            contentUri = FileProvider.getUriForFile(this,"com.momodev.savenshare.fileprovider",file);

        }catch(Exception e){
            e.printStackTrace();
        }
        return contentUri;
    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}