package com.example.anis.ecommerce.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.cart_list.CartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonContainerForALl {
 static  List<Product> productList;
  static   RequestQueue requestQueue;

    public  List<Product> getMenWear(final Context mctx){

        productList = new ArrayList<>();

        String lin= "categories/getmenwear.php";
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mctx));
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






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mctx, "error " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

        return productList;
    }




    public void cartActivityAdapter(final Context mCtx) {

        final String lin = "getcartlist.php";
        final RecyclerView recyclerView;
        final CartAdapter adapter;
        final List<Product> productList;

        productList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.home + lin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array1 = new JSONArray(response);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject obj1 = array1.getJSONObject(i);
                        Product product = new Product();
                        product.setTitle(obj1.getString("name"));
                        product.setAllImage(obj1.getString("image"));
                        product.setPrice(obj1.getDouble("price"));
                        product.setQnty(obj1.getInt("qnty"));
                        product.setTotal(obj1.getDouble("amount"));
                        productList.add(product);

                    }

                   // recyclerView.setAdapter(new CartAdapter(mCtx, productList));


                } catch (JSONException e) {
                    Toast.makeText(mCtx, "catch error" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx, "error =" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                return map;
            }
        };
        requestQueue.add(stringRequest);


    }
}
