package com.example.anis.ecommerce.image_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anis.ecommerce.R;

public class GridAdapter extends BaseAdapter {
    TextView itemName,itemPrice;
    Button btnCrat;
    ImageView imgView;

    private final Context context;
    private String[] gridViewString;
    private int[] gridViewImageId;
  /* public  Integer[] pics = {
           R.drawable.ma,
           R.drawable.mb,
           R.drawable.mc,
           R.drawable.md,
           R.drawable.me,
           R.drawable.mf,
           R.drawable.mg,
           R.drawable.mh,
           R.drawable.mi,
           R.drawable.mj,
           R.drawable.mk,
           R.drawable.ml,
           R.drawable.mm,
           R.drawable.mn,
           R.drawable.ma,
           R.drawable.mb,
           R.drawable.mc,
           R.drawable.md,
           R.drawable.me,
           R.drawable.mf,
           R.drawable.mg,
           R.drawable.mh,
           R.drawable.mi,
           R.drawable.mj,
           R.drawable.mk,
           R.drawable.ml,
           R.drawable.mm,
           R.drawable.mn,
           R.drawable.ma,
           R.drawable.mb,
           R.drawable.mc,
           R.drawable.md,
           R.drawable.me,
           R.drawable.mf,
           R.drawable.mg,
           R.drawable.mh,
           R.drawable.mi,
           R.drawable.mj,
           R.drawable.mk,
           R.drawable.ml,
           R.drawable.mm,
           R.drawable.mn};*/

    public GridAdapter(Context context) {
        this.context = context;
    }

    public GridAdapter(Context context , String[] gridViewString, int[] gridViewImageId) {
        this.context = context;
        this.gridViewString= gridViewString;
        this.gridViewImageId= gridViewImageId;

    }

    @Override
    public int getCount() {
        return gridViewString.length;
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
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null) {
                gridViewAndroid = new View(context);
                gridViewAndroid = inflater.inflate(R.layout.gallery_card_view, null);

                imgView = (ImageView) gridViewAndroid.findViewById(R.id.galleryImage);
                itemName = (TextView) gridViewAndroid.findViewById(R.id.galleryItemTitle);
                itemPrice = (TextView) gridViewAndroid.findViewById(R.id.galleryPrice);
                btnCrat = (Button) gridViewAndroid.findViewById(R.id.galleryCart);
                itemName.setText(gridViewString[i]);
                imgView.setImageResource(gridViewImageId[i]);
            } else {
                gridViewAndroid = (View) view;
            }
            return gridViewAndroid;




        /*ImageView imageView = new ImageView(context);
        imageView.setImageResource(pics[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(200,200));
        return imageView;*/
    }
}
