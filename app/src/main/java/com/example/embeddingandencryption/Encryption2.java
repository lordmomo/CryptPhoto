package com.example.embeddingandencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Encryption2 extends AppCompatActivity {

    ImageButton gotogallery;


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

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}