package com.iimbvista.iimbvista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ConfirmEmail extends AppCompatActivity {
    TextView textView;
    Button button;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_result);

        textView=(TextView)findViewById(R.id.reg_result_tv);
        button=(Button)findViewById(R.id.reg_result_button);

        progressDialog=new ProgressDialog(this);
        progressDialog.show();

        Intent intent=getIntent();
        Uri linkUri=intent.getData();

        try {
            String urlToExec = linkUri.toString();
            confirmEmail(urlToExec);
        }catch (NullPointerException e){
            e.printStackTrace();
            textView.setText("Error");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void confirmEmail(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error");
            }
        });

        requestQueue.add(stringRequest);
    }
}
