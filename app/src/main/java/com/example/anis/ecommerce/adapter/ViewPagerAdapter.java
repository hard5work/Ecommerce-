package com.example.anis.ecommerce.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.anis.ecommerce.category_stuff.KidsWear;
import com.example.anis.ecommerce.category_stuff.MenWear;
import com.example.anis.ecommerce.category_stuff.WomenWear;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new MenWear();
        }
        if(position == 1)
            return new WomenWear();
        else
            return new KidsWear();
    }


    @Override
    public int getCount() {
        return 3;
    }
}
