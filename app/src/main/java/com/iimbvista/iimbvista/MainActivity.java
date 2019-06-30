package com.iimbvista.iimbvista;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Events.CartActivity;
import com.iimbvista.iimbvista.Events.EventsMain;
import com.iimbvista.iimbvista.Events.EventsMainNew;
import com.iimbvista.iimbvista.Model.Home;
import com.iimbvista.iimbvista.Model.HomeRoot;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView workshop_number,sponsors_number,countries_number,speakers_number;
    TextView tv_days, tv_hours, tv_minutes, tv_seconds;
    Toolbar toolbar;
    LinearLayout timer_layout;
    View timer_view;
    CarouselView carouselView;
    public static boolean isAppRunning=false;
    private Handler handler;
    private Runnable runnable;
    Menu menu;
    List<Home> carouselList;
    private static final String CAROUSEL_URL = "http://www.iimb-vista.com/2019/Home.json";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawer_layout);

        tv_days = findViewById(R.id.txtDays);
        tv_hours = findViewById(R.id.txtHours);
        tv_minutes = findViewById(R.id.txtMinutes);
        tv_seconds = findViewById(R.id.txtSeconds);
        timer_layout = findViewById(R.id.timer_layout);
        timer_view = findViewById(R.id.timer_view);

        countDownStart();

        toolbar=(Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        carouselView = (CarouselView)findViewById(R.id.home_carousel_top);
        loadCarouselData();

        workshop_number=findViewById(R.id.workshop_number);
        speakers_number=findViewById(R.id.speakers_number);
        sponsors_number=findViewById(R.id.sponsors_number);
        countries_number=findViewById(R.id.countries_number);
//        animateTextView(0,20,workshop_number);
//        animateTextView(0,20,speakers_number);
//        animateTextView(0,20,sponsors_number);
//        animateTextView(0,4000,countries_number);

//        CardView workshop_card = (CardView)findViewById(R.id.home_workshops_card);
//        workshop_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO
//            }
//        });
//
//        CardView speakers_card = (CardView)findViewById(R.id.home_speakers_card);
//        speakers_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO
//            }
//        });
//
//        CardView sponsors_card = (CardView)findViewById(R.id.home_sponsors_card);
//        sponsors_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SponsorsActivity.class));
//            }
//        });
//
//        CardView reg_card = (CardView)findViewById(R.id.home_registrations_card);
//        reg_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
//            }
//        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.home_nav_view);

        updateMenu(navigationView.getMenu());

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
                else if(menuItem.getItemId() == R.id.nav_login) {
                    SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
                    if(profPref.getBoolean("Logged", false)){
                        Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_LONG).show();
                        return true;
                    }else{
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        return true;
                    }
                }else if(menuItem.getItemId() == R.id.nav_home){
                    drawerLayout.closeDrawers();
                }
                else if(menuItem.getItemId() == R.id.nav_events){
                    startActivity(new Intent(getApplicationContext(), EventsMainNew.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_profile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
//                else if(menuItem.getItemId() == R.id.nav_cart){
//                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
//                    return true;
//                }
                else if(menuItem.getItemId() == R.id.nav_logout){
                    SharedPreferences.Editor profEditor = getSharedPreferences("Profile", MODE_PRIVATE).edit();
                    profEditor.putBoolean("Logged", false);
                    profEditor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
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

    public void loadCarouselData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, CAROUSEL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                HomeRoot homeRoot = gson.fromJson(response, new TypeToken<HomeRoot>(){}.getType());
                carouselList = homeRoot.getHome();
                Log.e("Test", response);
                carouselView.setImageListener(new ImageListener() {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView) {
                        String urlToLoad = carouselList.get(position).getUrl();
//                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Picasso.with(getApplicationContext()).load(urlToLoad).into(imageView);
                    }
                });

                carouselView.setPageCount(carouselList.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2019-7-26");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        diff += 32410000;
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        tv_days.setText("" + String.format("%02d", days));
                        tv_hours.setText("" + String.format("%02d", hours));
                        tv_minutes.setText(""
                                + String.format("%02d", minutes));
                        tv_seconds.setText(""
                                + String.format("%02d", seconds));
                    } else {
                        timer_layout.setVisibility(View.GONE);
                        timer_view.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }



    public void animateTextView(int initialValue, int finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
//        if(profPref.getBoolean("Logged", false)){
//            menu.findItem(R.id.toolbar_menu_log_out).setVisible(true);
//        }else{
//            menu.findItem(R.id.toolbar_menu_log_out).setVisible(false);
//        }
//        return true;
//    }

    public void updateMenu(Menu menu){
        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
        if(profPref.getBoolean("Logged", false)){
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
//            menu.findItem(R.id.nav_cart).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
        }else{
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(false);
//            menu.findItem(R.id.nav_cart).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.toolbar_menu_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.toolbar_menu_cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                return true;
            case R.id.toolbar_menu_log_out:
                SharedPreferences.Editor profEditor = getSharedPreferences("Profile", MODE_PRIVATE).edit();
                profEditor.putBoolean("Logged", false);
                profEditor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppRunning=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning=false;
    }

}
