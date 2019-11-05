package com.example.anis.ecommerce.kidclothing;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.adapter.ProductAdapter;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KidTshirtCat extends Fragment {
    RecyclerView review;
    Product product;
    List<Product> tagProductList;
    String lin = "kidcategory/getktshirt.php";
    SwipeRefreshLayout swipe;
    TextView textView;
    SessionManager sm;
    String ide;
    List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cat_act_view, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tagProductList = new ArrayList<>();
        textView = view.findViewById(R.id.textmsg);

        review = view.findViewById(R.id.tshirtrecycler);
        swipe = view.findViewById(R.id.tshirtswipe);
        review.setLayoutManager(new GridLayoutManager(getContext() , 3));
        review.hasFixedSize();
        sm = new SessionManager(Objects.requireNonNull(getContext()));
        HashMap<String, String> user= sm.getUserDetails();
        ide= user.get(SessionManager.KEY_USERID);
        if (sm.isLoggedIn()){
            logedimageLoad();
        }
        else
            imageLoads();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                if (sm.isLoggedIn()){
                    productList.clear();
                    logedimageLoad();
                }
                else {
                    tagProductList.clear();
                    imageLoads();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Refresh Finished", Toast.LENGTH_SHORT).show();

                        swipe.setRefreshing(false);
                    }
                }, 2000);

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logedimageLoad(){

        productList = new ArrayList<>();

        String lint = "kidcategory/getktshirtlogin.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.<Context>requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
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
                        product.setFav(obj1.getString("favorite"));
                        product.setCart(obj1.getString("cart"));
                        productList.add(product);
                    }

                    ProductAdapter adapter =  new ProductAdapter(getContext(),productList);
                    adapter.notifyDataSetChanged();
                    review.setAdapter(adapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(Alerter.isShowing()){
                    Alerter.hide();
                }

                Alerter.create(getActivity())
                        .setTitle("ERRORR")
                        .setText("Internet Error")
                        .setBackgroundColorRes(R.color.colorPrimaryDark)
                        .setDuration(2000)
                        .enableSwipeToDismiss()
                        .enableIconPulse(true)
                        .show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",ide);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void imageLoads(){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
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
                        tagProductList.add(product);
                    }



                    if (tagProductList == null){
                        textView.setVisibility(View.VISIBLE);
                        review.setVisibility(View.GONE);
                    }
                    else if (tagProductList.isEmpty()){
                        textView.setVisibility(View.VISIBLE);
                        review.setVisibility(View.GONE);
                    }
                    else {
                        textView.setVisibility(View.GONE);
                        review.setVisibility(View.VISIBLE);

                        review.setAdapter(new ProductAdapter(getContext(), tagProductList));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(review,"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        requestQueue.add(stringRequest);


    }
}


