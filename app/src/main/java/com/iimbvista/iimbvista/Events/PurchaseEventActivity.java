package com.iimbvista.iimbvista.Events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.R;
import com.iimbvista.iimbvista.Register.RegisterationResult;
import com.squareup.picasso.Picasso;

public class PurchaseEventActivity extends AppCompatActivity {

    TextView event_title, event_description, event_cost, event_date, event_time;
    ImageView event_image;
    Button button_add_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event_title = findViewById(R.id.event_title);
        event_cost = findViewById(R.id.event_cost);
        event_description = findViewById(R.id.event_description);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
        event_image = findViewById(R.id.event_image);
        button_add_event = findViewById(R.id.button_add_event);

        final String title = getIntent().getStringExtra("title");
        final String cost = getIntent().getStringExtra("cost");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String img_url = getIntent().getStringExtra("img_url");

        event_title.append(title);
        event_time.append(time);
        event_date.append(date);
        event_description.append(description);
        event_cost.append(cost);

        Picasso.with(getApplicationContext()).load(img_url).into(event_image);

        button_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("1",title,cost);
            }
        });

    }

    public void addToCart(String vista_id, String title, String cost)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://www.iimb-vista.com/2019/add_to_cart.php?vista_id="+vista_id+"&event="+title+"&cost="+cost;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Add to Cart",response);
                    if(response.equals("Item already present in cart!")){
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    }
                    else if(response.equals("Success!"))
                    {
                        Toast.makeText(getApplicationContext(),"Event added to cart!",Toast.LENGTH_SHORT).show();
                    }
                    else if(response.equals("Failed")){
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        });

        requestQueue.add(stringRequest);

    }

}


