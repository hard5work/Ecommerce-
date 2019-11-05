package com.example.anis.ecommerce.image_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.anis.ecommerce.image_view.MenNewSee;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MenNewSee.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
}