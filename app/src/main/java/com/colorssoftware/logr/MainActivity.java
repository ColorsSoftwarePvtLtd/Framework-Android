package com.colorssoftware.logr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_button= (Button) findViewById(R.id.login);
        Button register_button= (Button) findViewById(R.id.register);
        Button billing =(Button) findViewById(R.id.billing);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent billing=new Intent(MainActivity.this, BillingActivity.class);
                startActivity(billing);
            }
        });
    }
}
