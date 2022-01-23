package com.example.embeddingandencryption;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
//import android.database.Cursor
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Encryption2 extends AppCompatActivity {

    ImageButton gotogallery;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gotogallery = findViewById(R.id.SelectImg);

        gotogallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Encryption2.this, Encryption3.class);
                startActivity(intent);
            }
        });

    }



}