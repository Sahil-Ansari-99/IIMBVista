package com.iimbvista.iimbvista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import com.iimbvista.iimbvista.Register.RegisterActivity;
import com.iimbvista.iimbvista.Sponsors.SponsorsActivity;

import org.json.JSONObject;

import static com.android.volley.VolleyLog.e;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btn_login;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_drawer);

        drawerLayout=(DrawerLayout)findViewById(R.id.login_drawer_layout);

        toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_toggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        email=findViewById(R.id.forgot_email);
        password=findViewById(R.id.forgot_password);
        btn_login=findViewById(R.id.change_password_button);

        progressDialog = new ProgressDialog(LoginActivity.this);

        linearLayout=(LinearLayout)findViewById(R.id.activity_login_layout);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                loginUser(userEmail, userPassword);
            }
        });

        NavigationView navigationView=(NavigationView)findViewById(R.id.login_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.nav_sponsors){
                    startActivity(new Intent(getApplicationContext(), SponsorsActivity.class));
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_register){
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    return true;
                }
                else if(menuItem.getItemId() == R.id.nav_login) {
                    drawerLayout.closeDrawers();
                }else if(menuItem.getItemId() == R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
//                else if(menuItem.getItemId() == R.id.nav_events){
//                    startActivity(new Intent(getApplicationContext(), EventsMain.class));
//                    return true;
//                }
                return false;
            }
        });

    }

    public void loginUser(final String email, String password){
        String loginUrl="https://www.iimb-vista.com/app_api/user/generate_auth_cookie/?insecure=cool&email="+email+"&password="+password;

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status=jsonObject.getString("status");

                    if(!status.equals("error")) {
                        Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_LONG).show();
                        Intent profIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        profIntent.putExtra("Email", email);
                        SharedPreferences.Editor profPref = getSharedPreferences("Profile", MODE_PRIVATE).edit();
                        profPref.putString("Email", email);
                        profPref.putBoolean("Logged", true);
                        profPref.apply();
                        startActivity(profIntent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Error", error.toString());
                Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
