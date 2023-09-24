package com.example.anis.ecommerce.category_stuff;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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

public class MenWear extends Fragment {
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    ProductAdapter adapter;
    ProductAdapter adapter3;
    RequestQueue requestQueue;
    String ide;
    List<Product> productListZoom;
    List<Product> productList;
    List<Product> productList2;
    List<Product> productList3 , favList;

    SessionManager sm;
    SwipeRefreshLayout refres;
    ProgressDialog pd;
  //  AsyncTest asyncTest;
    String username1;

    TextView seeMore,dealSeeMore,recentSeeMore;

    String lin= "categories/getmenwear.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.men_wear_activity, container, false);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();
        productList3 = new ArrayList<>();
        favList = new ArrayList<>();
     //   productListZoom = new ArrayList<>();

     /*   next = (TextView) view.findViewById(R.id.dealNext);
        prev = (TextView) view.findViewById(R.id.dealPrev);*/
        seeMore = (TextView)view.findViewById(R.id.newSeeMore);
        dealSeeMore = (TextView)view.findViewById(R.id.newSeeMore2);
        recentSeeMore = (TextView)view.findViewById(R.id.newSeeMore3);

        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),MensWearActivity.class);
                startActivity(i);
            }
        });
        dealSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MenNewSee.class);
                startActivity(i);
            }
        });

        recentSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MenNewSee.class);
                startActivity(i);
            }
        });

        sm = new SessionManager(getContext());

        Map<String, String> user = sm.getUserDetails();
        username1 = user.get(SessionManager.KEY_EMAIL);
        ide = user.get(SessionManager.KEY_USERID);
        String usernamee = user.get(SessionManager.KEY_USERNAME);
   //    Toast.makeText(getActivity(), "id - " +ide, Toast.LENGTH_SHORT).show();
    //    Toast.makeText(getActivity(), "EMAIL - " +test, Toast.LENGTH_SHORT).show();
    //    Toast.makeText(getActivity(), "username - " +usernamee, Toast.LENGTH_SHORT).show();



        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView2 = (RecyclerView)view.findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView)view.findViewById(R.id.recyclerView3);
       // recyclerViewZoom = (RecyclerView)view.findViewById(R.id.recyclerViewZoom);

       // recyclerViewZoom.setHasFixedSize(true);;
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);

        //recyclerViewZoom.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setMax(2000);
        pd.setCancelable(true);
        pd.setIndeterminate(true);



   //     new AsyncTest().execute();
        if(sm.isLoggedIn()){
            logedimageLoad();
        }
        else {

            imageLoads();
        }
  //    new AsyncTest().execute(InternetUrl.ServiceTYpe.URL+lin);

        refres = view.findViewById(R.id.mswipeContainer);
        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refres.setRefreshing(true);
                productList.clear();
                if(sm.isLoggedIn()){
                    logedimageLoad();
                }
                else {

                    imageLoads();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Refresh Finished", Toast.LENGTH_SHORT).show();

                        refres.setRefreshing(false);
                    }
                },2000);
            }
        });





    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getIds(){


        String lint = "loginstuff/getid.php";

        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject obj1 = new JSONObject(response);
                           String id = obj1.getString("id");
                            String getUsername = obj1.getString("username");
                            String userimage = obj1.getString("image");
                            sm.inserid(id);
                            sm.create(getUsername);
                            sm.getUserImage(userimage);

                        } catch (JSONException e) {
                          e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", username1);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void imageLoads(){
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.show();

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
                try {
                    Snackbar snackbar = Snackbar.make(requireView(), "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }catch (Exception e){
                    Log.e("error", e.toString());
                }
            }
        });
        requestQueue.add(stringRequest);


    }

    @Override
    public void onResume() {
     //   imageLoads();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logedimageLoad(){
        HashMap<String, String> userid = sm.getUserDetails();
        ide = userid.get(SessionManager.KEY_USERID);
        favList =new ArrayList<>();
        String lint = "categories/favNmenFav.php";
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



   /* private class AsyncTest extends AsyncTask<String, Void , JSONObject>{




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

            try {
                URL url = new URL(InternetUrl.ServiceTYpe.URL + lin);
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer sb = new StringBuffer();

                try{
                    while((line =reader.readLine()) != null){
                        sb.append(line).append('\n');
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

             *//*   HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(InternetUrl.ServiceTYpe.URL+lin);
               // HttpPost httppost = new HttpPost(strings[0]);//you can also pass it and get the Url here.

                HttpResponse responce = httpclient.execute(httppost);

                HttpEntity httpEntity = responce.getEntity();

                response = EntityUtils.toString(httpEntity);
                Toast.makeText(getActivity(), "Async response " +response, Toast.LENGTH_SHORT).show();

                Log.d("response is", response);*//*
             try
                    {
                        //   JSONObject jobj = jsonObject.getJSONObject("result");

                        //   String status = jobj.getString("status");

                        //    if(status.equals("true"))
                        //     {
                        JSONArray array = new JSONArray("");

                        for(int x = 0; x < array.length(); x++)
                        {
                            JSONObject obj1 = array.getJSONObject(x);
                            Product product = new Product();
                            product.setId(obj1.getInt("id"));
                            product.setTitle(obj1.getString("name"));
                            product.setAllImage(obj1.getString("image"));
                            product.setPrice(obj1.getDouble("price"));
                            product.setDesc(obj1.getString("longdesc"));
                            productList.add(product);

                        }




                    }
                    //     }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                return new JSONObject(response);




                //return new JSONObject(response);

            } catch (Exception ex) {

                ex.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pd.dismiss();

            recyclerView.setAdapter(new ProductAdapter(getContext(), productList));
     //       recyclerView2.setAdapter(new ProductAdapter(getContext(), productList));
     //       recyclerView3.setAdapter(new ProductAdapter(getContext(), productList));


        }
    }
*/



}
