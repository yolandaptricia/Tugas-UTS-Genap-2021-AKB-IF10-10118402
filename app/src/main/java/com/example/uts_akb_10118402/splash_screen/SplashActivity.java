/**
 * NIM : 10118402
 * Nama : Yolanda Patricia
 * Kelas : IF-10
 * Tanggal pengerjaan : 31 Mei 2021
 */

package com.example.uts_akb_10118402.splash_screen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.uts_akb_10118402.R;
import com.example.uts_akb_10118402.slide_screen.SlideActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashPresenter sPresenter;

    private static int SplashTimeOut = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sPresenter = new SplashPresenter(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SlideActivity.class);
                startActivity(intent);
                finish();
            }
        }, SplashTimeOut);
    }
}