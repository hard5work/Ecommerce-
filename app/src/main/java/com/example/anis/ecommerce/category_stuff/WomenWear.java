package com.example.anis.ecommerce.category_stuff;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.anis.ecommerce.image_view.MenNewSee;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.adapter.ProductAdapter;
import com.example.anis.ecommerce.R;
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

public class WomenWear extends Fragment {
    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerViewZoom;

    RequestQueue requestQueue;


    List<Product> productList;
    SwipeRefreshLayout swipe;

    ProgressDialog pd;
    SessionManager sm;
    String ide;

    /*
    List<Product> productList2;
    List<Product> productList3;*/

    String lin= "categories/getwomenwear.php";
    String lint = "favlist/logedinwomenwear.php";
    TextView seeMore,next,prev;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.female_wear_activity, container, false);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productList = new ArrayList<>();

        /*
        productList2 = new ArrayList<>();
        productList3 = new ArrayList<>();*/


        seeMore = (TextView) view.findViewById(R.id.newSeeMore);

        pd = new ProgressDialog(getContext());
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), WomenCategoryClass.class);
                startActivity(i);
            }
        });
        sm = new SessionManager(getContext());

        Map<String, String> user = sm.getUserDetails();
        String test = user.get(SessionManager.KEY_EMAIL);
        ide = user.get(SessionManager.KEY_USERID);



        recyclerView = (RecyclerView) view.findViewById(R.id.frecyclerView);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.frecyclerView2);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.frecyclerView3);
        // recyclerViewZoom = (RecyclerView)view.findViewById(R.id.recyclerViewZoom);

        // recyclerViewZoom.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);




        //recyclerViewZoom.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if (sm.isLoggedIn()){
            logedimageLoad();
        }
        else {
            imageLoad();
        }

        swipe = view.findViewById(R.id.fswipeContainer);
       swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                productList.clear();
                if (sm.isLoggedIn()){
                    logedimageLoad();
                }
                else {
                    imageLoad();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Refresh Finished", Toast.LENGTH_SHORT).show();

                        swipe.setRefreshing(false);
                    }
                },2000);
            }
        });
    }


       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
       public void imageLoad(){
            requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
            StringRequest stringRequest = new StringRequest(Request.Method.POST,InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array1 = new JSONArray(response);
                        if(array1.length()<0) {
                            pd.setTitle("Loading...");
                            pd.setMessage("Please wait.");
                            pd.setCancelable(true);
                            pd.setIndeterminate(true);
                            pd.show();
                        }
                        for (int i = 0; i < array1.length(); i++) {
                            pd.dismiss();
                            JSONObject obj1 = array1.getJSONObject(i);
                            Product product = new Product();
                            product.setId(obj1.getInt("id"));
                            product.setTitle(obj1.getString("name"));
                            product.setAllImage(obj1.getString("image"));
                            product.setPrice(obj1.getDouble("price"));
                            product.setDesc(obj1.getString("longdesc"));
                            productList.add(product);
                        }

                        ProductAdapter adapter = new ProductAdapter(getContext() , productList);
                        adapter.notifyDataSetChanged();

                        recyclerView.setAdapter(adapter);
                        recyclerView2.setAdapter(new ProductAdapter(getContext(), productList));
                        recyclerView3.setAdapter(new ProductAdapter(getContext(), productList));




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar snackbar=  Snackbar.make(Objects.requireNonNull(getView()),"No Internet Connection",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            });

            requestQueue.add(stringRequest);


        }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logedimageLoad(){
        HashMap<String, String> userid = sm.getUserDetails();
        ide = userid.get(SessionManager.KEY_USERID);
        String lint = "categories/favNwomenFav.php";
        requestQueue = Volley.newRequestQueue(Objects.<Context>requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {
                        pd.dismiss();
                        JSONObject obj1 = array1.getJSONObject(i);
                        Product product = new Product();
                        product.setId(obj1.getInt("id"));
                        product.setTitle(obj1.getString("name"));
                        product.setAllImage(obj1.getString("image"));
                        product.setPrice(obj1.getDouble("price"));
                        product.setDesc(obj1.getString("longdesc"));
                        product.setFav(obj1.getString("favorite"));
                        product.setCart(obj1.getString("status"));
                        productList.add(product);
                    }

                    ProductAdapter adapter =  new ProductAdapter(getContext(),productList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    recyclerView2.setAdapter(new ProductAdapter(getContext(), productList));
                    recyclerView3.setAdapter(new ProductAdapter(getContext(), productList));




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
                        .setTitle("No favlist")
                        .setText("favorite list not available")
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

    @Override
    public void onResume() {
      //  imageLoad();
        super.onResume();

    }

        /*

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logedimageLoad(){
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject obj1 = array1.getJSONObject(i);
                        FavoriteItems product = new FavoriteItems();
                        product.setProductid(obj1.getInt("productid"));
                        product.setFav(obj1.getString("favorite"));
                        favList.add(product);
                    }

                    recyclerView.setAdapter(new ProductAdapter(favList));
                    recyclerView2.setAdapter(new ProductAdapter(favList));
                    recyclerView3.setAdapter(new ProductAdapter(favList));




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "WomenWearError : " + error, Toast.LENGTH_SHORT).show();
                seeMore.setText(R.string.app_name);

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


    }*/


}
/*

class WomenWearAdapter extends RecyclerView.Adapter<WomenWearAdapter.WomenViewHolder> {
    private Context mCtxx;
    private List<Product> productList;

    public WomenWearAdapter(Context mCtx, List<Product> productList) {
        this.mCtxx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public WomenWearAdapter.WomenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtxx);
        View view = inflater.inflate(R.layout.activity_database_items,null);
        WomenViewHolder holder = new WomenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WomenWearAdapter.WomenViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewTitle.setText(product.getTitles());
        String poplr = "http:/192.168.1.141:8080/ecommerce/";
        String lintend = "http:/192.168.1.174:8080/ecommerce/";
        String home = "http:/192.168.100.143:8080/ecommerce/";
        Glide.with(mCtxx).load(home + product.getImages()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mCtxx,FullImageActivity.class);
                mIntent.putExtra("Image",productList.get(holder.getAdapterPosition()).getImage());
                mCtxx.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class WomenViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;
        TextView textViewTitle;
        public WomenViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.dcardView);
            imageView = itemView.findViewById(R.id.dimageView);

            textViewTitle = itemView.findViewById(R.id.dtextViewTitle);
        }
    }
}*/
