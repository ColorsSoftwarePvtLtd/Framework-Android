package com.colorssoftware.logr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.colorssoftware.library.LogR;

public class BillingActivity extends AppCompatActivity {

    private EditText mcustomerId,mFirstName,mLastName,mCompany,mAddress1,mAddress2,mCity,mState,mZip,mCountry;
    private Button mCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        final String id=getIntent().getStringExtra("id");
        //Initialization
        mcustomerId = (EditText) findViewById(R.id.customerId);
        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mCompany = (EditText) findViewById(R.id.company);
        mAddress1 = (EditText) findViewById(R.id.address_1);
        mAddress2 = (EditText) findViewById(R.id.address_2);
        mCity = (EditText) findViewById(R.id.city);
        mState = (EditText) findViewById(R.id.state);
        mZip = (EditText) findViewById(R.id.zip_code);
        mCountry = (EditText) findViewById(R.id.country);
        mCheckout = (Button) findViewById(R.id.checkout);
        mcustomerId.setText(id);
        //Button click listener
        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogR logr=new LogR();
                logr.payBill(mcustomerId.getText().toString().trim(), "abc", "def", "ghi", "jkl", "mno", "bng", "ka", "1234", "india", "https://colourssoftware.com:5443/customer/createshipping", new LogR.AsyncResponse() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(BillingActivity.this, result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

            }
        });
    }
}
