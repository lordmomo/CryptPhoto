package com.example.embeddingandencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        View decorView = getWindow().getDecorView();
        int uiOptions =View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        Thread thread = new Thread(){

            public void run(){
                try{
                    sleep(2500);
                }
                catch(Exception exception){

                    exception.printStackTrace();
                }
                finally{
                    Intent abc = new Intent(IntroScreen.this , MainActivity.class);
                    startActivity(abc);
                    finish();
                }
            }
        };thread.start();

    }
}