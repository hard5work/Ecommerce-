package com.example.anis.ecommerce.cart_list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.anis.ecommerce.R;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class CheckoutActivity extends AppCompatActivity {
    String[] descData = {"Shipping" , "Payment" , "Confirm"};
    RelativeLayout shipping, payment, confirm;
    FragmentTransaction ft;

    FloatingActionButton next , prev;
    StateProgressBar stateProgressBar;
    ViewPager viewPager;
    FrameLayout frameLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_cart_style);
        stateProgressBar = (StateProgressBar) findViewById(R.id.state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descData);
        next = findViewById(R.id.nextFragmnet);
        prev = findViewById(R.id.prevFragmnet);/*
        shipping = (RelativeLayout) findViewById(R.id.contactDetails);
        payment = (RelativeLayout) findViewById(R.id.paymentStyle);
        confirm = (RelativeLayout) findViewById(R.id.confrimation);*/
   //    viewPager = (ViewPager) findViewById(R.id.checkoutPager);//for displaying the tab layout and fragment
       frameLayout = (FrameLayout) findViewById(R.id.frameLayoutcheckout);

       ft= getSupportFragmentManager().beginTransaction();
       ft.replace(R.id.frameLayoutcheckout, new ShippingCheckout());
       ft.commit();




    //  FragmentManager fragmentManager = getSupportFragmentManager(); //to manage the fragment and to get support for the fragment
    //    viewPager.setAdapter(new MyAdapter(fragmentManager));
//        viewPager.setOnTouchListener(null);



     //   CheckoutActivity.ViewPagerAdapter pagerAdapter = new CheckoutActivity.ViewPagerAdapter(getSupportFragmentManager());
   //    viewPager.setAdapter(pagerAdapter);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (stateProgressBar.getCurrentStateNumber()) {
                    case 1:
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                  /*  shipping.setVisibility(View.GONE);
                    payment.setVisibility(View.VISIBLE);

                        confirm.setVisibility(View.GONE);*/
                  ft = getSupportFragmentManager().beginTransaction();
                  ft.replace(R.id.frameLayoutcheckout,new PaymentCheckout());
                  ft.commit();

                //   viewPager.setCurrentItem(stateProgressBar.getCurrentStateNumber() - 1);

                    break;
                    case 2:
                        stateProgressBar.setAllStatesCompleted(true);
                       /* shipping.setVisibility(View.GONE);
                        payment.setVisibility(View.GONE);
                        confirm.setVisibility(View.VISIBLE);*/
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayoutcheckout,new ConfirmCheckout());
                        ft.commit();


                        //    viewPager.setCurrentItem(stateProgressBar.getCurrentStateNumber() - 1);
                        break;




                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateProgressBar.setAllStatesCompleted(false);
                 switch (stateProgressBar.getCurrentStateNumber()) {

                    case 2:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                  /*      shipping.setVisibility(View.VISIBLE);
                        payment.setVisibility(View.GONE);

                        confirm.setVisibility(View.GONE);
                        */

                        ft = getSupportFragmentManager().beginTransaction();
                  ft.replace(R.id.frameLayoutcheckout,new ShippingCheckout());
                  ft.commit();


                   //     viewPager.setCurrentItem(stateProgressBar.getCurrentStateNumber() - 1);
                        break;
                    case 3:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        /*payment.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.GONE);
                        shipping.setVisibility(View.GONE);*/

                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayoutcheckout,new PaymentCheckout());
                        ft.commit();


                        //    viewPager.setCurrentItem(stateProgressBar.getCurrentStateNumber() - 1);
                        break;



                }
            }
        });




    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {

            super(fm);
        }

        public Fragment getItem(int position) {
            Fragment fragment = null;
            Log.w("Men", "GetItem is Called");

            //condition when the tab are clicked and classes to be called
            if (position == 0) {
                fragment = new ShippingCheckout();
            }
            if (position == 1) {

                fragment = new PaymentCheckout();
            }

            if (position == 2) {

                fragment = new ConfirmCheckout();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }




    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ShippingCheckout();
            }
            if (position == 1) {

                return new PaymentCheckout();
            } else {


                return new ConfirmCheckout();
            }
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

}

