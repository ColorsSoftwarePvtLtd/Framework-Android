package com.colorssoftware.logr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.colorssoftware.library.LogR;

public class RegisterActivity extends AppCompatActivity {
    private EditText mName,mPassword,mFirstName,mLastName,mEmail;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialisation
        mName= (EditText) findViewById(R.id.editText3);
        mPassword= (EditText) findViewById(R.id.editText4);
        mFirstName= (EditText) findViewById(R.id.editText5);
        mLastName= (EditText) findViewById(R.id.editText7);
        mEmail= (EditText) findViewById(R.id.editText6);
        mRegister = (Button) findViewById(R.id.button2);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mName.getText().toString().trim();
                String user_password=mPassword.getText().toString().trim();
                String user_firstname=mFirstName.getText().toString().trim();
                String user_lastname=mLastName.getText().toString().trim();
                String user_mail=mEmail.getText().toString().trim();
                //Register using library
                LogR logr=new LogR();
                LogR.allowAllSSL();
                logr.register(username, user_password, user_firstname,user_lastname, user_mail, "https://colourssoftware.com:5443/customer/register", new LogR.AsyncResponse() {

                    @Override
                    public void onSuccess(String out) {
                        Toast.makeText(RegisterActivity.this, out, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String fail) {
                        Toast.makeText(RegisterActivity.this, fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
