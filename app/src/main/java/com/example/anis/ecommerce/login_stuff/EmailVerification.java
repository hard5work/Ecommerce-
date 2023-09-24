package com.example.anis.ecommerce.login_stuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailVerification extends AppCompatActivity {
    TextInputEditText enterEmail;
    Activity mActivity;
    Context mContext;
    FloatingActionButton next;
    String emailAds;
    ProgressDialog pd;
    String lin = "loginstuff/forgotpw.php";
    int emailLen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);

        enterEmail = (TextInputEditText) findViewById(R.id.error1);
        next = (FloatingActionButton) findViewById(R.id.nextact);
        mContext = getApplicationContext();
        pd = new ProgressDialog(this);

        mActivity = EmailVerification.this;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailAds= enterEmail.getText().toString();

                emailLen = emailAds.trim().length();

                if (emailLen == 0)
                {
                    enterEmail.setError("Please Enter Email Address");
                }

                if (emailLen != 0)
                {
                    pd.setTitle("Loading");
                    pd.setMessage("Please Wait.......");

                    pd.show();
                    checkEmail();
                }
            }
        });
    }

    public void checkEmail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String test = enterEmail.getText().toString();
                            if (jsonObject.names().get(0).equals("matched")) {

                                pd.dismiss();

                                Toast.makeText(mActivity,"Email Matched", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(mActivity, PasswordChange.class);
                                in.putExtra("email", test);
                                startActivity(in);

                            }
                            else if (jsonObject.names().get(0).equals("error"))
                            {
                                pd.dismiss();
                                enterEmail.setError("email doesn't match.");
                                //         Toast.makeText(LoginActivty.this, "LOgin fail", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(mActivity, "errrorrr", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            pd.dismiss();
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.enterEmailRelative),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params = new HashMap<>();
                params.put("email",emailAds);
                return params;
            }
        };
        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
