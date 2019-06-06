package com.iimbvista.iimbvista;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.iimbvista.iimbvista.Events.EventsMain;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView workshop_number,sponsors_number,countries_number,speakers_number;
    Toolbar toolbar;
    public static boolean isAppRunning=false;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        workshop_number=findViewById(R.id.workshop_number);
        speakers_number=findViewById(R.id.speakers_number);
        sponsors_number=findViewById(R.id.sponsors_number);
        countries_number=findViewById(R.id.countries_number);
        animateTextView(0,20,workshop_number);
        animateTextView(0,20,speakers_number);
        animateTextView(0,20,sponsors_number);
        animateTextView(0,88,countries_number);

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
                    startActivity(new Intent(getApplicationContext(), EventsMain.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_profile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_logout){
                    SharedPreferences.Editor profEditor = getSharedPreferences("Profile", MODE_PRIVATE).edit();
                    profEditor.putBoolean("Logged", false);
                    profEditor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                return false;
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
        if(profPref.getBoolean("Logged", false)){
            menu.findItem(R.id.toolbar_menu_log_out).setVisible(true);
        }else{
            menu.findItem(R.id.toolbar_menu_log_out).setVisible(false);
        }
        return true;
    }

    public void updateMenu(Menu menu){
        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
        if(profPref.getBoolean("Logged", false)){
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
        }else{
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(false);
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
                //TODO
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
