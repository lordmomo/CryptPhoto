package com.example.embeddingandencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntroScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

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