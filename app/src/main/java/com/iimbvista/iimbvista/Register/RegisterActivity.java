package com.iimbvista.iimbvista.Register;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.R;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText name,password,email,company,city,degree;
    Button btnRegister;

    public static String API_LINK="http://www.iimb-vista.com/2019/app_api/get_nonce/?controller=user&method=register";
    String nonceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        name=(EditText)findViewById(R.id.input_name);
        password=(EditText)findViewById(R.id.input_password);
        email=(EditText)findViewById(R.id.input_email);
        company=(EditText)findViewById(R.id.input_company);
        city=(EditText)findViewById(R.id.input_city);
        degree=(EditText)findViewById(R.id.input_degree);

        btnRegister=(Button)findViewById(R.id.btn_register);

        nonceId=getNonceId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=name.getText().toString();
                String userPass=password.getText().toString();
                String userEmail=email.getText().toString();

                if(!userName.isEmpty() && !userPass.isEmpty() && !userEmail.isEmpty()){
                    registerUser(userName, userPass, userEmail, nonceId);
                }
            }
        });
    }

    public String getNonceId(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET,API_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jObj = new JSONObject(response);
                    nonceId=jObj.getString("nonce");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
        return nonceId;
    }

    public void registerUser(String name, String password, String email, String nonce){
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        String url="https://www.iimb-vista.com/2019/app_api/user/register/?username="+name+"&email="+email+"&nonce="+nonce+"&display_name="+name;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    int status=obj.getInt("status");
                    if(status==2){
                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }
}
