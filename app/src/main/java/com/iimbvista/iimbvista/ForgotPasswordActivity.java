package com.iimbvista.iimbvista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email,password,confirm_password;
    Button change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgot_email);
        password = findViewById(R.id.forgot_password);
        confirm_password = findViewById(R.id.forgot_password_confirm);
        change_password = findViewById(R.id.change_password_button);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                String userConfirmPassword=confirm_password.getText().toString();

                if (userPassword.equals(userConfirmPassword))
                {
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
