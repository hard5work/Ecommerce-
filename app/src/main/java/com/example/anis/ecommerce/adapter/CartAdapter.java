package com.example.anis.ecommerce.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.anis.ecommerce.cart_list.CartActivity;
import com.example.anis.ecommerce.image_view.FullImageActivity;
import com.example.anis.ecommerce.image_view.MenNewSee;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.login_stuff.LoginActivty;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.login_stuff.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
     final  Context mCtx;
    private List<Product> productList;
    String b;
    int count=1;
    double total =1;

    SessionManager sm;
    String ider;
    int counte;
    int counter;
    ProgressDialog pd;

    public CartAdapter(Context mCtx, List<Product> products) {
        this.mCtx = mCtx;
        this.productList = products;

    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cart_items, null);
        CartAdapter.CartViewHolder holder = new CartAdapter.CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.CartViewHolder holder, final int position) {
        final Product product=productList.get(position);


        String rs = "Rs " + String.valueOf(product.getPrice());
        holder.textName.setText(product.getTitle()); //get values from product class
        holder.textCost.setText(rs);
        pd = new ProgressDialog(mCtx);

        sm = new SessionManager(mCtx);
        HashMap<String ,String> user = sm.getUserDetails();
        ider = user.get(SessionManager.KEY_USERID);

        String tos = "Rs " + String.valueOf(product.getTotal());



        Glide.with(mCtx).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.imgView);

      //  holder.ctotal.setText(String.valueOf(product.getTotal()));

   //     holder.textQnty.setText(String.valueOf(count));
        holder.textQnty.setText(String.valueOf(product.getQnty(count)));


        holder.ctotal.setText(tos);
        count = product.getQnty(count);
   //     Toast.makeText(mCtx, "product pos = " + product.getId() +  "qnty = " + count, Toast.LENGTH_SHORT).show();




        holder.addqnty.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                counter = product.getQnty(count);
                          counter++;
                if (sm.isLoggedIn()) {
            /*    JSONObject params = new JSONObject();

                try {
                    params.put("productid", String.valueOf(product.getId()));
                    params.put("price",String.valueOf(product.getPrice()));
                    params.put("userid",ider);
                    params.put("qnty",String.valueOf(counter));
                } catch (JSONException e) {
                    e.printStackTrace();
                }if (params.length() > 0) {
            //        new SendJsonDataToServer().execute(String.valueOf(post_dict));
                    new AsyncAddQnty().execute(String.valueOf(params));

                }*/


                    String lin = "cartliststuff/addqnty.php";

                   RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                               //     Toast.makeText(mCtx, "resp " + response, Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("updated")) {
                                            try {
                                      //          Toast.makeText(mCtx, "qntyy added" + response, Toast.LENGTH_SHORT).show();

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } else if (jsonObject.names().get(0).equals("error")) {
                                     //       Toast.makeText(mCtx, "error" + response, Toast.LENGTH_SHORT).show();
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
                            params.put("qnty",String.valueOf(counter));
                            return params;
                        }
                    };


                    VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);

                    Intent intent = new Intent(mCtx,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mCtx.startActivity(intent);
                   ((Activity)mCtx).finish();




                }
                else
                {
                    Intent intent = new Intent(mCtx, LoginActivty.class);
                    mCtx.startActivity(intent);
                }


            }


        });
        holder.viewcart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String lin = "categories/sendimage.php";

                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

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
                                            intent.putExtra("id", String.valueOf(product.getId()));
                                            intent.putExtra("name", product.getTitle());
                                            intent.putExtra("image", product.getAllImage());
                                            intent.putExtra("desc", product.getDesc());
                                            mCtx.startActivity(intent);
                                        } catch (Exception e) {
                                            e.getMessage();
                                        }
                                    } else if (jsonObject.names().get(0).equals("error")) {
                                        Toast.makeText(mCtx, "error", Toast.LENGTH_SHORT).show();
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
                        params.put("id", String.valueOf(product.getId()));
                        return params;
                    }
                };


                VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);
            }
        });

        holder.deletecart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if(sm.isLoggedIn()) {
                    HashMap<String, String> userid = sm.getUserDetails();
                    ider = userid.get(SessionManager.KEY_USERID);
                    Toast.makeText(mCtx, "user id" + ider, Toast.LENGTH_SHORT).show();
                    Toast.makeText(mCtx, "product id" + product.getId(), Toast.LENGTH_SHORT).show();


                    String lin = "cartliststuff/deletefromcart.php";

                   RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("succ")) {
                                            try {
                                                //    sm.unFav("0");
                                             //   Toast.makeText(mCtx, "succes respse" + response, Toast.LENGTH_SHORT).show();

                                                sm.removeqnty(String.valueOf(product.getQnty(count)));


                                            } catch (Exception e) {
                                                e.getMessage();
                                            }

                                        } else if (jsonObject.names().get(0).equals("exists")) {
                                            Toast.makeText(mCtx, "exits response" + response, Toast.LENGTH_SHORT).show();
                                        } else if (jsonObject.names().get(0).equals("error")) {
                                            Toast.makeText(mCtx, "error response" + response, Toast.LENGTH_SHORT).show();

                                        } else {
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
                            params.put("userid", ider);
                            params.put("productid", String.valueOf(product.getId()));
                            return params;
                        }
                    };


                    requestQueue.add(stringRequest);

                    Intent intent = new Intent(mCtx, CartActivity.class);
                    //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    mCtx.startActivity(intent);
                    ((Activity)mCtx).finish();
                }
            }
        });



        holder.removeqnty.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (count>1) {
                        counte = product.getQnty(count);
                        if (counte >1) {

                            counte--;
                            if (sm.isLoggedIn()) {
                                String lin = "cartliststuff/addqnty.php";

                                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(mCtx));

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("error ", response);
                                                //        Toast.makeText(mCtx, "resp " + response, Toast.LENGTH_SHORT).show();
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);


                                                    if (jsonObject.names().get(0).equals("updated")) {
                                                        try {

                                                            sm.removeqnty(String.valueOf(counte));
                                                            //         Toast.makeText(mCtx, "qntyy added" + response, Toast.LENGTH_SHORT).show();

                                                        } catch (Exception e) {
                                                            e.getMessage();
                                                        }
                                                    } else if (jsonObject.names().get(0).equals("error")) {
                                                        //       Toast.makeText(mCtx, "error" + response, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //       Toast.makeText(mCtx, "no data found", Toast.LENGTH_SHORT).show();
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
                                        params.put("price", String.valueOf(product.getPrice()));
                                        params.put("userid", ider);
                                        params.put("qnty", String.valueOf(counte));
                                        return params;
                                    }
                                };


                                VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);

                                Intent intent = new Intent(mCtx, CartActivity.class);
                              //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                mCtx.startActivity(intent);
                                ((Activity)mCtx).finish();

                            }
                        }
                        else
                        {
                            Toast.makeText(mCtx, "cannot do 0", Toast.LENGTH_SHORT).show();
                        }





                    } else {
                        Intent intent = new Intent(mCtx, LoginActivty.class);
                        mCtx.startActivity(intent);
                    }
                }








        });







    }

    @Override
    public int getItemCount() {
        return productList.size();
    }




    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        Button checkout , deletecart, viewcart;
        TextView textName,textCost, removeqnty, addqnty , textQnty ,ctotal;
        CardView crdView;



        public CartViewHolder(View itemView) {
            super(itemView);
            crdView= itemView.findViewById(R.id.cartcardview);
            imgView=itemView.findViewById(R.id.imageViewCart);
            textName=itemView.findViewById(R.id.itemListName);
            textCost=itemView.findViewById(R.id.itemPrice);
            addqnty=itemView.findViewById(R.id.addButton);
            removeqnty=itemView.findViewById(R.id.minusButton);
            textQnty=itemView.findViewById(R.id.quantityItem);
            ctotal = (TextView) itemView.findViewById(R.id.carttotalPrice);
            deletecart = itemView.findViewById(R.id.cartdelete);
            viewcart = itemView.findViewById(R.id.cartviewImage);

        }
    }
    class AsyncAddQnty extends AsyncTask<String, Void , JSONObject> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Loading AsyncTaskData...");
            pd.setMessage("Please wait.");
            pd.setCancelable(true);
            pd.setIndeterminate(true);
            pd.show();

        }



        @Override
        protected JSONObject doInBackground(String... strings) {
            String response;
            String line;
            String JSONdata = strings[0];

            String lin = "cartliststuff/addqnty.php";

            try {
                URL url = new URL(InternetUrl.ServiceTYpe.URL + lin);
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
                writer.write(JSONdata);
               writer.close();


              //  InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer sb = new StringBuffer();

                try{
                    while((line =reader.readLine()) != null){
                        sb.append(line).append("\n");
                    }
                }catch (Exception e)
                {
                    e.getMessage();
                }finally {
                    try{
                        in.close();
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    response = sb.toString();

                }

                try
                {
                    //   JSONObject jobj = jsonObject.getJSONObject("result");

                    //   String status = jobj.getString("status");

                    //    if(status.equals("true"))


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                return new JSONObject(response);


            } catch (Exception ex) {

                ex.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pd.dismiss();


        }
    }

}
/*

                    String lintt = "viewcartitems.php";
                    RequestQueue requestQueue2 = Volley.newRequestQueue(mCtx);
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lintt, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                         //   Toast.makeText(mCtx, "updated cart + " + response, Toast.LENGTH_SHORT).show();

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

                                    // totalAmount = totalAmount + obj1.getDouble("amount");

                                }
                                //title
                                holder.textName.setText(product.getTitle()); //get values from product class
                                //actual price
                                holder.textCost.setText(String.valueOf(product.getPrice()));
                                //product image
                                Glide.with(mCtx).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.imgView);
                                //product qnty
                                holder.textQnty.setText(String.valueOf(product.getQnty(count)));
                                //product total amount
                                holder.ctotal.setText(String.valueOf(product.getTotal()));






                            } catch (JSONException e) {
                                Toast.makeText(mCtx, "catch error" + e, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mCtx, "error = " + error, Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("userid",ider);

                            return map;
                        }};
                    requestQueue2.add(stringRequest2);

 */
