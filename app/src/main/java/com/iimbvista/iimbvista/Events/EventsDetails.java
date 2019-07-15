package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.squareup.picasso.Picasso;

public class EventsDetails extends AppCompatActivity {

    TextView event_title, event_description, event_dates;
    Button btn_register;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.event_details_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String title = getIntent().getStringExtra("Title");
        String dates = getIntent().getStringExtra("Dates");
        String description = getIntent().getStringExtra("Description");
        String img_url = getIntent().getStringExtra("Image_URL");
        final String register_url = getIntent().getStringExtra("Register_URL");

        event_title = findViewById(R.id.event_title);
        event_dates = findViewById(R.id.event_dates);
        event_description = findViewById(R.id.event_description);
        btn_register = findViewById(R.id.button_register);
        imageView = findViewById(R.id.event_image);

        event_title.setText(title);
        event_description.setText(description);
        event_dates.setText(dates);

        Picasso.with(getApplicationContext()).load(img_url).into(imageView);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterEventActivity.class);
                intent.putExtra("Register_URL", register_url);
                startActivity(intent);
            }
        });

    }
}
