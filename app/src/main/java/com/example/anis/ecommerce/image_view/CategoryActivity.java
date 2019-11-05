package com.example.anis.ecommerce.image_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.CartGridAdapter;
import com.example.anis.ecommerce.adapter.CategoryAdapter;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.category_stuff.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity  extends AppCompatActivity {
    GridView androidGridView;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<Product> mProductList;
    Product mProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mRecyclerView = findViewById(R.id.cRecyclerview);
        mToolbar = findViewById(R.id.toolCategory);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GridLayoutManager myGridLayoutManager = new GridLayoutManager(CategoryActivity.this, 2); //act name and span count
        mRecyclerView.setLayoutManager(myGridLayoutManager);
        mProductList = new ArrayList<>();


        mProduct = new Product(
                "Men's Wear",
                R.drawable.ma
        );

        mProductList.add(mProduct);

        mProduct = new Product(
                "Women's Wear",
                R.drawable.fa
        );

        mProductList.add(mProduct);

        mProduct = new Product(
                "Kid's Wear",
                R.drawable.ka
        );

        mProductList.add(mProduct);

        mProduct = new Product(
                "Furniture",
                R.drawable.ma
        );

        mProductList.add(mProduct);

        mProduct = new Product(
                "Electronics",
                R.drawable.ma
        );

        mProductList.add(mProduct);



        CategoryAdapter myAdapter = new CategoryAdapter(CategoryActivity.this, mProductList);




        mRecyclerView.setAdapter(myAdapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


