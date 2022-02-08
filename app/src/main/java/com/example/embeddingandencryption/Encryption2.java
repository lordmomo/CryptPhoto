package com.example.embeddingandencryption;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Encryption2 extends AppCompatActivity {

   public  ImageView cover_img;
   public  Button ChooseImage,Embedd;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String[] cameraPermission;
    String[] storagePermission;

    private boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encryption");

        ChooseImage = findViewById(R.id.btChooseFile);
        Embedd = findViewById(R.id.btExtract);
        cover_img = findViewById(R.id.ivImage);

        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Handle button click.
        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Permissiondemo project's code
                int picd =0;
                if(picd==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromGallery();
                    }

                }else if(picd==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromGallery();
                    }
                }
            }
        });

        Intent intent = getIntent();
        byte[] hidden_image = intent.getByteArrayExtra("secret_message");
        Bitmap s_image = BitmapFactory.decodeByteArray( hidden_image, 0, hidden_image .length);

        Embedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isImageSelected){
                    Bitmap c_image = imageToBitmap();
                    Bitmap stegImage = new Embedding.embedSecretImage(c_image,s_image);
                    Intent intent = new Intent(Encryption2.this,Encryption3.class);

                    startActivity(intent);
                }else{
                    Toast.makeText(Encryption2.this, "Provide image", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void requestStoragePermission() {
        requestPermissions(storagePermission,STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private void requestCameraPermission(){
        requestPermissions(cameraPermission,CAMERA_REQUEST);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode ==RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(cover_img);
                isImageSelected = true;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case CAMERA_REQUEST:{
                if(grantResults.length > 0){
                    boolean camera_accepted = grantResults[0] ==(PackageManager.PERMISSION_GRANTED);
                    boolean storage_accepted = grantResults[1] ==(PackageManager.PERMISSION_GRANTED);
                    if(camera_accepted && storage_accepted) {
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Please enable camera and storage permission",Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST:{
                if(grantResults.length>0){
                    boolean storage_accepted= grantResults[0]==(PackageManager.PERMISSION_GRANTED);
                    if(storage_accepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Please enable storage permission",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
    private Bitmap imageToBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) cover_img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }


    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}