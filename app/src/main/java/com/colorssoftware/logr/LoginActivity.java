package com.colorssoftware.logr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.colorssoftware.library.LogR;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername,mPassword;
    private Button mLogin;
    private TextView mResponse;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.editText);
        mPassword = (EditText) findViewById(R.id.editText2);
        mResponse = (TextView) findViewById(R.id.textView);
        mLogin = (Button) findViewById(R.id.button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Login using Library
                 */
                LogR l=new LogR();
                LogR.allowAllSSL();
                l.login(mUsername.getText().toString().trim(), mPassword.getText().toString().trim(), "https://colourssoftware.com:5443/customer/login", new LogR.AsyncResponse() {
                    /**
                     * Response coming from server
                     * Use it in your code
                     * @param out
                     */
                    @Override
                    public void onSuccess(String out) {
                        try {
                            if(!out.isEmpty()){
                                JSONObject jsonObject=new JSONObject(out);
                                JSONObject jsonObject1=jsonObject.getJSONObject("customer");
                                id=jsonObject1.getString("_id");
                                String username=jsonObject1.getString("username");
                                String email=jsonObject1.getString("email");
                                mResponse.setText("Customer Id: " + id + "\n"+"Name: " +username +"\n"+"Email: " +email);
                            //Passing customer id to Billing
                            Intent intent=new Intent(LoginActivity.this,BillingActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Response is empty", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onFailure(String fail) {

                    }
                });
            }
        });

    }

}
