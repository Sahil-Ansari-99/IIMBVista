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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Adapter.AccomAdapter;
import com.iimbvista.iimbvista.Events.CartActivity;
import com.iimbvista.iimbvista.Events.EventsMain;
import com.iimbvista.iimbvista.Events.EventsMainNew;
import com.iimbvista.iimbvista.Events.EventsWebview;
import com.iimbvista.iimbvista.Model.AccommRoot;
import com.iimbvista.iimbvista.Model.AccommRule;
import com.iimbvista.iimbvista.Quiz.QuizActivity;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Register.RegisterNew;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class AccommodationActivity extends AppCompatActivity {
    Toolbar toolbar;
    AccommRoot accommRoot;
    List<AccommRule> itemList;
    RecyclerView recyclerView;
    AccomAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.accom_drawer_layout);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.accom_toolbar);
        setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.accom_recyclerView);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        String dataString = loadData();
        Log.e("Test", dataString);
        Gson gson = new Gson();
        accommRoot = gson.fromJson(dataString, new TypeToken<AccommRoot>() {}.getType());
        itemList = accommRoot.getRules();

        adapter = new AccomAdapter(getApplicationContext(), itemList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Button storeButton = (Button)findViewById(R.id.accom_button_buy);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
            }
        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.accom_nav_view);

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
                    startActivity(new Intent(getApplicationContext(), EventsWebview.class));
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
                    drawerLayout.closeDrawers();
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

    public String loadData(){
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("Accomodation.json")));
            String line = "";
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
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
