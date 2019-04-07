package com.iimbvista.iimbvista.Sponsors;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.iimbvista.iimbvista.Adapter.SponsorsAdapter;
import com.iimbvista.iimbvista.Model.SponsorsModel;
import com.iimbvista.iimbvista.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SponsorsActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView  recyclerView;
    SponsorsAdapter adapter;
    DrawerLayout drawerLayout;
    TextView name;
    OkHttpClient client;
    Request request;
    List<SponsorsModel> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        drawerLayout=(DrawerLayout)findViewById(R.id.sponsors_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.sponsors_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.sponsors_recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(SponsorsActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        itemList=new ArrayList<>();

        itemList.add(new SponsorsModel("Sponsor Name","Sponsor Title", "url"));
        itemList.add(new SponsorsModel("Sponsor Name","Sponsor Title", "url"));
        itemList.add(new SponsorsModel("Sponsor Name","Sponsor Title", "url"));
        itemList.add(new SponsorsModel("Sponsor Name","Sponsor Title", "url"));
        adapter=new SponsorsAdapter(getApplicationContext(), itemList);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        client=new OkHttpClient();
        request=new Request.Builder().url(String.format("http://www.iimb-vista.com/vista_app_sponsors.php",0)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText("Failed");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data=response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(data);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
