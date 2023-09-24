package com.example.anis.ecommerce.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.login_stuff.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationTypeAdapter extends RecyclerView.Adapter<NotificationTypeAdapter.NotificationTypeHolder > {

    Context context;
    List<Product> pList;
    String types;
    SessionManager sm;
    HashMap<String,String> uList;
    String userid;

    public NotificationTypeAdapter(Context context, List<Product> pList) {
        this.context = context;
        this.pList = pList;
    }

    public NotificationTypeAdapter(Context context, List<Product> pList, String types) {
        this.context = context;
        this.pList = pList;
        this.types = types;
    }

    @NonNull
    @Override
    public NotificationTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationTypeHolder(LayoutInflater.from(context).inflate(R.layout.card_type_noti_checkbox,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationTypeHolder holder, int position) {
        Product p =pList.get(holder.getAdapterPosition());
        sm = new SessionManager(context);
        uList = sm.getUserDetails();
        userid = uList.get(SessionManager.KEY_USERID);

        if (p.getStatus().matches("1")){
            holder.types.setChecked(true);
        }else
            holder.types.setChecked(false);
        holder.types.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setStatus(pList.get(holder.getAdapterPosition()).getTypeid(),types,"1");
                else
                    setStatus(pList.get(holder.getAdapterPosition()).getTypeid(),types,"0");
            }
        });
        holder.types.setText(p.getTypename());

    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    public void setStatus(final String typeid,final String type, final String status){
        StringRequest sr = new StringRequest(
                Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "notification/addnotification.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response aa", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("typeid",typeid);
                maps.put("type",type);
                maps.put("status",status);
                maps.put("userid",userid);
                return maps;

            }
        };

        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(sr);
    }

    public class NotificationTypeHolder extends RecyclerView.ViewHolder{
        SwitchCompat types;
        public NotificationTypeHolder(@NonNull View itemView) {
            super(itemView);
            types = itemView.findViewById(R.id.cardlistid);
        }
    }

}
