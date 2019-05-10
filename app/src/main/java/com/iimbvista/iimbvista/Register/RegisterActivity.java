package com.iimbvista.iimbvista.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iimbvista.iimbvista.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    EditText first_name,last_name,password,email,confirm_email,company,city,degree,vcap;
    Button btnRegister;
    public static String API_LINK="http://www.iimb-vista.com/2019/app_api/get_nonce/?controller=user&method=register";
    String nonceId;
    ProgressBar progressBar;
    private ProgressBar spinner;
    boolean regResult=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        first_name=(EditText)findViewById(R.id.login_password);
        last_name=findViewById(R.id.input_last_name);
        password=(EditText)findViewById(R.id.input_password);
        email=(EditText)findViewById(R.id.input_email);
        confirm_email=findViewById(R.id.confirm_email);
        company=(EditText)findViewById(R.id.input_company);
        city=(EditText)findViewById(R.id.input_city);
        degree=(EditText)findViewById(R.id.input_degree);
        progressBar=findViewById(R.id.progressBar);
        btnRegister=(Button)findViewById(R.id.btn_register);
        vcap=findViewById(R.id.vcap);

        nonceId=getNonceId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);

                String userFirstName=first_name.getText().toString();
                String userLastName=last_name.getText().toString();
                String userPass=password.getText().toString();
                String userEmail=email.getText().toString();
                String userConfirmEmail=confirm_email.getText().toString();
                String userCompany=company.getText().toString();
                String userCity=city.getText().toString();
                String userDegree=degree.getText().toString();
                String userVCAP=vcap.getText().toString();
                String userName=userFirstName+" "+userLastName+" "+userDegree;
                String dbName=userFirstName+" "+userLastName;

                if(!userName.isEmpty() && !userPass.isEmpty() && !userEmail.isEmpty() && !userConfirmEmail.isEmpty()  && !userCompany.isEmpty() && !userCity.isEmpty() && !userDegree.isEmpty()) {

                    if(userEmail.equals(userConfirmEmail)) {
                        progressBar.setVisibility(View.VISIBLE);
//                        registerUser(userName, userPass, userEmail, nonceId);
                        sendRegRequest(userFirstName, userLastName, userPass, userEmail, userCity, userCompany, userVCAP);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Email id does not match",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private AlphaAnimation buttonClick=new AlphaAnimation(1F,0.5F);

    public String getNonceId(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET,API_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jObj = new JSONObject(response);
                    nonceId=jObj.getString("nonce");
                    Log.e("Nonce",nonceId);
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

        String url="https://www.iimb-vista.com/2019/app_api/user/register/?insecure=cool&username="+name+"&email="+email+"&nonce="+nonce+"&display_name="+name+"&user_pass="+password;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONObject obj = new JSONObject(response);
                    Log.e("Response",response);
                    regResult=true;
//                    int status=obj.getInt("status");
//                    if(status==2){
//                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
                regResult=false;
            }
        });

        requestQueue.add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }

    public  void addToDB(String db_Name, final String email , String company , String city,String VCAP)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        Date c= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        String time_stamp=df.format(c);
        String url="https://www.iimb-vista.com/2019/app_register.php?name="+db_Name+"&email="+email+"&college="+company+"&city="+city+"&date="+time_stamp+"&vcap="+VCAP;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Addition to DB",response);
                    if(response.equals("Email id already exists"))
                    {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        EditText email=(EditText)findViewById(R.id.input_email);
                        email.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
                    }
                    else if(response.equals("Registration Failed")){
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), RegisterationResult.class);
                        intent.putExtra("Result", response);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), RegisterationResult.class);
                        intent.putExtra("Result", response);
                        startActivity(intent);
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

        progressBar.setVisibility(View.GONE);
    }

    public void sendRegRequest(String firstname, String lastname, String password, String email, String city, String college, String vcap){
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        Date c= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
        String time_stamp=df.format(c);
        String url="https://www.iimb-vista.com/2019/app_reg.php?firstname="+firstname+"&lastname="+lastname+"&email="+email+"&college="+college+"&city="+city+"&vcap="+vcap+"&password="+password;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent=new Intent(getApplicationContext(), RegisterationResult.class);
                intent.putExtra("Result", response);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }
}
