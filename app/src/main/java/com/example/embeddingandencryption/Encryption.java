package com.example.embeddingandencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption extends AppCompatActivity {

    Button next;
    String AES = "AES";
    EditText inputText, inputPassword;
    String outputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encryption");

        inputText = (EditText)findViewById(R.id.GetMessage);
        inputPassword = (EditText)findViewById(R.id.Password);
        next =(Button) findViewById(R.id.NextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    outputString = encrypt(inputText.getText().toString(),inputPassword.getText().toString());

                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent( Encryption.this, Encryption2.class);
                startActivity(intent);
            }
        });

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

}