package com.iimbvista.iimbvista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Events.CartActivity;
import com.iimbvista.iimbvista.Events.EventsMain;
import com.iimbvista.iimbvista.Model.ProfileModel;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView name, city, college, vcap, vista_id, emailText;
    ProgressDialog progressDialog;
    Button btn_events;
    String profEmail;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.profile_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        name=(TextView)findViewById(R.id.profile_name);
        city=(TextView)findViewById(R.id.profile_city);
        college=(TextView)findViewById(R.id.profile_college);
        vcap=(TextView)findViewById(R.id.profile_vcap);
        vista_id=(TextView)findViewById(R.id.event_title);
        emailText=(TextView)findViewById(R.id.profile_email);
//        btn_events = findViewById(R.id.btn_events);

        progressDialog=new ProgressDialog(this);
        progressDialog.show();

        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
        boolean logStatus = profPref.getBoolean("Logged", false);

        if(logStatus){
            profEmail = profPref.getString("Email", null);
            getProfileDetails(profEmail);
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(), "Please Log In", Toast.LENGTH_SHORT).show();
        }

//        getProfileDetails(profEmail);

//        btn_events.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), EventsMain.class);
//                intent.putExtra("email", profEmail);
//                intent.putExtra("vista_id", vista_id.getText().toString().split(": ")[1]);
//                startActivity(intent);
//            }
//        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.profile_nav_view);

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
//                else if(menuItem.getItemId() == R.id.nav_events){
//                    startActivity(new Intent(getApplicationContext(), EventsMain.class));
//                    return true;
//                }
                else if(menuItem.getItemId() == R.id.nav_profile){
                    drawerLayout.closeDrawers();
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
                return false;
            }
        });

    }

    public void getProfileDetails(String email){
        String profile_url="https://www.iimb-vista.com/2019/app_profile.php/?email="+email;

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                Gson gson=new Gson();
                ProfileModel model=gson.fromJson(response, new TypeToken<ProfileModel>(){}.getType());

                SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);
                SharedPreferences.Editor editor = profPref.edit();
                editor.putString("vista_id", model.getVista_id());
                editor.apply();

                progressDialog.dismiss();

                name.setText("Name: "+model.getName());
                emailText.setText("Email: "+model.getEmail());
                vcap.setText("VCAP: "+model.getVcap());
                vista_id.setText("Vista ID: "+model.getVista_id());
                city.setText("City: "+model.getCity());
                college.setText("College: "+model.getCollege());

                if(model.getVcap() == null)
                vcap.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                name.setText("Error");
            }
        });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(stringRequest);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
