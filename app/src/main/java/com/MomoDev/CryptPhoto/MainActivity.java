package com.MomoDev.CryptPhoto;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    Button encrypt;
    Button decrypt;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encrypt = findViewById(R.id.encrypt_button);
        decrypt = findViewById(R.id.decrypt_button);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST);


        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Encryption.class);
                startActivity(intent);
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Decryption.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body = "The link is:";
        String sub = "Put the link here";
        intent.putExtra(Intent.EXTRA_TEXT, Body);
        intent.putExtra(Intent.EXTRA_TEXT, sub);
        startActivity(Intent.createChooser(intent, "Share Via"));
        return super.onOptionsItemSelected(item);
    }

}