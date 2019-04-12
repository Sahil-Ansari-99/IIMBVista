package com.iimbvista.iimbvista.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iimbvista.iimbvista.MainActivity;
import com.iimbvista.iimbvista.R;

public class RegisterationResult extends AppCompatActivity {
    TextView tv;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_result);

        Intent i=getIntent();
        String resultReg=i.getStringExtra("Result");

        tv=(TextView)findViewById(R.id.reg_result_tv);
        button=(Button)findViewById(R.id.reg_result_button);

        tv.setText(resultReg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
