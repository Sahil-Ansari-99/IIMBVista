package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.SponsorsModel;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;
    private List<SponsorsModel> itemList;

    public SponsorsAdapter(Context mContext, List<SponsorsModel> itemList) {
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
        SponsorsModel sponsorsModel=itemList.get(i);

        String imgUrl=sponsorsModel.getUrl();

//        Picasso.with(mContext).load(imgUrl).into(placeViewHolder.mPlace);

        placeViewHolder.sponsorName.setText(sponsorsModel.getName());
        placeViewHolder.sponsorTitle.setText(sponsorsModel.getTitle());
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
        sponsorName=itemView.findViewById(R.id.sponsors_card_name);
        sponsorTitle=itemView.findViewById(R.id.sponsors_card_title);
    }

}