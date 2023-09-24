package com.example.anis.ecommerce.category_stuff;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.anis.ecommerce.services.NotificationService1;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.anis.ecommerce.SettingActivity;
import com.example.anis.ecommerce.adapter.CustomVolleyRequest;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adminpanel.AdminDashboard;
import com.example.anis.ecommerce.cart_list.CartActivity;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.ViewPagerAdapter;
import com.example.anis.ecommerce.image_view.CategoryActivity;
import com.example.anis.ecommerce.image_view.FavoritesProduct;
import com.example.anis.ecommerce.login_stuff.LoginActivty;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.userprofile.UserProfileActivity;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout tabLayout;
    DrawerLayout drawer;
    ImageLoader imageLoader;
    SessionManager sm;
    TextView navUser, navemail;
    ImageView navImage, navCov;
    LinearLayout navLinear;
    ProgressDialog pd;
    FrameLayout frameLayout;
    private Context mContext;
    Animation fadein;
    TextView itemCountCart;
    String userid, deviceid;
    int mCartItemCount = 0;
    String cartItemcount;
    AdapterViewFlipper adapterViewFlipper;
    int[] IMAGES = {
            R.drawable.ml,
            R.drawable.fa,
            R.drawable.kd
    };


    private String[] pageTitle = {
            "Men's Wear", "Women's Wear", " Kids Wear"
    };

    ViewPager viewPager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobarId);
        drawer = findViewById(R.id.drawerLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapterViewFlapper);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), IMAGES);
        try {
            adapterViewFlipper.setAdapter(customAdapter);
            adapterViewFlipper.setFlipInterval(2000);
            adapterViewFlipper.setAutoStart(true);
        }catch (Exception e){
            Log.e("adv error 1",e.toString());
        }catch (OutOfMemoryError e){
            Log.e("adv out",e.toString());
        }


        pd = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        // drawer.setScrimColor(getResources().getColor(android.R.color.white));
        toggle.syncState();


        sm = new SessionManager(this);
        HashMap<String, String> ider = sm.getUserDetails();
        userid = ider.get(SessionManager.KEY_USERID);
        deviceid = sm.getDeviceid();

        if (sm.isLoggedIn()){
            try{
                Log.e("Service","Started");
                startService(new Intent(MainActivity.this, NotificationService1.class));
//                    BroadcastReceiver noticeRecv = new BroadcastReceiver() {
//                        @Override
//                        public void onReceive(Context context, Intent intent) {
//                            String action = intent.getAction();
//                            assert action != null;
//                            if(action.equals("new.items")){
//                                Log.e("new items", "new Items available");
//                            }
//                        }
//                    };

            }catch (Exception e){
                Log.e("error", e.toString());
            }
        }

        viewPager = (ViewPager) findViewById(R.id.pager);//for displaying the tab layout and fragment
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager.setAdapter(new MyAdapter(fragmentManager));
        }catch (Exception e){
            Log.e("error", e.toString());
            //to manage the fragment and to get support for the fragment
        }
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i])); //displaying the fragment name in tab view
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        NavigationView navigationView = findViewById(R.id.nav_new);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navCov = header.findViewById(R.id.coverImage);
        navemail = header.findViewById(R.id.navemail);
        navUser = header.findViewById(R.id.navid);
        navImage = header.findViewById(R.id.navimageView);
        navImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sm.isLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                    startActivity(intent);
                }

            }
        });

        mContext = getApplicationContext();

        Map<String, String> user = sm.getUserDetails();
        String test = user.get(SessionManager.KEY_EMAIL);
        String test2 = user.get(SessionManager.KEY_USERNAME);
        String userimage = user.get(SessionManager.KEY_USERIMAGE);
        String userType = user.get(SessionManager.USER_TYPE);
        cartItemcount = user.get(SessionManager.KEY_FAVORITE);
        // Toast.makeText(this, userimage, Toast.LENGTH_SHORT).show();
      /*  if(userimage == null){
            Glide.with(getApplicationContext()).load(R.drawable.profile).into(navImage);
        }*/
        if (userimage != null) {
           /* imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
            imageLoader.get(InternetUrl.ServiceTYpe.URL +userimage,ImageLoader.getImageListener(navImage,R.mipmap.ic_launcher, R.drawable.gradient1));
*/

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true);
            Glide.with(getApplicationContext())
                    .load(InternetUrl.ServiceTYpe.URL + userimage)
                    .apply(requestOptions)
                    .apply(RequestOptions.circleCropTransform())
                    .into(navImage);

        }
        if (userimage != null) {
            imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
            imageLoader.get(InternetUrl.ServiceTYpe.URL + userimage, ImageLoader.getImageListener(navCov, R.mipmap.ic_launcher, R.drawable.gradient1));
/*
            Glide.with(getApplicationContext())
                    .load(InternetUrl.ServiceTYpe.URL + userimage)
                    .into(navCov);*/
        }

        if (userType != null) {

            if (sm.userType(userType)) {
                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                startActivity(intent);

            }
        }
        if (sm.isLoggedIn()) {


            navemail.setText(test);
            navUser.setText(test2);
            if (cartItemcount == null) {
                mCartItemCount = 0;
            } else
                mCartItemCount = parseInt(cartItemcount);


        } else {
            String is = "Log in to APP";
            navUser.setText(is);
            mCartItemCount = 0;

        }
        Menu menu = navigationView.getMenu();
        MenuItem nav_signout = menu.findItem(R.id.dsignOut);
        if (sm.isLoggedIn()) {
            nav_signout.setTitle("Signout");
        } else {
            nav_signout.setTitle("Signin");
        }
        if (!isNetworkAvailable()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("NO Internet Connection")
                    .setMessage("PLease Check your COnnection ")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifi.setWifiEnabled(true);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try{
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(menuItem, R.layout.cart_attach);
        frameLayout = (FrameLayout) MenuItemCompat.getActionView(menuItem);
        View actionView = MenuItemCompat.getActionView(menuItem);
        itemCountCart = (TextView) actionView.findViewById(R.id.cart_badge);

        menu.findItem(R.id.cart).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onOptionsItemSelected(menuItem);
            }

            });
        }catch (Exception e){
            Log.e("error", "there is error on attachment");
        }

        setupBadge();
      /*  actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });*/

        return true;
    }

    private void setupBadge() {

        if (itemCountCart != null) {
            if (mCartItemCount == 0) {
                if (itemCountCart.getVisibility() != View.GONE) {
                    itemCountCart.setVisibility(View.GONE);
                }
            } else {
                itemCountCart.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (itemCountCart.getVisibility() != View.VISIBLE) {
                    itemCountCart.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.changeServer) {
            final View server = LayoutInflater.from(MainActivity.this).inflate(R.layout.choose_server, null);
            final EditText serverIP = server.findViewById(R.id.serverIP);
            final Button submit = server.findViewById(R.id.submitServer);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(server);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(true);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String serverIps = serverIP.getText().toString();
                    SessionManager sm = new SessionManager(MainActivity.this);
                    sm.setServerip(serverIps);
                    Toast.makeText(MainActivity.this, "Server Changed", Toast.LENGTH_SHORT).show();
                    Intent ints = new Intent(MainActivity.this, MainActivity.class);
                    ints.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ints);
                    MainActivity.this.finish();
                }
            });

        } else if (item.getItemId() == R.id.action_Setting) {
            if (sm.isLoggedIn()) {
                Intent ints = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(ints);
            } else {
                // item.setTitle("Sign In");
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        } else if (item.getItemId() == R.id.action_signin) {
            if (sm.isLoggedIn()) {
                logoutWeb();
                sm.logOut();
                pd.setTitle("Processing...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
                stopService(new Intent(MainActivity.this, NotificationService1.class));
                Log.e("mdes option", "destroied");
                MainActivity.this.finish();
            } else {
               // item.setTitle("Sign In");
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
    public void logoutWeb(){
        Log.e("user id and deviceid", userid + "  " + deviceid);

        StringRequest sr = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "loginstuff/logout.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error response", error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",userid);
                params.put("deviceid",deviceid);
                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
       // VolleySingleton.getmInstance(this).addToRequestQueue(sr);
    }

    @Override
    protected void onDestroy() {
        if (sm.isLoggedIn()) {
            stopService(new Intent(MainActivity.this, NotificationService1.class));
            Log.e("mdes", "destroied");
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Wanna exit?")
                    .setMessage("DO YOU WANT TO EXIT?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
/*

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
            alertBuilder.setTitle("EXIT?");
            alertBuilder
                    .setMessage("Do you want to EXIT?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
*/

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here
        int id = item.getItemId();
        if (id == R.id.dmenwear) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.dwmenwear) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.dkidwear) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.dcategory) {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.dfav) {
            Intent intent = new Intent(this, FavoritesProduct.class);
            startActivity(intent);

        } else if (id == R.id.myProduct) {
            if (sm.isLoggedIn()) {

                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        } else if (id == R.id.dexitApp) {
            finish();
        } else if (id == R.id.dsignOut) {
            // Toast.makeText(this, "signout", Toast.LENGTH_SHORT).show();
            if (sm.isLoggedIn()) {
                logoutWeb();
                sm.logOut();
                pd.setTitle("Processing...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
                stopService(new Intent(MainActivity.this, NotificationService1.class));
                Log.e("mdes nav", "destroied");
                MainActivity.this.finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}


class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fm) {

        super(fm);
    }

    public Fragment getItem(int position) {
        Fragment fragment = null;
        Log.w("Men", "GetItem is Called");

        //condition when the tab are clicked and classes to be called
        if (position == 0) {
            fragment = new MenWear();
        }
        if (position == 1) {
            fragment = new WomenWear();
        }

        if (position == 2) {
            fragment = new KidsWear();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        //titles for the position of fragments
        if (position == 0) {
            return "Men's Wear";
        }
        if (position == 1) {
            return "Women's Wear";
        }
        if (position == 2) {
            return "Kid's Wear";
        }
        return null;
    }


}

class CustomAdapter extends BaseAdapter {
    Context context;
    int[] images;
    LayoutInflater inflater;

    public CustomAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.image_home_list, viewGroup,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageSlider);
        try {
            imageView.setImageResource(images[i]);
        }catch (Exception e){
            Log.e("error load", e.toString());
        }catch (OutOfMemoryError e){
            Log.e("error out", e.toString());
        }


        return view;
    }


}
