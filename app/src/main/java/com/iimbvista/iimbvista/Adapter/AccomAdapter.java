package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.AccommRoot;
import com.iimbvista.iimbvista.Model.AccommRule;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AccomAdapter extends RecyclerView.Adapter<AccomPlaceViewHolder> {

    private Context mContext;
    private List<AccommRule> itemList;

    public AccomAdapter(Context mContext, List<AccommRule> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public AccomPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accom_item,
                parent, false);
        return new AccomPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccomPlaceViewHolder placeViewHolder, int i) {
        AccommRule rule = itemList.get(i);

        placeViewHolder.cardIndex.setText(String.valueOf(rule.getIndex()));
        placeViewHolder.cardText.setText(rule.getText());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class AccomPlaceViewHolder extends RecyclerView.ViewHolder {

    TextView cardIndex, cardText;

    public AccomPlaceViewHolder(View itemView) {
        super(itemView);

        cardIndex = (TextView)itemView.findViewById(R.id.accom_card_index);
        cardText = (TextView)itemView.findViewById(R.id.accom_card_text);
    }

}