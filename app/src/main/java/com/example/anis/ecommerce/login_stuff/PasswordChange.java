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

public class PasswordChange extends AppCompatActivity {

    Activity mActivity ;
    Context mContext;
    FloatingActionButton btnSucc;
    TextInputEditText fPw, sPw;
    ProgressDialog pd;
    String lin="loginstuff/forgotpw2.php";

    String enterPass, reEnterPass , userEmail;
    int passlen, rePasslen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        fPw = (TextInputEditText) findViewById(R.id.enterPassword1);
        sPw = (TextInputEditText) findViewById(R.id.reenterPass);
        btnSucc = (FloatingActionButton) findViewById(R.id.successs);
        mActivity = PasswordChange.this;
        mContext = getApplicationContext();

        pd = new ProgressDialog(this);
        userEmail = getIntent().getStringExtra("email");

        btnSucc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPass = fPw.getText().toString();
                reEnterPass = sPw.getText().toString();

                passlen = enterPass.trim().length();
                rePasslen = reEnterPass.trim().length();

                if (passlen == 0 || rePasslen == 0){
                    if (passlen == 0 ){
                        fPw.setError("Enter Passowrd");
                    }
                    if (rePasslen == 0) {
                        sPw.setError("Re Enter Password");
                    }
                }
                if (passlen != 0 && rePasslen != 0){
                    pd.setTitle("Loading");
                    pd.setMessage("Please Wait.....");

                    pd.show();
                    resetPassword();
                }
            }
        });
    }

    public void resetPassword(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("updated")) {

                                pd.dismiss();

                                Toast.makeText(mActivity,"Password Changed", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(mActivity, LoginActivty.class);
                                startActivity(in);

                            }
                            else if (jsonObject.names().get(0).equals("error"))
                            {
                                pd.dismiss();
                                fPw.setError("password doesn't match.");

                                sPw.setError("password doesn't match.");
                                //         Toast.makeText(LoginActivty.this, "LOgin fail", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(mActivity, "error updating password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            pd.dismiss();
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.resetLayout),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.resetLayout),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params = new HashMap<>();
                params.put("email",userEmail);
                params.put("password" , enterPass);
                params.put("repassword" , reEnterPass);
                return params;
            }
        };
        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);

    }
}
