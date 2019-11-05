package com.example.anis.ecommerce.login_stuff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adminpanel.AdminDashboard;
import com.example.anis.ecommerce.adminpanel.UploadMultipleImage;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.R;
import com.tapadoo.alerter.Alert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class LoginActivty extends AppCompatActivity {
    EditText userName, passWord;
    Button btnSign, btnSignUp;
    TextInputLayout error1, error2;
    String username,password,un;
    SessionManager sm;
    Map<String, String> param;
    Map<String , String> params;
    String test2;
    ProgressDialog pd;
    String email;
    String id,getUsername, userimage;
    TextView register,forgotpw;
    Animation bounce;
    CardView cardView;
    RelativeLayout cardRelative;

   RequestQueue requestQueue;

    String lin = "loginstuff/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activty);

        userName = findViewById(R.id.enterUsername);
        passWord = findViewById(R.id.enterPassword);
        forgotpw = findViewById(R.id.forgotpw);
        btnSign = findViewById(R.id.btnSignin);
        register = findViewById(R.id.signupEvent);
        error1 = findViewById(R.id.errorText1);
        error2 = findViewById(R.id.errorText2);
        cardView = findViewById(R.id.login_Card);
        cardRelative = findViewById(R.id.card_relative);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
       // cardView.setAnimation(bounce);
        cardRelative.setAnimation(bounce);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.loginToolbar);
       /* setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        sm = new SessionManager(LoginActivty.this);

       if(sm.isLoggedIn()){
           Intent i = new Intent(this, MainActivity.class);
           startActivity(i);



       }

        HashMap<String, String> user = sm.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        pd = new ProgressDialog(this);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(true);
        pd.setIndeterminate(true);




        btnSign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                username = userName.getText().toString();//email address
                password = passWord.getText().toString();



                int user = username.trim().length();
                 int pass = password.trim().length();

                if (user == 0 || pass == 0) {
                    if (user == 0) {
                        error1.setError("This field needs to be filled");

                        Toast.makeText(LoginActivty.this, "username zero", Toast.LENGTH_SHORT).show();
                    }
                    if (pass == 0) {
                        error2.setError("This field needs to be Filled");

                        Toast.makeText(LoginActivty.this, " pass zero", Toast.LENGTH_SHORT).show();
                    }
                }

                if (user != 0 && pass != 0) {
                    loginAdapter();
                    getId();
                }

            }
        });
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(LoginActivty.this , EmailVerification.class);
               startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // Toast.makeText(LoginActivty.this, "Moving to next Activity for signin up", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivty.this, SignupActivity.class);
                    startActivity(i);
                    finish();


            }


        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loginAdapter(){
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.i("error ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    final String test = userName.getText().toString();

                 if (jsonObject.names().get(0).equals("success")) {
                     pd.dismiss();
                 //    Toast.makeText(LoginActivty.this, "cannot access", Toast.LENGTH_SHORT).show();

                     if (jsonObject.getString("user_type").equals("admin"))
                 {

                     pd.dismiss();
                     AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivty.this);
                     builder.setTitle("Login As");
                     builder.setPositiveButton("Admin", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {

                             sm.createAdminSession(test,"admin");
                             Intent intent = new Intent(LoginActivty.this , AdminDashboard.class);
                             startActivity(intent);
                         }
                     });
                     builder.setNegativeButton("User", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             sm.createLoginSession(test);
                             Toast.makeText(LoginActivty.this,"log in successful", Toast.LENGTH_SHORT).show();

                             Intent in = new Intent(LoginActivty.this, MainActivity.class);
                          //   getId();
                             startActivity(in);

                         }
                     });

                     AlertDialog alertDialog = builder.create();
                     alertDialog.show();


                 }


                 else if (jsonObject.getString("user_type").equals("") || jsonObject.getString("user_type").equals("user")) {

                         pd.dismiss();
                         sm.createLoginSession(test);
                         Toast.makeText(LoginActivty.this,"log in successful", Toast.LENGTH_SHORT).show();
                         getId();
                         Intent in = new Intent(LoginActivty.this, MainActivity.class);
                         startActivity(in);
                     }


                 }


                    else if (jsonObject.names().get(0).equals("error"))
                    {
                       pd.dismiss();
                       error1.setError("email doesn't match");
                        error2.setError("password doesn't match");
               //         Toast.makeText(LoginActivty.this, "LOgin fail", Toast.LENGTH_SHORT).show();

                    }
                    else
                 {
                     pd.dismiss();
                     Toast.makeText(LoginActivty.this, "errrorrr", Toast.LENGTH_SHORT).show();
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
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                 params = new HashMap<>();
                params.put("email",username);
                params.put("password",password);
                return params;
            }
        };
        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getId(){


        String lint = "loginstuff/getid.php";

        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            id = obj1.getString("id");
                            getUsername = obj1.getString("username");
                            userimage = obj1.getString("image");
                            sm.inserid(id);
                            sm.create(getUsername);
                            sm.getUserImage(userimage);

                        } catch (JSONException e) {
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


    }


 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        finish();
    }
}


