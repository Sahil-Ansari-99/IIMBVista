package com.iimbvista.iimbvista.Events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

public class PurchaseEventActivity extends AppCompatActivity {

    TextView event_title, event_description, event_cost, event_date, event_time;
    ImageView event_image;
    Button button_add_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event_title = findViewById(R.id.event_title);
        event_cost = findViewById(R.id.event_cost);
        event_description = findViewById(R.id.event_description);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
        event_image = findViewById(R.id.event_image);
        button_add_event = findViewById(R.id.button_add_event);

        String title = getIntent().getStringExtra("title");
        String cost = getIntent().getStringExtra("cost");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String img_url = getIntent().getStringExtra("img_url");

        event_title.append(title);
        event_time.append(time);
        event_date.append(date);
        event_description.append(description);
        event_cost.append(cost);

        Picasso.with(getApplicationContext()).load(img_url).into(event_image);

    }
}
