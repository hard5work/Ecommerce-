package com.example.anis.ecommerce.image_view;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.FavoriteClassAdapter;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.services.MyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FavoritesProduct extends AppCompatActivity {
   RequestQueue requestQueue;
   Toolbar tb;
    RecyclerView rv;
    List<Product> productList;
    SessionManager sm;
    String ider;
    BroadcastReceiver myBroadcastReceiver;
    MyService myService;
    FavoriteClassAdapter myAdapter;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

     //   startService(new Intent(this,MyService.class));
        rv = findViewById(R.id.favRecycler);
        tb = findViewById(R.id.favToolbar);

        setSupportActionBar(tb);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rv.setHasFixedSize(true);
        GridLayoutManager myGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv.setLayoutManager(myGridLayoutManager);
        sm = new SessionManager(this);
        HashMap<String, String> user = sm.getUserDetails();
        ider =user.get(SessionManager.KEY_USERID);
        Toast.makeText(this, ider, Toast.LENGTH_SHORT).show();


        productList = new ArrayList<>();

     //   myAdapter = new FavoriteClassAdapter(getApplicationContext(),productList);
       // rv.setAdapter(myAdapter);
      //  stopService(new Intent(FavoritesProduct.this,MyService.class));


            imageLoads();


    }

    @Override
    protected void onResume() {

     //   imageLoads();

        /*myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               Intent i = new Intent(FavoritesProduct.this,FavoritesProduct.class);
                Toast.makeText(context, "Page refreshed", Toast.LENGTH_SHORT).show();
         //       String test = intent.getStringExtra("fav");
            //    rv.setAdapter(myAdapter);
                Toast.makeText(context, "RESULT " , Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();

            }
        };

        registerReceiver(myBroadcastReceiver,new IntentFilter("fav"));*/
     //   rv.setAdapter(myAdapter);
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        stopService(new Intent(FavoritesProduct.this,MyService.class));
        super.onDestroy();
    }

    @Override
    protected void onStop() {

//      unregisterReceiver(myBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (myBroadcastReceiver != null){
            unregisterReceiver(myBroadcastReceiver);

        }
        super.onPause();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void imageLoads(){
        String lin = "favlist/favlist.php";
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
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
                        product.setPrice(obj1.getDouble("price"));
                        product.setDesc(obj1.getString("longdesc"));
                        productList.add(product);
                    }
                    FavoriteClassAdapter adapter = new FavoriteClassAdapter(getApplicationContext() ,productList);
                    adapter.notifyDataSetChanged();
                    rv.setAdapter(adapter);



                } catch (JSONException e) {
                    Toast.makeText(FavoritesProduct.this, "catch " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.favRelativeLayour),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",ider);
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onBackPressed() {
        finish();
    }*/
}
