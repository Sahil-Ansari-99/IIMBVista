package com.iimbvista.iimbvista.Events;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iimbvista.iimbvista.AccommodationActivity;
import com.iimbvista.iimbvista.Events.CartActivity;
import com.iimbvista.iimbvista.Events.EventsMainNew;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.MerchActivity;
import com.iimbvista.iimbvista.ProfileActivity;
import com.iimbvista.iimbvista.Quiz.QuizActivity;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterNew;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.iimbvista.iimbvista.StoreClient;

public class EventsWebview extends AppCompatActivity {
    DrawerLayout drawerLayout;
    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    private String REG_URL = "https://www.iimb-vista.com/events/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        drawerLayout = (DrawerLayout) findViewById(R.id.registration_drawer_layout);

        progressBar = (ProgressBar)findViewById(R.id.register_new_progress);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.registration_toolbar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        webView = (WebView) findViewById(R.id.registration_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        progressBar.setVisibility(View.GONE);

        StoreClient storeClient = new StoreClient(this);

        webView.loadUrl(REG_URL);

        NavigationView navigationView = (NavigationView) findViewById(R.id.registration_nav_view);

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
//                    SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
//                    if(profPref.getBoolean("Logged", false)){
//                        Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_LONG).show();
//                        return true;
//                    }else{
//                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                        return true;
//                    }
                else if(menuItem.getItemId() == R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_events){
                    drawerLayout.closeDrawers();
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
//                else if(menuItem.getItemId() == R.id.nav_quiz) {
//                    startActivity(new Intent(getApplicationContext(), QuizActivity.class));
//                    return true;
//                }
                return false;
            }
        });

    }

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
