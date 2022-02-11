package com.MomoDev.CryptPhoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDecryptedMessage extends AppCompatActivity {

    TextView showMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_decrypted_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Message");

        showMessage = findViewById(R.id.messageDisplay);

        Intent intent = getIntent();
        String passMessage = intent.getStringExtra("dMessage");

        showMessage.setText(passMessage);

    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}