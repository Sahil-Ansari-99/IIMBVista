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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.Model.EventsModel;
import com.iimbvista.iimbvista.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Day1Fragment extends Fragment {
    TextView fragTitle;
    RecyclerView fragRecyclerView;
    EventsFragmentAdapter adapter;
    List<EventsModel> itemList;

    private static final String JSON_URL = "http://www.iimb-vista.com/2019/events.json";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.events_fragment, container, false);

        fragTitle=(TextView)view.findViewById(R.id.events_fragment_title);
        fragTitle.setText("Day 1");

        fragRecyclerView=(RecyclerView)view.findViewById(R.id.events_fragment_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragRecyclerView.setLayoutManager(linearLayoutManager);
        fragRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemList=new ArrayList<>();
        loadList();

        adapter=new EventsFragmentAdapter(getContext(), itemList);
        fragRecyclerView.setAdapter(adapter);



        return view;
    }

    public void loadList(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray eventsArray = object.getJSONArray("Events");

                    for(int i=0; i<eventsArray.length(); i++)
                    {
                        JSONObject eventsObject = eventsArray.getJSONObject(i);
                        EventsModel event = new EventsModel(eventsObject.getString("title"),eventsObject.getString("url"),eventsObject.getString("date"),eventsObject.getString("time"),eventsObject.getString("description"),eventsObject.getString("location"),eventsObject.getString("cost"));
                        itemList.add(event);
                    }

                    EventsFragmentAdapter adapter = new EventsFragmentAdapter(getContext(), itemList);
                    fragRecyclerView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
