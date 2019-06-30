package com.iimbvista.iimbvista.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iimbvista.iimbvista.Events.EventsDetails;
import com.iimbvista.iimbvista.Model.EventsModelNew;
import com.iimbvista.iimbvista.Model.Sponsors;
import com.iimbvista.iimbvista.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<EventsModelNew> itemList;
    private Context context;

    public EventsAdapter(List<EventsModelNew> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_new,viewGroup,false);
        return new EventsAdapter.EventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder eventsViewHolder, int i) {

        final EventsModelNew eventsModelNew=itemList.get(i);
        String imgUrl=eventsModelNew.getImg_url();
        Picasso.with(context).load(imgUrl).into(eventsViewHolder.imageView);

        eventsViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventsDetails.class);
                intent.putExtra("Title", eventsModelNew.getTitle());
                intent.putExtra("Dates", eventsModelNew.getDates());
                intent.putExtra("Description", eventsModelNew.getDescription());
                intent.putExtra("Image_URL", eventsModelNew.getImg_url());
                intent.putExtra("Register_URL", eventsModelNew.getRegister_url());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.event_image);
        }
    }
}
