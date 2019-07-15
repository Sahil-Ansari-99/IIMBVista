package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.AccommodationActivity;
import com.iimbvista.iimbvista.Adapter.EventsAdapter;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.MerchActivity;
import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.Model.EventsModelNew;
import com.iimbvista.iimbvista.ProfileActivity;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Register.RegisterNew;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EventsMainNew extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    EventsAdapter eventsAdapter;
    RecyclerView recyclerView;
    private List<EventsModelNew> itemList;
    String email, vista_id;

    public static String EVENTS_LINK="http://iimb-vista.com/2019/events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.events_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.events_main_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
        if(profPref.getBoolean("Logged", false)) {
            email = profPref.getString("Email", null);
            vista_id = profPref.getString("vista_id", null);
        }else{
            email = null;
            vista_id = null;
        }

        NavigationView navigationView=(NavigationView)findViewById(R.id.events_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.nav_sponsors){
                    startActivity(new Intent(getApplicationContext(), SponsorsActivity.class));
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_register){
                    startActivity(new Intent(getApplicationContext(), RegisterNew.class));
                    return true;
                }
//                else if(menuItem.getItemId() == R.id.nav_login) {
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    return true;
//                }
//                else if(menuItem.getItemId() == R.id.nav_events){
//                    drawerLayout.closeDrawers();
//                }
                else if(menuItem.getItemId() == R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
//                else if(menuItem.getItemId() == R.id.nav_profile){
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                    return true;
//                }
//                else if(menuItem.getItemId() == R.id.nav_cart){
//                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
//                    return true;
//                }
//                else if(menuItem.getItemId() == R.id.nav_logout){
//                    SharedPreferences.Editor profEditor = getSharedPreferences("Profile", MODE_PRIVATE).edit();
//                    profEditor.putBoolean("Logged", false);
//                    profEditor.apply();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    return true;
//                }
                else if(menuItem.getItemId() == R.id.nav_accommodation){
                    startActivity(new Intent(getApplicationContext(), AccommodationActivity.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_merch){
                    startActivity(new Intent(getApplicationContext(), MerchActivity.class));
                    return true;
                }
                return false;
            }
        });

        itemList=new ArrayList<>();
        getData();

        recyclerView = findViewById(R.id.flagship_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventsMainNew.this);
        recyclerView.setLayoutManager(linearLayoutManager);

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

    public void getData(){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, EVENTS_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);

                StringTokenizer stringTokenizer = new StringTokenizer(response, "#");
                while (stringTokenizer.hasMoreElements())
                {
                    String event = stringTokenizer.nextToken();
                    Gson gson=new Gson();
                    EventsModelNew model = gson.fromJson(event, new TypeToken<EventsModelNew>(){}.getType());
                    EventsModelNew entry = new EventsModelNew(model.getTitle(), model.getImg_url(), model.getRegister_url(), model.getDeadline(), model.getDescription(), model.getDates());
                    itemList.add(entry);
                    eventsAdapter = new EventsAdapter(itemList, getApplicationContext());
                    recyclerView.setAdapter(eventsAdapter);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());

            }
        });


        requestQueue.add(stringRequest);
    }

}
