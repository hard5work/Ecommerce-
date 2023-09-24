package com.example.anis.ecommerce.image_view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.anis.ecommerce.adapter.SliderViewPager;
import com.example.anis.ecommerce.adapter.ViewPagerAnimation;
import com.example.anis.ecommerce.login_stuff.LoginActivty;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.login_stuff.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FullImageActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotCount;
    private ImageView[] dots;
    SliderViewPager viewPagerAdapter;
    Button btnadd, btnadded;
    Spinner spinnerColor, spinnerSize;
    Toolbar mToolbar;
    ImageView mFlView;
    TextView mTtiView;
      RecyclerView imageSLider;
 //   AdapterViewFlipper imageSLider1;
    Product mpro;
    List<Product> productList, favList;
    String getid, image, title, desc, price;
    TextView fav, unfav, pricee;
    SessionManager sm;
    String ide, favv, productid, cartid;
    Intent intent;
    List<String> colorList, sizeList;

    String favlist;
    String request_url = "fulldetail/viewProductPic.php";
    String lin = "categories/getdetailmen.php";
    String favorite = "favlist/updatefav.php";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        mToolbar = (Toolbar) findViewById(R.id.fToolbar);
        mTtiView = (TextView) findViewById(R.id.tvTitle);
        // mFlView = (ImageView) findViewById(R.id.full_image_view);
        unfav = findViewById(R.id.favorite);
        fav = findViewById(R.id.clickfav);
        btnadd = findViewById(R.id.fulladdtocart);
        btnadded = findViewById(R.id.fulladdedtocart);
        pricee = (TextView) findViewById(R.id.priceamount);
        viewPager = (ViewPager) findViewById(R.id.fullimageViewpager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.sldierdot);

         //imageSLider = (RecyclerView) findViewById(R.id.recyclerViewslider);
     //   imageSLider1 = (AdapterViewFlipper) findViewById(R.id.recyclerViewslider);

        spinnerColor = (Spinner) findViewById(R.id.colorSPinner);
        spinnerSize = (Spinner) findViewById(R.id.sizeSpinner);

        ViewPagerAnimation viewPagerAnimation = new ViewPagerAnimation();

     //   intent = getIntent();
     //   String transformation = intent.getStringExtra("transformation");

     //   switch (transformation)
     //   {
          //  case "transformation"://
          ///      break;

       // }



        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        productList = new ArrayList<>();

        sizeList = new ArrayList<>();
        colorList = new ArrayList<>();

        getid = Objects.requireNonNull(getIntent().getExtras()).getString("id");
        title = getIntent().getExtras().getString("name");
        image = getIntent().getExtras().getString("image");
        desc = getIntent().getExtras().getString("desc");
        price = getIntent().getExtras().getString("price");
        //      favlist = getIntent().getExtras().getString("favorite");
        setTitle(title);
        //mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mTtiView.setText(desc);
        String prce = "Rs " + price;
        pricee.setText(prce);

         /*    imageSLider.setLayoutManager(new LinearLayoutManager(
                      getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false));*/

        // Glide.with(this).load(InternetUrl.ServiceTYpe.URL + image).into(mFlView);


        sm = new SessionManager(this);
        HashMap<String, String> userid = sm.getUserDetails();
        ide = userid.get(SessionManager.KEY_USERID);

        logedimageLoad();


        if (favv == null) {

            unfav.setVisibility(View.GONE);
            fav.setVisibility(View.VISIBLE);
        }

        getBtn();
        imageLoads();


        if (cartid == null) {
            btnadded.setVisibility(View.GONE);
            btnadd.setVisibility(View.VISIBLE);

        }

        setSizeList();
        setColorList();

    /*   spinnerSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Snackbar.make(view,value,Snackbar.LENGTH_LONG).show();
            }

        });*/

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = spinnerSize.getItemAtPosition(spinnerSize.getSelectedItemPosition()).toString();
                ((TextView) spinnerSize.getChildAt(0)).setGravity(Gravity.CENTER);
                Toast.makeText(FullImageActivity.this, "size " + value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = spinnerColor.getItemAtPosition(spinnerColor.getSelectedItemPosition()).toString();
                ((TextView) spinnerColor.getChildAt(0)).setGravity(Gravity.CENTER);

                Toast.makeText(FullImageActivity.this, "color " + value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //view pager listener

        viewPager.setPageTransformer(true,viewPagerAnimation);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0 ; i< dotCount ; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask() , 1400,4000);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sm.isLoggedIn()) {
                    String lin = "cartliststuff/addtocart.php";

                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    Toast.makeText(getApplicationContext(), "resp " + response, Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("added")) {
                                            try {

                                                btnadd.setVisibility(View.GONE);
                                                btnadded.setVisibility(View.VISIBLE);
                                                //  Toast.makeText(mCtx, "added to cart" + response, Toast.LENGTH_SHORT).show();

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } else if (jsonObject.names().get(0).equals("error")) {
                                            Toast.makeText(getApplicationContext(), "error" + response, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), " data error : " + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("productid", String.valueOf(getid));
                            params.put("price", String.valueOf(price));
                            params.put("userid", ide);
                            return params;
                        }
                    };


                    VolleySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                } else {
                    Intent intent = new Intent(FullImageActivity.this, LoginActivty.class);
                    startActivity(intent);
                }
            }
        });


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sm.isLoggedIn()) {


                    String lin = "favlist/favlistlogin.php";

                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("success")) {
                                            try {
                                                Toast.makeText(getApplicationContext(), "succes respse" + response, Toast.LENGTH_SHORT).show();
                                                unfav.setVisibility(View.VISIBLE);
                                                fav.setVisibility(View.GONE);

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } else if (jsonObject.names().get(0).equals("exists")) {
                                            Toast.makeText(getApplication(), "exits response" + response, Toast.LENGTH_SHORT).show();
                                        } else if (jsonObject.names().get(0).equals("error")) {
                                            Toast.makeText(getApplicationContext(), "error response" + response, Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "no data found" + response, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.fullimageview), "NO INternet coonection", Snackbar.LENGTH_LONG);
                            snackbar.show();


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("favorite", "1");
                            params.put("userid", ide);
                            params.put("productid", String.valueOf(getid));
                            return params;
                        }
                    };


                    requestQueue.add(stringRequest);
                } else {
                    Intent login = new Intent(FullImageActivity.this, LoginActivty.class);
                    startActivity(login);
                }

            }
        });

        unfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sm.isLoggedIn()) {


                    String lin = "favlist/favlistnotlogin.php";

                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);


                                        if (jsonObject.names().get(0).equals("success")) {
                                            try {
                                                fav.setVisibility(View.VISIBLE);
                                                unfav.setVisibility(View.GONE);


                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "CATCH ERROR :" + e, Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.fullimageview), "NO INternet coonection", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("favorite", "1");
                            params.put("userid", ide);
                            params.put("productid", String.valueOf(getid));
                            return params;
                        }
                    };


                    requestQueue.add(stringRequest);
                }
            }
        });


    }
    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            FullImageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   int test =  dotCount;

                    int timers = viewPager.getCurrentItem();


                    if (viewPager.getCurrentItem() == (dotCount-1)) {
                     //   Toast.makeText(FullImageActivity.this, "timer == dot " + timers, Toast.LENGTH_SHORT).show();
                        timers = -1;
                        viewPager.setCurrentItem(0);
                    }
                    if (timers < test) {
                      //  Toast.makeText(FullImageActivity.this, "timer < dot " + (timers + 1), Toast.LENGTH_SHORT).show();
                        timers++;
                        viewPager.setCurrentItem(timers);
                    }



/*
                  if (viewPager.getCurrentItem() == 0){
                      viewPager.setCurrentItem(1);
                  } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }
                    else
                    {
                        viewPager.setCurrentItem(0);
                    }*/

                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void imageLoads() {
        String lint = "fulldetail/viewProductPic.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + lint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray array1 = new JSONArray(response);

                            for (int i = 0; i < array1.length(); i++) {

                                JSONObject obj1 = array1.getJSONObject(i);
                                Product product = new Product();
                                product.setAllImage(obj1.getString("image"));
                                productList.add(product);
                            }

                            viewPagerAdapter = new SliderViewPager(getApplicationContext() , productList);
                            viewPager.setAdapter(viewPagerAdapter);
                            dotCount = viewPagerAdapter.getCount();
                            dots = new ImageView[dotCount];

                            if (!productList.isEmpty()) {
                                for (int i = 0; i < dotCount; i++) {
                                    dots[i] = new ImageView(FullImageActivity.this);
                                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(8, 0, 8, 0);
                                    sliderDotspanel.addView(dots[i], params);

                                }

                                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                            }

                         //   Toast.makeText(FullImageActivity.this, "res" + productList.size(), Toast.LENGTH_LONG).show();

                         //   imageSLider.setAdapter(new FullImageAdapter(getApplicationContext(),productList));


                          //  CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), productList);
                          //  imageSLider1.setAdapter(customAdapter);
                          //  imageSLider1.setFlipInterval(2000);
                         //   imageSLider1.setAutoStart(true);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(Objects.requireNonNull(findViewById(R.id.fullimageview)), "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productid", getid);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logedimageLoad() {
        HashMap<String, String> userid = sm.getUserDetails();
        ide = userid.get(SessionManager.KEY_USERID);
        favList = new ArrayList<>();
        String lint = "favlist/favoritelist.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.<Context>requireNonNull(this));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {

                        JSONObject obj1 = array1.getJSONObject(i);
                        Product product = new Product();
                        product.setAllImage(obj1.getString("image"));
                        productList.add(product);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
             /*   if(Alerter.isShowing()){
                    Alerter.hide();
                }

                Alerter.create(FullImageActivity.this)
                        .setTitle("No favlist")
                        .setText("favorite list not available")
                        .setBackgroundColorRes(R.color.colorPrimaryDark)
                        .setDuration(2000)
                        .enableSwipeToDismiss()
                        .enableIconPulse(true)
                        .show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", ide);
                params.put("productid", getid);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getBtn() {

        HashMap<String, String> userid = sm.getUserDetails();
        ide = userid.get(SessionManager.KEY_USERID);

        String lint = "cartliststuff/cartlist.php";
        RequestQueue requestQueue = Volley.newRequestQueue((getApplicationContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj1 = new JSONObject(response);
                    cartid = obj1.getString("id");
                    //    Toast.makeText(FullImageActivity.this, " cart "+cartid, Toast.LENGTH_SHORT).show();
                    /* if(cartid != null){*/
                    btnadded.setVisibility(View.VISIBLE);
                    btnadd.setVisibility(View.GONE);
                    //}


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                  /*  if(Alerter.isShowing()){
                        Alerter.hide();
                    }

                    Alerter.create((Activity) mCtx)
                            .setTitle("No favlist")
                            .setText("favorite list not available")
                            .setBackgroundColorRes(R.color.colorPrimaryDark)
                            .setDuration(2000)
                            .enableSwipeToDismiss()
                            .enableIconPulse(true)
                            .show();*/
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", ide);
                params.put("productid", getid);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setSizeList() {




        String lint = "fulldetail/viewProductSize.php";
        RequestQueue requestQueue = Volley.newRequestQueue((getApplicationContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {

                        JSONObject obj1 = array1.getJSONObject(i);
                        String product;
                        product =obj1.getString("size");
                        sizeList.add(product);
                    }
                    ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            sizeList);

                    spinnerSize.setAdapter(spinAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productid", getid);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setColorList() {


        String lint = "fulldetail/viewProductColor.php";
        RequestQueue requestQueue = Volley.newRequestQueue((getApplicationContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + lint,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {

                        JSONObject obj1 = array1.getJSONObject(i);
                        String product;
                        product =obj1.getString("color");
                        colorList.add(product);
                    }
                    ArrayAdapter<String> colorSpin = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            colorList);

                    spinnerColor.setAdapter(colorSpin);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productid", getid);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

/*

    class FullImageAdapter extends RecyclerView.Adapter<FullImageAdapter.FullImageViewAdapter> {
            private Context mCtx;
            private List<Product> productList;
            SessionManager sm;
            RequestQueue requestQueue;
            Animation fadein,zoom;
            String test;
            Product product2;
            String id,favorite,ider;

            public FullImageAdapter(Context mCtx, List<Product> productList) {
                this.mCtx = mCtx;
                this.productList = productList;
            }

            @NonNull
            @Override
            public FullImageViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View view = inflater.inflate(R.layout.full_image_adpater,null);
                FullImageViewAdapter mholder = new FullImageViewAdapter(view);
                return mholder;
            }

            @Override
            public void onBindViewHolder(@NonNull final FullImageViewAdapter holder, int position) {
                final int pos = holder.getAdapterPosition();

                final Product product = productList.get(pos);
              //  holder.fcardView.setAnimation(zoom);
                Glide.with(mCtx).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.imageView);
            }

            @Override
            public int getItemCount() {
                return productList.size();
            }

            public class FullImageViewAdapter extends RecyclerView.ViewHolder {
                CardView fcardView;
                ImageView imageView;
                public FullImageViewAdapter(View itemView) {
                    super(itemView);
                  //     fcardView = itemView.findViewById(R.id.adapterCard);
                    imageView = itemView.findViewById(R.id.imageSlider2);
                }    }
            }

*/

/*
    class CustomAdapter extends BaseAdapter {
        Context context;
        int[] images;
        List<Product> productList;
        LayoutInflater inflater;

        public CustomAdapter(Context context, List<Product> productList) {
            this.context = context;
            this.productList = productList;
            inflater = (LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.full_image_adpater, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageSlider2);

            Product product = productList.get(i);
            // imageView.setImageResource(images[i]);

            Glide.with(context).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(imageView);


            return view;
        }
    }*/

}

