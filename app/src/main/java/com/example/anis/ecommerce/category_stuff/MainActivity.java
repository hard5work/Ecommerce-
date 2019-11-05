package com.example.anis.ecommerce.category_stuff;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.Objects;

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
        adapterViewFlipper.setAdapter(customAdapter);
        adapterViewFlipper.setFlipInterval(2000);
        adapterViewFlipper.setAutoStart(true);


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


        viewPager = (ViewPager) findViewById(R.id.pager);//for displaying the tab layout and fragment

        FragmentManager fragmentManager = getSupportFragmentManager(); //to manage the fragment and to get support for the fragment
        viewPager.setAdapter(new MyAdapter(fragmentManager));

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
            imageLoader.get(InternetUrl.ServiceTYpe.URL +userimage,ImageLoader.getImageListener(navCov,R.mipmap.ic_launcher, R.drawable.gradient1));
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
        } else if (item.getItemId() == R.id.action_Setting) {
            Toast.makeText(this, "Setting commin soon", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_signin) {
            if (sm.isLoggedIn()) {
                sm.logOut();
                pd.setTitle("Processing...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
                MainActivity.this.finish();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
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

        }
        else if(id ==  R.id.myProduct) {
            if (sm.isLoggedIn()) {

                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }
        else if (id == R.id.dexitApp) {
            finish();
        } else if (id == R.id.dsignOut) {
            // Toast.makeText(this, "signout", Toast.LENGTH_SHORT).show();
            if (sm.isLoggedIn()) {
                sm.logOut();
                pd.setTitle("Processing...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.image_home_list, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageSlider);
        imageView.setImageResource(images[i]);


        return view;
    }
}
