package com.iimbvista.iimbvista.Events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iimbvista.iimbvista.Model.EventsModel;
import com.iimbvista.iimbvista.R;

import java.util.ArrayList;
import java.util.List;

public class Day3Fragment extends Fragment {
    TextView fragTitle;
    RecyclerView fragRecyclerView;
    EventsFragmentAdapter adapter;
    List<EventsModel> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.events_fragment, container, false);

        fragTitle=(TextView)view.findViewById(R.id.events_fragment_title);
        fragTitle.setText("Day 3");

        fragRecyclerView=(RecyclerView)view.findViewById(R.id.events_fragment_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragRecyclerView.setLayoutManager(linearLayoutManager);
        fragRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemList=new ArrayList<>();

        itemList.add(new EventsModel("Title","Title","Title","Title","Title","Title","Title"));
        itemList.add(new EventsModel("Title","Title","Title","Title","Title","Title","Title"));
        itemList.add(new EventsModel("Title","Title","Title","Title","Title","Title","Title"));

        adapter=new EventsFragmentAdapter(getContext(), itemList);
        fragRecyclerView.setAdapter(adapter);


        return view;
    }
}
