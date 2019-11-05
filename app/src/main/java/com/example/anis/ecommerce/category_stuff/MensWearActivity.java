package com.example.anis.ecommerce.category_stuff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.CategoryAdapter;
import com.example.anis.ecommerce.adapter.MenCategoryAdapter;
import com.example.anis.ecommerce.adapter.Product;

import java.util.ArrayList;
import java.util.List;

public class MensWearActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar mToolbar;
    Product myProduct;
    List<Product> mProdList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mens_wear_activity);

        recyclerView = findViewById(R.id.mensCategory);
        mToolbar = findViewById(R.id.mensTool);

        mProdList = new ArrayList<>();

        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        GridLayoutManager myGridLayoutManager = new GridLayoutManager(MensWearActivity.this, 4); //act name and span count
        recyclerView.setLayoutManager(myGridLayoutManager);

        myProduct = new Product(
                "T-shirt",
                R.drawable.ma
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "Pant",
                R.drawable.mb
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "Shorts",
                R.drawable.mc
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "suit",
                R.drawable.md
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "shoes",
                R.drawable.me
        );
        mProdList.add(myProduct);


        myProduct = new Product(
                "Watch",
                R.drawable.mg
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "Jackets",
                R.drawable.mb
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "Sweater",
                R.drawable.mj
        );
        mProdList.add(myProduct);


        myProduct = new Product(
                "Trousers",
                R.drawable.mf
        );
        mProdList.add(myProduct);


        myProduct = new Product(
                "Mufflers",
                R.drawable.mk
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "GLoves",
                R.drawable.ma
        );
        mProdList.add(myProduct);

        myProduct = new Product(
                "Helmets",
                R.drawable.md
        );
        mProdList.add(myProduct);









        MenCategoryAdapter myAdapter = new MenCategoryAdapter(this,mProdList);




        recyclerView.setAdapter(myAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
