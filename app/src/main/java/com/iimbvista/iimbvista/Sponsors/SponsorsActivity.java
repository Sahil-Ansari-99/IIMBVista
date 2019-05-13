package com.iimbvista.iimbvista.Sponsors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Adapter.SponsorsAdapter;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.Model.RootObject;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SponsorsActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView  recyclerView;
    SponsorsAdapter adapter;
    DrawerLayout drawerLayout;
    TextView name;
    OkHttpClient client;
    Request request;
    List<Sponsors> itemList;

    public static String SPONS_LINK="http://www.iimb-vista.com/2019/sponsors.json";

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
        getData();

        NavigationView navigationView=(NavigationView)findViewById(R.id.sponsors_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.nav_sponsors){
                    drawerLayout.closeDrawers();
                }else if(menuItem.getItemId() == R.id.nav_register){
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else if(menuItem.getItemId() == R.id.nav_login) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void getData() {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, SPONS_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson=new Gson();
                RootObject rootObject=gson.fromJson(s, new TypeToken<RootObject>(){}.getType());
                itemList=rootObject.getSponsors();
                adapter=new SponsorsAdapter(getApplicationContext(), itemList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(stringRequest);
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
