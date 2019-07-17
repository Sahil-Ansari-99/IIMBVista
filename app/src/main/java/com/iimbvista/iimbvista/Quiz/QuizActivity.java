package com.iimbvista.iimbvista.Quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.AccommodationActivity;
import com.iimbvista.iimbvista.Events.EventsMainNew;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.MerchActivity;
import com.iimbvista.iimbvista.Model.Quiz;
import com.iimbvista.iimbvista.Model.QuizRoot;
import com.iimbvista.iimbvista.Model.RootObject;
import com.iimbvista.iimbvista.ProfileActivity;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Register.RegisterNew;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private List<Quiz> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_drawer);

        drawerLayout = (DrawerLayout)findViewById(R.id.quiz_drawer_layout);

        toolbar = (Toolbar)findViewById(R.id.quiz_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        itemList = new ArrayList<>();

        fragmentManager = getSupportFragmentManager();
        QuizFragment quizFragment = new QuizFragment();
        fragmentManager.beginTransaction().replace(R.id.quiz_fragment, quizFragment).commit();

        NavigationView navigationView=(NavigationView)findViewById(R.id.quiz_nav_view);

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
                    startActivity(new Intent(getApplicationContext(), EventsMainNew.class));
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
//                    drawerLayout.closeDrawers();
//                    return true;
//                }
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
}
