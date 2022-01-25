package com.example.embeddingandencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
*/
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

//import java.io.File;

public class Encryption extends AppCompatActivity {

    Button next;
    String AES = "AES";
    EditText inputText, inputPassword, confirmPassword;
    String outputString, text, password,c_password;
    Bitmap finalbitmap;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encryption");

        inputText = (EditText)findViewById(R.id.GetMessage);
        inputPassword = (EditText)findViewById(R.id.ePassword);
        confirmPassword =(EditText)findViewById(R.id.ConfirmPassword);
        next =(Button) findViewById(R.id.NextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int dimen = width < height ? width : height;
                dimen = dimen * 3 / 4;

                text = getSecretMessage();
                password = getPassword();
                c_password = getConfirmPassword();
                if (!text.isEmpty() && !password.isEmpty() && !c_password.isEmpty())
                {
                    if(password.equals(c_password)) {
                        try {
                            outputString = encrypt(text, password);
                            qrgEncoder = new QRGEncoder(outputString, null, QRGContents.Type.TEXT, dimen);
                            try {
                              finalbitmap = qrgEncoder.encodeAsBitmap();
                                SaveImage( finalbitmap );
                             //qrCodeIV.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                             Log.e("Tag", e.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Encryption.this, Encryption2.class);
                        //intent.putExtra("");
                        startActivity(intent);
                    }
                    else{

                        Toast.makeText(Encryption.this,"Passwords are not same",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(Encryption.this, "enter message and password",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
    private void SaveImage(Bitmap finalBitmap)
    {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/CryptPhoto");
        boolean wasSuccessful= myDir.mkdirs();
        if (!wasSuccessful) {
            System.out.println("was not successful.");
        }
        String fname = "Image-1.png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;
    }

    //data encodes in messageDigest

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


    @Override
    public boolean onSupportNavigateUp () {
            onBackPressed();
            return super.onSupportNavigateUp();
    }


    public String getSecretMessage() {
        return inputText.getText().toString().trim();
    }


    public String getPassword(){
        return inputPassword.getText().toString().trim();
    }


    private String getConfirmPassword() {
        return confirmPassword.getText().toString().trim();
    }

}

