package com.example.anis.ecommerce.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.anis.ecommerce.R;

import java.util.List;

public class SliderViewPager extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Product> productList;
    private ImageLoader imageLoader;

    public SliderViewPager(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.full_image_adpater,null ,false);
        Product product = productList.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageSlider2);

        imageLoader =  CustomVolleyRequest.getInstance(context).getImageLoader();
       imageLoader.get(InternetUrl.ServiceTYpe.URL +product.getAllImage(), ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0){
                    Toast.makeText(context, "SLide CLicked 1", Toast.LENGTH_SHORT).show();
                }
                else if (position == 1){
                    Toast.makeText(context, "SLide CLicked 2", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "slide CLicked 3", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view , 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
