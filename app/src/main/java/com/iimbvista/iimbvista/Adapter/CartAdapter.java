package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.Cart;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    private List<Cart> itemList;

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

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        Cart cartModel = itemList.get(i);
        String title = cartModel.getTitle();
        String cost = cartModel.getCost();

        cardViewHolder.cardTitle.setText(title);
        cardViewHolder.cardCost.setText("Cost: " + cost);
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView cardTitle, cardCost;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cart_card_title);
            cardCost = itemView.findViewById(R.id.cart_card_cost);
        }
    }
}