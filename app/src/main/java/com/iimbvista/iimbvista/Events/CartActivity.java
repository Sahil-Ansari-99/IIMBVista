package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.Model.ProfileModel;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterationResult;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final String email = getIntent().getStringExtra("email");
        final String vista_id = getIntent().getStringExtra("vista_id");

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

    }

    public void showCart(String vista_id)
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
                        Cart entry = new Cart(cartModel.getTitle(),cartModel.getCost());
                        cartList.add(entry);

                        cartAdapter = new CartAdapter(cartList);
                        recyclerView.setAdapter(cartAdapter);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while(true){
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int total = cartAdapter.grandTotal();
                                    cart_total.setText("Total : "+ total);
                                }
                            }
                        }).start();
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

}


