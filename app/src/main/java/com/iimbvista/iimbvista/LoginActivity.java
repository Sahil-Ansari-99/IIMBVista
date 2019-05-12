package com.iimbvista.iimbvista;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.android.volley.VolleyLog.e;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btn_login;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        btn_login=findViewById(R.id.login_button);

        linearLayout=(LinearLayout)findViewById(R.id.activity_login_layout);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                loginUser(userEmail, userPassword);
            }
        });
    }

    public void loginUser(final String email, String password){
        String loginUrl="https://www.iimb-vista.com/app_api/user/generate_auth_cookie/?insecure=cool&email="+email+"&password="+password;

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");

                    if(!status.equals("error")) {
                        Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_LONG).show();
                        Intent profIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        profIntent.putExtra("Email", email);
                        startActivity(profIntent);
                    }else{
                        Snackbar snackbar=Snackbar.make(linearLayout, "Incorrect Username or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}
