package com.iimbvista.iimbvista.Events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.EventsModel;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsFragmentAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;
    private List<EventsModel> itemList;

    public EventsFragmentAdapter(Context mContext, List<EventsModel> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_main_card,
                parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int i) {
        EventsModel eventsModelModel=itemList.get(i);

        String imgUrl=eventsModelModel.getUrl();

//        Picasso.with(mContext).load(imgUrl).into(placeViewHolder.eventImage);
        placeViewHolder.eventImage.setImageResource(R.mipmap.vistalogo_dark);

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
