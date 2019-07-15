package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Sponsors.SponsorDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;
    private List<Sponsors> itemList;

    public SponsorsAdapter(Context mContext, List<Sponsors> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sponsors_card,
                parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int i) {
        Sponsors sponsorsModel=itemList.get(i);
        final int currIndex = i;
        final String imgUrl=sponsorsModel.getUrl();

        Picasso.with(mContext).load(imgUrl).into(placeViewHolder.mPlace);

//        placeViewHolder.sponsorName.setText(sponsorsModel.getName());
        placeViewHolder.sponsorTitle.setText(sponsorsModel.getTitle());

        placeViewHolder.mPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SponsorDetailActivity.class);
                intent.putExtra("url", imgUrl);
                intent.putExtra("description", itemList.get(currIndex).getDescription());
                intent.putExtra("webUrl", itemList.get(currIndex).getWebUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class PlaceViewHolder extends RecyclerView.ViewHolder {

    ImageView mPlace;
    TextView sponsorName, sponsorTitle;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        mPlace = itemView.findViewById(R.id.sponsors_card_img);
//        sponsorName=itemView.findViewById(R.id.sponsors_card_name);
        sponsorTitle=itemView.findViewById(R.id.sponsors_card_title);
    }

}