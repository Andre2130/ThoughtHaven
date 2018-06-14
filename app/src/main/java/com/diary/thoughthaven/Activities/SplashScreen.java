package com.diary.thoughthaven.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.diary.thoughthaven.R;


public class SplashScreen extends AppCompatActivity {

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void startApp() {
        Intent intent = new Intent(SplashScreen.this, Introduction.class);
        startActivity(intent);
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=1) {
            try {
                Thread.sleep(20);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
