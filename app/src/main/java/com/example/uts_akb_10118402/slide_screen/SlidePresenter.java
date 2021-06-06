/**
 * NIM : 10118402
 * Nama : Yolanda Patricia
 * Kelas : IF-10
 * Tanggal pengerjaan : 1 Juni 2021
 */

package com.example.uts_akb_10118402.slide_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.uts_akb_10118402.R;

public class SlidePresenter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlidePresenter(Context context) {
        this.context = context;
    }

    //Arrays for logo
    public int[] slide_images = {
            R.drawable.welcome2,
            R.drawable.img_3,
            R.drawable.img_2
    };

    //Array for heading
    public String[] slide_headings = {
            "Welcome!",
            "What is NoteKU?",
            "Bring Your Memory"
    };

    //Arrays for desc
    public  String[] slide_decs = {
            "Selamat Datang di Aplikasi NoteKU",
            "Disini kamu bisa menulis apapun di Memo yang tersimpan",
            "Ayo Mulai memakai NoteKU"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_decs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
