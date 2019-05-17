package com.iimbvista.iimbvista.Events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.iimbvista.iimbvista.Adapter.CartAdapter;
import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList = new ArrayList<>();
        recyclerView = findViewById(R.id.cart_recyclerView);
        cartAdapter = new CartAdapter(cartList);
        recyclerView.setAdapter(cartAdapter);

    }
}
