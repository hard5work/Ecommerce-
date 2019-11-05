package com.example.anis.ecommerce.adminadapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.anis.ecommerce.R;

import java.util.ArrayList;

public class GridAdapterAdmin extends BaseAdapter {

    private Context context;
    private int pos;
    private LayoutInflater inflater;
    private ImageView imageView;
   private ArrayList<Uri> mArrayList;

    public GridAdapterAdmin(Context context, ArrayList<Uri> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        pos = i;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gr_adv,viewGroup,false);
        imageView = itemView.findViewById(R.id.gridImage);
        imageView.setImageURI(mArrayList.get(i));
        return itemView;
    }
}
