package com.iimbvista.iimbvista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iimbvista.iimbvista.Events.CartActivity;
import com.iimbvista.iimbvista.Events.EventsMain;
import com.iimbvista.iimbvista.Events.EventsMainNew;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

public class MerchActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.merch_drawer_layout);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.merch_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        Button storeButton = (Button)findViewById(R.id.merch_button_buy);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
            }
        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.merch_nav_view);

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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
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
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

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
}
