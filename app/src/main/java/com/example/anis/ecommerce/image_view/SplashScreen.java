package com.example.anis.ecommerce.image_view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.category_stuff.MainActivity;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView image=(ImageView) findViewById(R.id.imageSplash);
        Animation animation1= AnimationUtils.loadAnimation(this, R.anim.slide_right);
        image.startAnimation(animation1);
        /* TextView txtv=(TextView) findViewById(R.id.textv);*/
       // Animation animation2=AnimationUtils.loadAnimation(this,R.anim.fade_in);
        // txtv.startAnimation(animation2);



        Thread aa= new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1200);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }



                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
            }

        });
        aa.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

