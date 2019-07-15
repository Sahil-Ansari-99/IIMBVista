package com.iimbvista.iimbvista.Sponsors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

public class SponsorDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors_detail);

        toolbar = (Toolbar)findViewById(R.id.sponsor_detail_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("url");
        String description = intent.getStringExtra("description");
        final String webUrl = intent.getStringExtra("webUrl");

        ImageView imageView = (ImageView)findViewById(R.id.sponsor_detail_img);
        Picasso.with(getApplicationContext()).load(imgUrl).into(imageView);

        TextView sponsDesc = (TextView)findViewById(R.id.sponsor_detail_txt);
        sponsDesc.setText(description);

        Button button = (Button)findViewById(R.id.sponsor_detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                startActivity(webIntent);
            }
        });
    }
}
