package com.example.imageslider;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    List<Photo> photoList;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == photoList.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager_2);
        circleIndicator3 = findViewById(R.id.circle_indicator_3);

        photoList = getListPhoto();
        PhotoAdapter photoAdapter = new PhotoAdapter(photoList);
        viewPager2.setAdapter(photoAdapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        //https://developer.android.com/training/animation/screen-slide-2
//        viewPager2.setPageTransformer(new DepthPageTransformer());
    }

    private List<Photo> getListPhoto() {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.img1));
        photos.add(new Photo(R.drawable.img2));
        photos.add(new Photo(R.drawable.img3));
        photos.add(new Photo(R.drawable.img4));
        return photos;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}