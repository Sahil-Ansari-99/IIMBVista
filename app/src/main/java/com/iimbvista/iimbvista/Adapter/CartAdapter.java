package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    private List<Cart> itemList;
    CartAdapter cartAdapter;

    public CartAdapter(List<Cart> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_card,viewGroup,false);
        return new CardViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public int grandTotal(){
        int totalPrice = 0;
        for(int i=0; i<itemList.size(); i++)
        {
            totalPrice += Integer.parseInt(itemList.get(i).getCost());
        }
        return totalPrice;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        final Cart cartModel = itemList.get(i);
        final String title = cartModel.getTitle();
        String cost = cartModel.getCost();
        final int position = i;

        cardViewHolder.cardTitle.setText(title);
        cardViewHolder.cardCost.setText("Cost: " + cost);

        cardViewHolder.removeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(v.getContext());
                String url="https://www.iimb-vista.com/2019/remove_from_cart.php?vista_id=1&event="+title;
                final Context context = v.getContext();
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Remove from Cart",response);
                            itemList.remove(cartModel);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, itemList.size());
                            Toast.makeText(context, response,Toast.LENGTH_SHORT).show();
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
        });

    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle, cardCost;
        TextView cart_total;
        FloatingActionButton removeEvent;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cart_card_title);
            cardCost = itemView.findViewById(R.id.cart_card_cost);
            removeEvent = itemView.findViewById(R.id.btn_remove_event);
            cart_total = itemView.findViewById(R.id.cart_total);

        }
    }

}

