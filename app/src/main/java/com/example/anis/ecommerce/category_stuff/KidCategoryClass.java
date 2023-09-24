package com.example.anis.ecommerce.category_stuff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.cart_list.CartActivity;
import com.example.anis.ecommerce.kidclothing.KIdPantCat;
import com.example.anis.ecommerce.kidclothing.KidOthersCat;
import com.example.anis.ecommerce.kidclothing.KidShoesCat;
import com.example.anis.ecommerce.kidclothing.KidTshirtCat;

public class KidCategoryClass extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    TabLayout tabLayout;
    private String[] pageTitle = {
            "T-Shirt" , "Pant" , "Shoes" ,
            "Others"
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
        pager.setAdapter(new KidCategoryClass.MyAdapterCategory(fragmentManager));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_men);

        for (int i = 0; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i])); //displaying the fragment name in tab view
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        KidCategoryClass.ViewPagerAdapter1 pagerAdapter = new KidCategoryClass.ViewPagerAdapter1(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
     /*   String pos1 = getIntent().getStringExtra("one");
        pager.setCurrentItem(parseInt(pos1));
*/

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

                fragment = new KidTshirtCat();
            }
            if (position == 1) {
                fragment = new KIdPantCat();

            }



            if (position == 2) {
                fragment = new KidShoesCat();
            }

            if (position == 3) {
                fragment = new KidOthersCat();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }


    }
    class ViewPagerAdapter1 extends FragmentPagerAdapter {

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new KidTshirtCat();
            }
            if(position == 1) {
                return new KIdPantCat();
            }

            if (position == 2){

                return new KidShoesCat();
            }
            else
                return new KidOthersCat();

        }


        @Override
        public int getCount() {
            return 4;
        }
    }

}