package com.iimbvista.iimbvista.Events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.iimbvista.iimbvista.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class EventsMain extends AppCompatActivity {
    CarouselView carouselViewTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main);

        carouselViewTop=(CarouselView)findViewById(R.id.events_main_carousel_top);
        carouselViewTop.setPageCount(5);
        carouselViewTop.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(R.mipmap.vistalogo_dark);
            }
        });
    }
}
