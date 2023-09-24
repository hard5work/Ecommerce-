package com.example.anis.ecommerce;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.NotificationTypeAdapter;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    SwitchCompat typeid,categoryid;
    RecyclerView typelist,categorylist;
    HashMap<String, String> userlist;
    SessionManager sm;
    String userid;
  //  Boolean typeBoo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        typeid = findViewById(R.id.typeid);
        categoryid = findViewById(R.id.categoryid);
        typelist = findViewById(R.id.typeidlist);
        categorylist = findViewById(R.id.categoryidlist);

        //setting list view
        typelist.hasFixedSize();
        categorylist.hasFixedSize();
        typelist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categorylist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //typelist1.clear();

        //session manager call for user id
        sm = new SessionManager(getApplicationContext());
        userlist = sm.getUserDetails();
        userid =userlist.get(SessionManager.KEY_USERID);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());
//        if (new SessionManager(getApplicationContext()).getTypeid().matches("1")){
//            typeid.setChecked(true);
//            typelist.setVisibility(View.VISIBLE);
//
//        }
//        else{
//            typeid.setChecked(false);
//            typelist.setVisibility(View.GONE);
//        }

        getStatus();

        typeid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   // typeBoo = true;
                    new SessionManager(getApplicationContext()).setTypeid("1");
                    setStatus("1",sm.getCategoryid());
                    typelist.setVisibility(View.VISIBLE);
                    //typelist1.clear();
                    getTypesStatus("type");
                }else
                {
                  //  typeBoo=  false;
                    new SessionManager(getApplicationContext()).setTypeid("0");
                    setStatus("0",sm.getCategoryid());
                    typelist.setVisibility(View.GONE);
                }
              //  Toast.makeText(SettingActivity.this, "khoi k vayo ni " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        categoryid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    new SessionManager(getApplicationContext()).setCategoryid("1");
                    setStatus(sm.getTypeid(),"1");
                    categorylist.setVisibility(View.VISIBLE);
                    getTypesStatus("category");
                }
                else{
                    new SessionManager((getApplicationContext())).setCategoryid("0");
                    setStatus(sm.getTypeid(),"0");
                    categorylist.setVisibility(View.GONE);
                }
            }
        });




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }



    public void getStatus(){
        StringRequest st = new StringRequest(
                Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "notification/getnotifcaitonstatus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject oj = new JSONObject(response);
                            String notids = oj.getString("notid");
                            String categoryids = oj.getString("categoryid");
                            String typeids = oj.getString("typeid");
                            sm.setNotid(notids);
                            sm.setTypeid(typeids);
                            sm.setCategoryid(categoryids);
                            if (typeids.matches("1")){
                                typeid.setChecked(true);
                               typelist.setVisibility(View.VISIBLE);
                             //  typelist1.clear();
                               getTypesStatus("type");
                            }
                            else{

                                typeid.setChecked(false);
                                typelist.setVisibility(View.GONE);
                            }
                            if (categoryids.matches("1")){
                                categoryid.setChecked(true);
                                categorylist.setVisibility(View.VISIBLE);
                                getTypesStatus("category");
                            }
                            else{

                                categoryid.setChecked(false);
                                categorylist.setVisibility(View.GONE);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
             //   getStatus();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> maps = new HashMap<>();
                maps.put("userid", userid);
                return maps;

            }
        };

        RequestQueue  rq  = Volley.newRequestQueue(getApplicationContext());
        rq.add(st);

    }

    public void setStatus(final String typeid2, final String categoryid2){
        StringRequest st = new StringRequest(
                Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "notification/setnotification.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try{
                            JSONObject oj = new JSONObject(response);
                            String success = oj.getString("success");
                            Toast.makeText(SettingActivity.this, success, Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error set", error.toString());
                //getStatus();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> maps = new HashMap<>();
                maps.put("userid", userid);
                maps.put("typeid", typeid2);
                maps.put("categoryid",categoryid2);
                maps.put("userdevice", sm.getDeviceid());
                return maps;

            }
        };

        RequestQueue  rq  = Volley.newRequestQueue(getApplicationContext());
        rq.add(st);
    }

    public void getTypesStatus(final String types){

        final List<Product> typelist1 = new ArrayList<>();
        typelist1.clear();
        StringRequest st = new StringRequest(
                Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "notification/getnotificationlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try{
                            JSONArray jA = new JSONArray(response);
                            for(int i=0; i < jA.length() ; i++){
                                JSONObject oj = jA.getJSONObject(i);
                                Product ps = new Product();
                                ps.setTypename(oj.getString("typename"));
                                ps.setNotid(oj.getString("notid"));
                                ps.setTypeid(oj.getString("typeid"));
                                ps.setStatus(oj.getString("status"));
                                ps.setCheckID(oj.getString("checkid"));
                                typelist1.add(ps);
                            }
                            NotificationTypeAdapter adapter = new NotificationTypeAdapter(SettingActivity.this, typelist1, types);

                            if (types.matches("type")) {
                               typelist.setAdapter(adapter);
                            }
                            else if(types.matches("category")){
                                categorylist.setAdapter(adapter);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error set", error.toString());
                //getStatus();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> maps = new HashMap<>();
                maps.put("userid", userid);
                maps.put("types",types);
                return maps;

            }
        };

        RequestQueue  rq  = Volley.newRequestQueue(getApplicationContext());
        rq.add(st);
    }
}