package com.example.anis.ecommerce.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class ViewPagerAnimation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {

        page.setTranslationX(-position*page.getWidth());
        page.setCameraDistance(999999999);

        if (position<0.5 && position >-0.5){
            page.setVisibility(View.VISIBLE);
        }
        else {
            page.setVisibility(View.INVISIBLE);
        }

        if (position < -1){ //[-infinity , -1]
            //this page is way off to the left
            page.setAlpha(0);
        }
        else if (position <= 0){ //[-1 , 0]
            page.setAlpha(1);
            page.setRotationX(180*(1-Math.abs(position) +1));
        }
        else if (position <=1) { //[0,1]
            page.setAlpha(1);
            page.setRotationX(-180*(1-Math.abs(position)+1));
        }
        else {
            //[1,+infinty]
            //this page is way off to the right
            page.setAlpha(0);
        }

    }
}
