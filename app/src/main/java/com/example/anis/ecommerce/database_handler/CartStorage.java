package com.example.anis.ecommerce.database_handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.anis.ecommerce.adapter.Product;

import java.util.List;

public class CartStorage extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase dbs = getWritableDatabase();
    List<Product> productList;
    public static final String DATABASE_NAME = "Fav_product";
    public static final String TABLE_NAME = "Fav_items";
    public static final String ID ="ID";
    public static final String ITEMNAME ="ITEMSNAME";

    public CartStorage(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
