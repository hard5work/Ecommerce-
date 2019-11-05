package com.example.anis.ecommerce.image_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.CartGridAdapter;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.category_stuff.MainActivity;

import java.util.List;
import java.util.ArrayList;

public class MenNewSee extends AppCompatActivity {
    GridView androidGridView;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<Product> mProductList;
    Product mProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.men_new_see_more);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager myGridLayoutManager = new GridLayoutManager(MenNewSee.this,2); //act name and span count
        mRecyclerView.setLayoutManager(myGridLayoutManager);
        mProductList = new ArrayList<>();


        CartGridAdapter myAdapter= new CartGridAdapter(MenNewSee.this,mProductList);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }






}


