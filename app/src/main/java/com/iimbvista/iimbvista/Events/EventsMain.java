package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.AccommodationActivity;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.MerchActivity;
import com.iimbvista.iimbvista.Model.EventsModel;
import com.iimbvista.iimbvista.ProfileActivity;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsMain extends AppCompatActivity {
    CarouselView carouselViewTop;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private List<EventsModel> itemList;
    Button button_cart;
    FragmentManager fragmentManager;
    String email, vista_id;

    private static final String JSON_URL = "http://www.iimb-vista.com/2019/events.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        Bundle bundle = new Bundle();
        bundle.putString("vista_id", vista_id);
        fragmentManager = getSupportFragmentManager();
        Day1Fragment day1Fragment = new Day1Fragment();
        Day2Fragment day2Fragment = new Day2Fragment();
        Day3Fragment day3Fragment = new Day3Fragment();
        day1Fragment.setArguments(bundle);
        day2Fragment.setArguments(bundle);
        day3Fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_1,day1Fragment).commit();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_2,day2Fragment).commit();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_3,day3Fragment).commit();

//        button_cart = findViewById(R.id.button_cart);
//            button_cart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (vista_id != null) {
//                        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                        intent.putExtra("vista_id", vista_id);
//                        intent.putExtra("email", email);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "Please login to access cart!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        itemList=new ArrayList<>();
        loadList(vista_id);

        carouselViewTop=(CarouselView)findViewById(R.id.events_main_carousel_top);
//        carouselViewTop.setPageCount(6);
//        carouselViewTop.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
////                Log.e("List", itemList.get(1).getUrl());
//                imageView.setImageResource(R.drawable.vistalogo_dark);
//            }
//        });

//            carouselViewTop.setImageListener(new ImageListener() {
//                @Override
//                public void setImageForPosition(int i, ImageView imageView) {
//
//                    EventsModel eventsModelModel = itemList.get(i);
//                    String imgUrl = eventsModelModel.getUrl();
//                    Picasso.with(getApplicationContext()).load(imgUrl).into(imageView);
//                }
//
//            });

        NavigationView navigationView=(NavigationView)findViewById(R.id.events_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.nav_sponsors){
                    startActivity(new Intent(getApplicationContext(), SponsorsActivity.class));
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_register){
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
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

    private void loadList(final String vista_id){
//        final List<EventsModel> eventsModelList=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray eventsArray = object.getJSONArray("Events");

                    for(int i=0; i<eventsArray.length(); i++)
                    {
                        JSONObject eventsObject = eventsArray.getJSONObject(i);
                        EventsModel event = new EventsModel(eventsObject.getString("title"),eventsObject.getString("url"),eventsObject.getString("date"),eventsObject.getString("time"),eventsObject.getString("description"),eventsObject.getString("location"),eventsObject.getString("cost"));
                        itemList.add(event);
//                        Log.e("List", itemList.get(i).getUrl());
                    }

                    carouselViewTop.setImageListener(new ImageListener() {
                        @Override
                        public void setImageForPosition(int position, ImageView imageView) {
                            Log.e("List", itemList.get(position).getUrl());
//                            imageView.setImageResource(R.drawable.vistalogo_dark);
                            String urlToLoad=itemList.get(position).getUrl();

                            Picasso.with(getApplicationContext()).load(urlToLoad).into(imageView);
                        }
                    });

                    carouselViewTop.setImageClickListener(new ImageClickListener() {
                        @Override
                        public void onClick(int position) {
                                Intent intent = new Intent(getApplicationContext(), PurchaseEventActivity.class);
                                intent.putExtra("title",itemList.get(position).getTitle());
                                intent.putExtra("date",itemList.get(position).getDate());
                                intent.putExtra("time",itemList.get(position).getTime());
                                intent.putExtra("description",itemList.get(position).getDescription());
                                intent.putExtra("img_url",itemList.get(position).getUrl());
                                intent.putExtra("cost", itemList.get(position).getCost());
                                intent.putExtra("location", itemList.get(position).getLocation());
                                intent.putExtra("vista_id", vista_id);
                                startActivity(intent);
                        }
                    });

                    carouselViewTop.setPageCount(itemList.size());

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
