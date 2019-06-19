package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Adapter.CartAdapter;
import com.iimbvista.iimbvista.LoginActivity;
import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.Model.ProfileModel;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Register.RegisterationResult;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Cart> cartList;
    Button btn_purchase;
    TextView cart_total;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.cart_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        SharedPreferences profPref = getSharedPreferences("Profile", MODE_PRIVATE);

        final String email = profPref.getString("Email", null);
        final String vista_id = profPref.getString("vista_id", null);

        cartList = new ArrayList<>();

        recyclerView = findViewById(R.id.cart_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btn_purchase = findViewById(R.id.btn_purchase);
        cart_total = findViewById(R.id.cart_total);

        showCart(vista_id);

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartEmail(vista_id, email);
            }
        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.cart_nav_view);

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

    public void showCart(final String vista_id)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://www.iimb-vista.com/2019/cart.php?vista_id="+vista_id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Show Cart",response);
                    String s = response;
                    StringTokenizer stringTokenizer = new StringTokenizer(s, "#");
                    while (stringTokenizer.hasMoreElements()){
                        String lat = stringTokenizer.nextToken();
                        Gson gson=new Gson();
                        Cart cartModel=gson.fromJson(lat, new TypeToken<Cart>(){}.getType());
                        String title = cartModel.getTitle().replace("_", " ");
                        Cart entry = new Cart(title,cartModel.getCost());
                        cartList.add(entry);
                        cartAdapter = new CartAdapter(cartList, vista_id, cart_total);
                        recyclerView.setAdapter(cartAdapter);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    public void cartEmail(String vista_id, String email)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://www.iimb-vista.com/2019/events_email.php?vista_id="+vista_id+"&email="+email;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cart Email", response);
                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.e("Response", error.toString());
                } else if (error instanceof NoConnectionError) {
                    Log.e("Response", "No Connection");
                }
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
        btn_purchase.setClickable(false);
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


