package com.example.anis.ecommerce.adminpanel;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anis.ecommerce.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    android.support.v4.app.FragmentTransaction ft;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayoutAdmin, new ViewProducts());
                    ft.commit();
                    return true;
                case R.id.navigation_dashboard:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayoutAdmin, new ViewUsers());
                    ft.commit();
                    return true;
                case R.id.navigation_notifications:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayoutAdmin, new AdminDetail());
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mTextMessage = (TextView) findViewById(R.id.message);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutAdmin, new ViewProducts());
        ft.commit();




    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Wanna exit?")
                .setMessage("DO YOU WANT TO EXIT?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
