package com.iimbvista.iimbvista;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;


public class TestActivity extends AppCompatActivity {

    ImageView imageView;
    String responseString[];
    public static String API_LINK="http://www.iimb-vista.com/2019/vista_app_sponsors.php?id=1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_sponsor);

        imageView=(ImageView)findViewById(R.id.dummy_tv);
        getData();

//        Picasso.with(getApplicationContext()).load(API_LINK).into(imageView);
    }

    public void getData(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET,API_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
//                    JSONObject jObj = new JSONObject(response);
//                    String status = jObj.getString("nonce");
//                    textView.setText("success");

                    StringTokenizer tokenizer=new StringTokenizer(response,"^");
                    String name=tokenizer.nextToken();
                    String url=tokenizer.nextToken();
                    String title=tokenizer.nextToken();
//                    responseString=response.split(" ");
                    Log.e("test",name +" " + url + " " + title);
                    Picasso.with(getApplicationContext()).load(url).into(imageView);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText(error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }
}
