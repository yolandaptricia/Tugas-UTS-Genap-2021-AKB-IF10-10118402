/**
 * NIM : 10118402
 * Nama : Yolanda Patricia
 * Kelas : IF-10
 * Tanggal pengerjaan : 31 Mei 2021
 */

package com.example.uts_akb_10118402.splash_screen;

import android.content.Context;
import android.content.Intent;

import com.example.uts_akb_10118402.slide_screen.SlideActivity;

public class SplashPresenter implements SplashContract.Presenter {

    Context context;
    private SplashContract.View mView;
    private SplashContract.Interactor mInteractor;

    public SplashPresenter(SplashActivity mView) {
        this.mView = mView;
    }

    public void SplashPresenter(SplashContract.View mView) {
        this.mView = mView;
    }

    SlideActivity getSlide = new SlideActivity();

    @Override
    public Intent setSlide() {
        Intent intent = new Intent(context, SlideActivity.class);
        return intent;
    }
}
