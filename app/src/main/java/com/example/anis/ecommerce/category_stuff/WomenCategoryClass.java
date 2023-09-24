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
import com.example.anis.ecommerce.womenclothing.WomenGlovesCat;
import com.example.anis.ecommerce.womenclothing.WomenHelmetsCat;
import com.example.anis.ecommerce.womenclothing.WomenJacketsCat;
import com.example.anis.ecommerce.womenclothing.WomenKurthaCat;
import com.example.anis.ecommerce.womenclothing.WomenLengaCat;
import com.example.anis.ecommerce.womenclothing.WomenMufflersCat;
import com.example.anis.ecommerce.womenclothing.WomenOnePiece;
import com.example.anis.ecommerce.womenclothing.WomenOthersCat;
import com.example.anis.ecommerce.womenclothing.WomenPantCat;
import com.example.anis.ecommerce.womenclothing.WomenShoesCat;
import com.example.anis.ecommerce.womenclothing.WomenShortsCat;
import com.example.anis.ecommerce.womenclothing.WomenSuitCat;
import com.example.anis.ecommerce.womenclothing.WomenSweaterCat;
import com.example.anis.ecommerce.womenclothing.WomenTopCat;
import com.example.anis.ecommerce.womenclothing.WomenTrouserCat;
import com.example.anis.ecommerce.womenclothing.WomenTshirtCat;
import com.example.anis.ecommerce.womenclothing.WomenWatchCat;

import static java.lang.Integer.parseInt;

public class WomenCategoryClass extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    TabLayout tabLayout;
    private String[] pageTitle = {
            "T-Shirt" , "Pant" , "Shorts" , "Suits" , "Shoes"  , "Watch" , "Jackets" , "Sweater" ,
            "Trousers" , "Mufflers" , "Gloves" , "Helmets" , "Lenga", "One-Piece" , "Kurtha" , "Top" ,
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
        pager.setAdapter(new WomenCategoryClass.MyAdapterCategory(fragmentManager));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_men);

        for (int i = 0; i < 17; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i])); //displaying the fragment name in tab view
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        WomenCategoryClass.ViewPagerAdapter1 pagerAdapter = new WomenCategoryClass.ViewPagerAdapter1(getSupportFragmentManager());
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

                fragment = new WomenTshirtCat();
            }
            if (position == 1) {
                fragment = new WomenPantCat();

            }

            if (position == 2) {

                fragment = new WomenShortsCat();

            }
            if (position == 3) {
                fragment = new WomenSuitCat();
            }
            if (position == 4) {
                fragment = new WomenShoesCat();
            }

            if (position == 5) {
                fragment = new WomenWatchCat();
            }
            if (position == 6) {
                fragment = new WomenJacketsCat();
            }
            if (position == 7) {
                fragment = new WomenSweaterCat();
            }

            if (position == 8) {
                fragment = new WomenTrouserCat();
            }
            if (position == 9) {
                fragment = new WomenMufflersCat();
            }
            if (position == 10) {
                fragment = new WomenGlovesCat();
            }

            if (position == 11) {
                fragment = new WomenHelmetsCat();
            }
            if (position == 12) {
                fragment = new WomenKurthaCat();
            }


            if (position == 13) {
                fragment = new WomenTopCat();
            }
            if (position == 14) {
                fragment = new WomenLengaCat();
            }
            if (position == 15) {
                fragment = new WomenOnePiece();
            }

            if (position == 16) {
                fragment = new WomenOthersCat();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 17;
        }


    }
    class ViewPagerAdapter1 extends FragmentPagerAdapter {

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new WomenTshirtCat();
            }
            if(position == 1) {
                return new WomenPantCat();
            }

            if (position == 2){

                return new WomenShortsCat();
            }
            if(position == 3) {

                return new WomenSuitCat();
            }
            if (position == 4){

                return new WomenShoesCat();
            }
            if(position == 5) {
                return new WomenWatchCat();
            }
            if (position == 6){
                return new WomenJacketsCat();
            }
            if(position == 7) {
                return new WomenSweaterCat();
            }
            if (position == 8){
                return new WomenTrouserCat();
            }
            if(position == 9) {
                return new WomenMufflersCat();
            }
            if (position == 10){
                return new WomenGlovesCat();
            }
            if (position == 11){
                return new WomenKurthaCat();
            }
            if(position == 12) {
                return new WomenTopCat();
            }
            if (position == 13){
                return new WomenOnePiece();
            }
            if(position == 14) {
                return new WomenOthersCat();
            }
            if (position ==15){
                return new WomenLengaCat();
            }
            else
                return new WomenHelmetsCat();

        }


        @Override
        public int getCount() {
            return 17;
        }
    }

}
