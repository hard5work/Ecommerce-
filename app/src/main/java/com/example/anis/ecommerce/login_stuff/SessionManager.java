package com.example.anis.ecommerce.login_stuff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.JsonReader;

import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences favsp;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor favEditor;
    Context context;

    //  public static final String KEY_TYPE = "type";
    public static final String PREF_NAME = "name";
    int PRIVATE_MODE = 0;
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FAVORITE = "favorites";
    public static final String USER_TYPE = "user_type";

    public static final String FAVORITES = "Product_Favorite";
    public static final String KEY_ID = "id";
    public static final String KEY_USERIMAGE = "image";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERID = "userid";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_ADMIN = "isAdmin";
    public static final String IS_FAV = "isFav";
    public static final String SERVERIP = "serverIP";
    public static final String DEVICEID = "deviceID";
    public static final String DEVICENAME = "deviceName";

    public static final String TYPEID = "typeid";
    public static final String CATEGORYID = "categoryid";
    public static final String NOTID ="notid";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public SessionManager() {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createLoginSession(String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void setServerip(String serverip) {
        editor.putString(SERVERIP, serverip);
        editor.commit();
    }

    public void setDeviceID(String deviceID) {
        editor.putString(DEVICEID, deviceID);
        editor.commit();
    }

    public void setTypeid(String typeid) {
        editor.putString(TYPEID, typeid);
        editor.commit();
    }


    public String getTypeid() {
        return sharedPreferences.getString(TYPEID, null);
    }

    public void setNotid(String notid){
        editor.putString(NOTID,notid);
        editor.commit();
    }
    public String getNotid(){
        return sharedPreferences.getString(NOTID, null);
    }

    public void setCategoryid(String categoryid) {
        editor.putString(CATEGORYID, categoryid);
        editor.commit();
    }

    public String getCategoryid() {
        return sharedPreferences.getString(CATEGORYID, null);

    }

    public void setDevicename(String devicename) {
        editor.putString(DEVICENAME, devicename);
        editor.commit();
    }

    public void createAdminSession(String email, String utype) {
        editor.putString(USER_TYPE, utype);
        editor.putBoolean(IS_ADMIN, true);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void addqnty(String qnty) {
        editor.putBoolean(IS_FAV, true);
        editor.putString(KEY_FAVORITE, qnty);
        editor.commit();
    }

    public void removeqnty(String qnty) {
        editor.putBoolean(IS_FAV, true);
        editor.putString(KEY_FAVORITE, qnty);
        editor.commit();

    }

    public void getUserImage(String image) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERIMAGE, image);
        editor.commit();
    }


    public void inserid(String id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERID, id);
        editor.commit();
    }


    public void create(String username) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
        user.put(KEY_USERID, sharedPreferences.getString(KEY_USERID, null));
        user.put(KEY_FAVORITE, sharedPreferences.getString(KEY_FAVORITE, null));
        user.put(KEY_USERIMAGE, sharedPreferences.getString(KEY_USERIMAGE, null));
        user.put(USER_TYPE, sharedPreferences.getString(USER_TYPE, null));

        return user;
    }

    public boolean userType(String userType) {
        String type = "admin";
        if (type.matches(userType)) {
            return sharedPreferences.getBoolean(IS_ADMIN, true);

        } else
            return sharedPreferences.getBoolean(IS_ADMIN, false);

    }

    public String getServerIP() {
        return sharedPreferences.getString(SERVERIP, null);
    }

    public String getDeviceid() {
        return sharedPreferences.getString(DEVICEID, null);
    }

    public String getDevicename() {
        return sharedPreferences.getString(DEVICENAME, null);
    }

    public boolean isLoggedIn() {

        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logOut() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//FOr closing all the existing activities;
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
        context.startActivity(i);

    }


}
