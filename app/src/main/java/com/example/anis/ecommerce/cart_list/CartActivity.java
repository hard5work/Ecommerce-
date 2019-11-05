package com.example.anis.ecommerce.cart_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.CartAdapter;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.adapter.ProductAdapter;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.login_stuff.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    SessionManager sm;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<Product> productList;
    TextView cadd, cminus, ctotal, overall;
    RequestQueue requestQueue;
    Button checkout;
    int totalqnty = 0;
    double totalAmount = 0;
    Product product;
    String ider;
    String lin = "cartliststuff/viewcartitems.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        productList= new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.cardRecylerview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cartToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        sm =new SessionManager(this);
        HashMap<String,String> user = sm.getUserDetails();
        ider = user.get(SessionManager.KEY_USERID);
      //  Toast.makeText(this, ider , Toast.LENGTH_SHORT).show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar.setTitle("My Cart");

       ctotal = (TextView) findViewById(R.id.totalPrice);
       overall = findViewById(R.id.amountView);
       checkout = findViewById(R.id.checkout);

       ctotal.setText(String.valueOf(totalAmount));
        cartActivityAdapter();

     //   new RecyclerView.Adapter<>().notifyDataSetChanged();






    }

    public void cartActivityAdapter() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array1 = new JSONArray(response);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject obj1 = array1.getJSONObject(i);
                        Product product = new Product();
                        product.setId(obj1.getInt("id"));
                        product.setTitle(obj1.getString("name"));
                        product.setAllImage(obj1.getString("image"));
                        product.setQnty(obj1.getInt("qnty"));
                        product.setDesc(obj1.getString("longdesc"));
                        product.setPrice(obj1.getDouble("price"));
                        product.setTotal(obj1.getDouble("amount"));
                        productList.add(product);
                        totalqnty = totalqnty + obj1.getInt("qnty");

                        totalAmount = totalAmount + obj1.getDouble("amount");

                    }
                    sm.addqnty(String.valueOf(totalqnty));



                    if (!productList.isEmpty()) {

                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new CartAdapter(CartActivity.this, productList));

                        checkout.setVisibility(View.VISIBLE);
                        overall.setVisibility(View.VISIBLE);
                        ctotal.setVisibility(View.VISIBLE);
                        String rs = "Rs " + String.valueOf(totalAmount);
                        ctotal.setText(rs);
                        checkout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               Intent intent = new Intent(CartActivity.this , CheckoutActivity.class);
                               startActivity(intent);
                                  }
                        });
                    }

                    else {

                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.cartRelativeLayout),"No Cart Item",Snackbar.LENGTH_LONG)
                                .setAction("Add Items", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent= new Intent(CartActivity.this ,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        snackbar.setDuration(30000);
                        snackbar.show();


                    }



                } catch (JSONException e) {
                    Toast.makeText(CartActivity.this, "catch error" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.cartRelativeLayout),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("userid",ider);

                return map;
            }};
        requestQueue.add(stringRequest);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}


