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
import com.example.anis.ecommerce.image_view.FavoritesProduct;
import com.example.anis.ecommerce.image_view.FullImageActivity;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.login_stuff.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FavoriteClassAdapter extends RecyclerView.Adapter<FavoriteClassAdapter.FavoriteViewAdapter> {
    private Context mCtx;
    private List<Product> productList;
    SessionManager sm;
    RequestQueue requestQueue;
    Animation fadein,zoom;
    String test;
    Product product2;


    String id,favorite,ider;

    public FavoriteClassAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public FavoriteViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.fav_cart_view,null);
        FavoriteViewAdapter mholder = new FavoriteViewAdapter(view);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewAdapter holder, int position) {
        final int pos = holder.getAdapterPosition();

        final Product product = productList.get(pos);
        sm = new SessionManager(mCtx);
        String rs = "Rs " + String.valueOf(product.getPrice());


        holder.ftextViewTitle.setText(product.getTitle());
        holder.ftextViewPrice.setText(rs);


        zoom = AnimationUtils.loadAnimation(mCtx,R.anim.fade_in);
      //  holder.fcardView.setAnimation(zoom);

        Glide.with(mCtx).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.fimageView);



        holder.fcardView.setOnClickListener(new View.OnClickListener() {
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

        holder.fremovefromfav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "position" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                if(sm.isLoggedIn()) {
                    HashMap<String, String> userid = sm.getUserDetails();
                    ider = userid.get(SessionManager.KEY_USERID);


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
                                             //   sm.unFav("0");
                                                Toast.makeText(mCtx, "succes respse" + response, Toast.LENGTH_SHORT).show();


                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } else if (jsonObject.names().get(0).equals("exists")) {
                                            Toast.makeText(mCtx, "exits response" + response, Toast.LENGTH_SHORT).show();
                                        }else if (jsonObject.names().get(0).equals("error")) {
                                            Toast.makeText(mCtx, "error response" + response, Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(mCtx,FavoritesProduct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         //      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mCtx.startActivity(intent);


            }



        });






    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class FavoriteViewAdapter extends RecyclerView.ViewHolder {

        ImageView fimageView;
        TextView ftextViewTitle,ftextViewPrice;
        CardView fcardView;
        Button fremovefromfav;


        public FavoriteViewAdapter(View itemView) {
            super(itemView);
            fcardView = itemView.findViewById(R.id.fcardView);
            fimageView = itemView.findViewById(R.id.fimageView);
            ftextViewTitle = itemView.findViewById(R.id.ftextViewTitle);
            ftextViewPrice = itemView.findViewById(R.id.ftextViewPrice);
            fremovefromfav = itemView.findViewById(R.id.fremovefromfav);
        }
    }
}
