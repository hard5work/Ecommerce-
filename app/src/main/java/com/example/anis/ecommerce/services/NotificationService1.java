package com.example.anis.ecommerce.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.NoiseSuppressor;
import android.os.Build;
import android.os.IBinder;
import android.se.omapi.Session;
import android.util.Log;

import androidx.collection.SimpleArrayMap;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.login_stuff.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class NotificationService1 extends Service {
    SessionManager sm;
    HashMap<String, String> mp;
    String userid,deviceid;

    public NotificationService1() {
    }

    @Override
    public void onCreate() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(10000);
                        try{
                            notifier();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        super.onCreate();
    }

    public void notifier(){
        sm = new SessionManager(NotificationService1.this);
        mp = sm.getUserDetails();
        userid =  mp.get(SessionManager.KEY_USERID);
        deviceid = sm.getDeviceid();

        StringRequest st = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + "notification/newgetnotification.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jo = new JSONObject(response);
                            Log.e("The Responses",response);
                            String id = jo.getString("id");
                            String status = jo.getString("status");
                            String name = jo.getString("name");
                            if (status.matches("0")){
                                createNotification(name,id,NotificationService1.this);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent("new.items");
                        sendBroadcast(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("notification error ", error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("userid",userid);
                maps.put("userdevice",deviceid);
                return maps;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(NotificationService1.this);
        rq.add(st);

    }
    private NotificationManager manager;

    public void createNotification(String name, String id, Context context){

        String title = "new Item " + id;
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (manager == null){
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            assert manager  != null;
            NotificationChannel mC = manager.getNotificationChannel(id);
            if (mC == null){
                mC = new NotificationChannel(id,title, importance);
                mC.enableVibration(true);
                manager.createNotificationChannel(mC);
            }
            String titles = " New Item- " + name;
            String msger = "Availble";
            builder = new NotificationCompat.Builder(context,id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentText(titles)
                    .setSmallIcon(R.mipmap.ic_launcher_1_round)
                    .setContentText(msger)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setTicker(titles)
                    .setContentIntent(pendingIntent);
        }
        else{
            String msger = "Available";
            String ready ="New Item- " + name;
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentTitle(ready)
                    //  .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setSmallIcon(R.mipmap.ic_launcher_1_round)
                    .setContentText(msger)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(ready)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        int notifyID = parseInt(id);
        manager.notify(notifyID,notification);

    }

    @Override
    public void onDestroy() {
        Log.e("Notification Destoried", "destroied");
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
