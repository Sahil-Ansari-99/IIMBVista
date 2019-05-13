package com.iimbvista.iimbvista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Model.ProfileModel;

public class ProfileActivity extends AppCompatActivity {
    TextView name, city, college, vcap, vista_id, emailText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=(TextView)findViewById(R.id.profile_name);
        city=(TextView)findViewById(R.id.profile_city);
        college=(TextView)findViewById(R.id.profile_college);
        vcap=(TextView)findViewById(R.id.profile_vcap);
        vista_id=(TextView)findViewById(R.id.profile_vista_id);
        emailText=(TextView)findViewById(R.id.profile_email);

        progressDialog=new ProgressDialog(this);
        progressDialog.show();

        Intent intent=getIntent();
        String profEmail=intent.getStringExtra("Email");

        getProfileDetails(profEmail);
    }

    public void getProfileDetails(String email){
        String profile_url="https://www.iimb-vista.com/2019/app_profile.php/?email="+email;

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                Gson gson=new Gson();
                ProfileModel model=gson.fromJson(response, new TypeToken<ProfileModel>(){}.getType());

                progressDialog.dismiss();

                name.setText("Name: "+model.getName());
                emailText.setText("Email: "+model.getEmail());
                vcap.setText("VCAP: "+model.getVcap());
                vista_id.setText("Vista ID: "+model.getVista_id());
                city.setText("City: "+model.getCity());
                college.setText("College: "+model.getCollege());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                name.setText("Error");
            }
        });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(stringRequest);
    }
}
