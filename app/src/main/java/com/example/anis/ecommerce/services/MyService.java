package com.example.anis.ecommerce.services;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.adapter.FavoriteClassAdapter;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.image_view.FavoritesProduct;
import com.example.anis.ecommerce.login_stuff.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyService extends Service {
    List<Product> productList;
    SessionManager sm;
    String ider;
    FavoriteClassAdapter adapter;

    public MyService() {
    }

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(10000);
                        Log.i("services-log","fetching Staus");
                        imageLoads();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void imageLoads(){
        sm = new SessionManager(this);
        HashMap<String, String> user = sm.getUserDetails();
        ider =user.get(SessionManager.KEY_USERID);
        productList = new ArrayList<>();
//        Toast.makeText(this, "from service" + ider, Toast.LENGTH_SHORT).show();

        String lin = "favlist.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
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
                        sendBroadcast(new Intent("fav"));
                    }

                   // rv.setAdapter(new FavoriteClassAdapter(getApplicationContext(), productList));



                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "catch " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fav error : " + error, Toast.LENGTH_LONG).show();

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
}
