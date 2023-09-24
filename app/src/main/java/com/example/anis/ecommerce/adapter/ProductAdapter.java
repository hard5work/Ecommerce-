package com.example.anis.ecommerce.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.image_view.FullImageActivity;
import com.example.anis.ecommerce.login_stuff.LoginActivty;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.login_stuff.VolleySingleton;

//import junit.framework.Test;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * RecyclerView.Adapter
 * RecyclerView.viewHolder
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    List<Product> productList ,favlist ,favList;
    SessionManager sm;
    int count =1;
    ProductViewHolder mholder;
    RequestQueue requestQueue;
    String ider;
    Animation fadein,zoom ,slide_right;
    String value1 = "1";
    String favv, cartid;



    // String lin = "test2.php";


    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_row_item,parent ,false);
        mholder = new ProductViewHolder(view);
        return mholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
      //  productList.clear();
        fadein = AnimationUtils.loadAnimation(mCtx,R.anim.fade_in);
        zoom = AnimationUtils.loadAnimation(mCtx,R.anim.zoom_in);
        slide_right = AnimationUtils.loadAnimation(mCtx,R.anim.slide_right);
     //   YoYo.with(Techniques.ZoomIn).playOn(holder.cardView);//animations
        holder.cardView.setAnimation(slide_right);
        final int pos = holder.getAdapterPosition();

        final Product product = productList.get(pos);
        sm = new SessionManager(mCtx);

        HashMap<String, String> userid = sm.getUserDetails();
        ider = userid.get(SessionManager.KEY_USERID);

        String rs = "Rs " + String.valueOf(product.getPrice());


            holder.textViewTitle.setText(product.getTitle());
         holder.textViewPrice.setText(rs);






       Glide.with(mCtx).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.imageView);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String lin = "categories/sendimage.php";

                requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("error ", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);


                                    if (jsonObject.names().get(0).equals("available")) {
                                        try {
                                       //    Toast.makeText(mCtx, "Id IN INTENT = " + String.valueOf(product.getId()), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(mCtx, FullImageActivity.class);
                                            intent.putExtra("id",String.valueOf(product.getId()));
                                            intent.putExtra("name",product.getTitle());
                                            intent.putExtra("image",product.getAllImage());
                                            intent.putExtra("desc", product.getDesc());
                                            intent.putExtra("price" ,String.valueOf(product.getPrice()));

                                            mCtx.startActivity(intent);
                                        }catch (Exception e){
                                            e.getMessage();
                                        }
                                    }



                                    else if (jsonObject.names().get(0).equals("error"))
                                    {
                                        Toast.makeText(mCtx, "error", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(mCtx, "no data found", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(mCtx, "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, " data error : "+ error, Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id",String.valueOf(product.getId()));
                        return params;
                    }
                };


                VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
            }

        });


        String favvv = product.getFav();
        String cartt = product.getCart();

        if (favvv != null)
            {
                if (favvv.matches("1")) {

                    holder.unfav.setVisibility(View.VISIBLE);
                    holder.fav.setVisibility(View.GONE);
                }
                else {
                    holder.unfav.setVisibility(View.GONE);
                    holder.fav.setVisibility(View.VISIBLE);

                }

        }
        if (favvv == null){
            holder.unfav.setVisibility(View.GONE);
            holder.fav.setVisibility(View.VISIBLE);

        }
        if (cartt != null) {
            if (cartt.matches("1")) {
                holder.btnaddedtocart.setVisibility(View.VISIBLE);
                holder.btnAddToCart.setVisibility(View.GONE);
            }
            else{
                holder.btnaddedtocart.setVisibility(View.GONE);
                holder.btnAddToCart.setVisibility(View.VISIBLE);

            }


        }

        if (cartt == null){
            holder.btnaddedtocart.setVisibility(View.GONE);
            holder.btnAddToCart.setVisibility(View.VISIBLE);

        }

        HashMap<String, String> user = sm.getUserDetails();
        String test = user.get(SessionManager.KEY_FAVORITE);
        String prid = user.get(SessionManager.KEY_ID);
        String testing = user.get(SessionManager.FAVORITES);



        //check for favorite items or not

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                Toast.makeText(mCtx, "click" + position, Toast.LENGTH_SHORT).show();

                if(sm.isLoggedIn()) {


                    String lin = "favlist/favlistlogin.php";

                    requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("success")) {
                                            try {
                                              //      Toast.makeText(mCtx, "succes respse" + response, Toast.LENGTH_SHORT).show();
                                                    holder.unfav.setVisibility(View.VISIBLE);
                                                    holder.fav.setVisibility(View.GONE);

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        }

                                        else {
                                            Toast.makeText(mCtx, "no data found" + response, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(mCtx, "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mCtx, " data error : " + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("favorite", "1");
                          params.put("userid",ider);
                            params.put("productid", String.valueOf(product.getId()));
                            return params;
                        }
                    };


                    requestQueue.add(stringRequest);
                   // VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
                }
                else {
                    Intent login =  new Intent(mCtx, LoginActivty.class);
                    mCtx.startActivity(login);
                }

            }
        });

        holder.unfav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
              //  Toast.makeText(mCtx, "unvisible click" + position, Toast.LENGTH_SHORT).show();

                if(sm.isLoggedIn()) {
                    HashMap<String, String> userid = sm.getUserDetails();
                    ider = userid.get(SessionManager.KEY_USERID);
                    Toast.makeText(mCtx,"user id" + ider, Toast.LENGTH_SHORT).show();
                    Toast.makeText(mCtx, "product id" + product.getId(), Toast.LENGTH_SHORT).show();


                    String lin = "favlist/favlistnotlogin.php";

                    requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("success")) {
                                            try {
                                           //     Toast.makeText(mCtx, "succes respse" + response, Toast.LENGTH_SHORT).show();
                                                holder.fav.setVisibility(View.VISIBLE);
                                                holder.unfav.setVisibility(View.GONE);


                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        }

                                        else {
                                            Toast.makeText(mCtx, "no data found" + response, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(mCtx, "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mCtx, " data error : " + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("favorite", "1");
                            params.put("userid",ider);
                            params.put("productid", String.valueOf(product.getId()));
                            return params;
                        }
                    };


                    requestQueue.add(stringRequest);
                    // VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
                }
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Item CLicked on Postion " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                if (sm.isLoggedIn()) {
                    String lin = "categories/addtocart.php";

                    requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    Toast.makeText(mCtx, "resp " + response, Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("added")) {
                                            try {

                                                holder.btnAddToCart.setVisibility(View.GONE);
                                                holder.btnaddedtocart.setVisibility(View.VISIBLE);
                                                //  Toast.makeText(mCtx, "added to cart" + response, Toast.LENGTH_SHORT).show();

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } else {
                                            Toast.makeText(mCtx, "no data found", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(mCtx, "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mCtx, " data error : " + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("productid", String.valueOf(product.getId()));
                            params.put("price",String.valueOf(product.getPrice()));
                            params.put("userid",ider);
                            return params;
                        }
                    };


                    VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
                }
                else
                {
                    Intent intent = new Intent(mCtx, LoginActivty.class);
                    mCtx.startActivity(intent);
                }
            }



        });




    }





    @Override
    public int getItemCount() {
        return productList.size(); //itemcount is equal to productsize. To return the count of item in the product
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle,textViewPrice;
        CardView cardView;
        Button btnAddToCart , btnaddedtocart;
        TextView fav,unfav;

        public ProductViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            btnAddToCart=itemView.findViewById(R.id.addTocart);
            unfav = itemView.findViewById(R.id.listfav);
            fav = itemView.findViewById(R.id.listunfav);
            btnaddedtocart = itemView.findViewById(R.id.addedTocart);
        }
    }




    }




