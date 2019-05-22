package com.iimbvista.iimbvista.Events;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.EventsModel;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsFragmentAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;
    private List<EventsModel> itemList;
    String vista_id;

    public EventsFragmentAdapter(Context mContext, List<EventsModel> itemList, String vista_id) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.vista_id = vista_id;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_main_card,
                parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder placeViewHolder, final int i) {
        EventsModel eventsModelModel=itemList.get(i);

        String imgUrl=eventsModelModel.getUrl();
        Picasso.with(mContext).load(imgUrl).into(placeViewHolder.eventImage);

        placeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PurchaseEventActivity.class);
                intent.putExtra("title",itemList.get(i).getTitle());
                intent.putExtra("date",itemList.get(i).getDate());
                intent.putExtra("time",itemList.get(i).getTime());
                intent.putExtra("description",itemList.get(i).getDescription());
                intent.putExtra("img_url",itemList.get(i).getUrl());
                intent.putExtra("cost", itemList.get(i).getCost());
                intent.putExtra("location", itemList.get(i).getLocation());
                intent.putExtra("vista_id", vista_id);
                v.getContext().startActivity(intent);
            }
        });

//        placeViewHolder.sponsorName.setText(sponsorsModel.getName());
        placeViewHolder.eventTitle.setText(eventsModelModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

class PlaceViewHolder extends RecyclerView.ViewHolder {

    ImageView eventImage;
    TextView eventTitle;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        eventImage = itemView.findViewById(R.id.events_card_img);
        eventTitle=itemView.findViewById(R.id.events_card_title);
    }

}


