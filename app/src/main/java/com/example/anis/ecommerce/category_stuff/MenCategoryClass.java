package com.example.anis.ecommerce.category_stuff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.cart_list.CartActivity;
import com.example.anis.ecommerce.mensclothing.GlovesCat;
import com.example.anis.ecommerce.mensclothing.HelmetsCat;
import com.example.anis.ecommerce.mensclothing.JacketsCat;
import com.example.anis.ecommerce.mensclothing.MufflersCat;
import com.example.anis.ecommerce.mensclothing.PantCat;
import com.example.anis.ecommerce.mensclothing.ShoesCat;
import com.example.anis.ecommerce.mensclothing.ShortsCat;
import com.example.anis.ecommerce.mensclothing.SuitCat;
import com.example.anis.ecommerce.mensclothing.SweaterCat;
import com.example.anis.ecommerce.mensclothing.TrousersCat;
import com.example.anis.ecommerce.mensclothing.TshirtCat;
import com.example.anis.ecommerce.mensclothing.WatchCat;

import static java.lang.Integer.parseInt;

@SuppressLint("Registered")
public class MenCategoryClass extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    TabLayout tabLayout;
    private String[] pageTitle = {
            "T-Shirt" , "Pant" , "Shorts" , "Suits" , "Shoes"  , "Watch" , "Jackets" , "Sweater" ,
            "Trousers" , "Mufflers" , "Gloves" , "Helmets"
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencategory_collection);
        toolbar = findViewById(R.id.toolbarLayou);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        pager = findViewById(R.id.pager_men);

        FragmentManager fragmentManager = getSupportFragmentManager(); //to manage the fragment and to get support for the fragment
        pager.setAdapter(new MyAdapterCategory(fragmentManager));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_men);

        for (int i = 0; i < 12; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i])); //displaying the fragment name in tab view
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        ViewPagerAdapter1 pagerAdapter = new ViewPagerAdapter1(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        String pos1 = getIntent().getStringExtra("one");
        pager.setCurrentItem(parseInt(pos1));


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        if(item.getItemId() == R.id.cart)
        {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapterCategory extends FragmentStatePagerAdapter {

        public MyAdapterCategory(FragmentManager fm) {

            super(fm);
        }

        public Fragment getItem(int position) {
            Fragment fragment = null;
            Log.w("Men", "GetItem is Called");

            //condition when the tab are clicked and classes to be called
            if (position == 0) {

                fragment = new TshirtCat();
            }
            if (position == 1) {
                fragment = new PantCat();

            }

            if (position == 2) {

                fragment = new ShortsCat();

            }
            if (position == 3) {
                fragment = new SuitCat();
            }
            if (position == 4) {
                fragment = new ShoesCat();
            }

            if (position == 5) {
                fragment = new WatchCat();
            }
            if (position == 6) {
                fragment = new JacketsCat();
            }
            if (position == 7) {
                fragment = new SweaterCat();
            }

            if (position == 8) {
                fragment = new TrousersCat();
            }
            if (position == 9) {
                fragment = new MufflersCat();
            }
            if (position == 10) {
                fragment = new GlovesCat();
            }

            if (position == 11) {
                fragment = new HelmetsCat();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 12;
        }


    }
    class ViewPagerAdapter1 extends FragmentPagerAdapter {

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new TshirtCat();
            }
            if(position == 1) {
                return new PantCat();
            }

            if (position == 2){

                return new ShortsCat();
            }
            if(position == 3) {

                return new SuitCat();
            }
            if (position == 4){

                return new ShoesCat();
            }
            if(position == 5) {
                return new WatchCat();
            }
            if (position == 6){
                return new JacketsCat();
            }
            if(position == 7) {
                return new SweaterCat();
            }
            if (position == 8){
                return new TrousersCat();
            }
            if(position == 9) {
                return new MufflersCat();
            }
            if (position == 10){
                return new GlovesCat();
            }
            else
                return new HelmetsCat();

        }


        @Override
        public int getCount() {
            return 12;
        }
    }

}
