package com.example.anis.ecommerce.userprofile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.CustomVolleyRequest;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.login_stuff.LoginActivty;
import com.example.anis.ecommerce.login_stuff.SessionManager;
import com.example.anis.ecommerce.login_stuff.SignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {
    TextView fullName,username,useremail,userAddress,userContact;
    ImageView userPhoto;
    Button changePic,editDetail ,savPic;
    PopupWindow popUP;
    String fname,mname,lname,uname,uemail,uaddres,ucontact,uphoto,id,useremail2 ,username2,userimage;
    SessionManager sm;
    String imageString;
    RelativeLayout userRelativeLayoutl;
    FloatingActionButton floatbtn;
    private static final int CAMERA_REQUEST = 12;
    private static final int GALLERY_REQUEST = 10;
    Boolean click = true;
    ProgressDialog pd;
    Context mContext;
    Activity mActivity;
    AlertDialog.Builder builder;
    ImageLoader imageLoader;
    AlertDialog dialog;
    String frstname,lastname,midname,cccontact,aaaddress;
    int  firstlen, lastlen,midlen, addreslen ,contactlen;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        fullName = (TextView) findViewById(R.id.userFullNameDisplay);
        username = (TextView) findViewById(R.id.userNameDisplay);
        useremail = (TextView) findViewById(R.id.userEmailDisplay);
        userAddress = (TextView) findViewById(R.id.userAddressDisplay);
        userContact = (TextView) findViewById(R.id.userContactDisplay);
        userPhoto = (ImageView) findViewById(R.id.userImage);
        savPic = (Button) findViewById(R.id.savePic);
        changePic = (Button)findViewById(R.id.changePic);
        editDetail = (Button) findViewById(R.id.editProfile);
        floatbtn = (FloatingActionButton) findViewById(R.id.floatingbtnuser);
        userRelativeLayoutl = (RelativeLayout) findViewById(R.id.userProfileRelativeLayout);
        mContext = getApplicationContext();
        mActivity = UserProfileActivity.this;

        popUP  = new PopupWindow(this);

        pd= new ProgressDialog(this);
        sm = new SessionManager(getApplicationContext());

        HashMap<String,String> user = sm.getUserDetails();
        useremail2 = user.get(SessionManager.KEY_EMAIL);
        username2 = user.get(SessionManager.KEY_USERNAME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setTitle("Profile");


        setTitleColor(getResources().getColor(android.R.color.white));

        getId();
        if (uphoto == null)
        {
            userPhoto.setImageResource(R.drawable.profile);
        }

        editDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                // Toast.makeText(UserProfileActivity.this, "clickeddd", Toast.LENGTH_SHORT).show();
          /*   LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

          View vi = inflater.inflate(R.layout.popup_edit_window,null);
          popUP = new PopupWindow(vi,
                   ViewGroup.LayoutParams.WRAP_CONTENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT
                   );

           if(Build.VERSION.SDK_INT>=21) {
               popUP.setElevation(5.0f);
           }*/
       @SuppressLint("InflateParams")
       View vi = LayoutInflater.from(mContext).inflate(R.layout.popup_edit_window,null,false);

           final Button save = vi.findViewById(R.id.saveDetails);

                final EditText  ffname = vi.findViewById(R.id.editFullNameDisplay);
                final EditText mmname = vi.findViewById(R.id.editNameDisplay);
                final EditText llname =vi.findViewById(R.id.editEmailDisplay);
                final EditText aaddress = vi.findViewById(R.id.editAddressDisplay);
                final EditText ccontact = vi.findViewById(R.id.editContactDisplay);




                pd.setTitle("Updating");
                pd.setMessage("Please wait..");

                builder = new AlertDialog.Builder(mActivity);
                builder.setView(vi);
                dialog = builder.create();/*
                dialog.setIcon(android.R.drawable.edit_text);*/
                dialog.show();

                {
                    final String lin = "loginstuff/getUserDetail.php";

                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.<Context>requireNonNull(getApplicationContext()));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("error ", response);
                                    try {
                                        JSONObject obj1 = new JSONObject(response);
                                        id = obj1.getString("id");
                                        uname = obj1.getString("username");
                                        uemail= obj1.getString("email");
                                        fname = obj1.getString("firstname");
                                        lname = obj1.getString("lastname");
                                        mname = obj1.getString("middlename");
                                        uphoto = obj1.getString("image");
                                        uaddres = obj1.getString("address");
                                        ucontact = obj1.getString("contact_no");

                                        if (mname == null){
                                            mname = "";
                                            mmname.setText(mname);
                                            mmname.setSelection(mmname.getText().length());
                                        }
                                        else {
                                            ffname.setText(fname);
                                            ffname.setSelection(ffname.getText().length());
                                            mmname.setText(mname);
                                            mmname.setSelection(mmname.getText().length());
                                            llname.setText(lname);
                                            llname.setSelection(llname.getText().length());
                                        }
                                        aaddress.setText(uaddres);
                                        aaddress.setSelection(aaddress.getText().length());
                                        ccontact.setText(ucontact);
                                        ccontact.setSelection(ccontact.getText().length());



                                    } catch (JSONException e) {
                                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.userProfileRelativeLayout),"Email Password error",Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.userDisplayer),"No Internet Connection",Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", useremail2);
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);

                }


              //  popUP.showAtLocation(userRelativeLayoutl,Gravity.CENTER,0,0);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        frstname= ffname.getText().toString();
                        lastname = llname.getText().toString();
                        midname = mmname.getText().toString();
                        aaaddress= aaddress.getText().toString();
                        cccontact = ccontact.getText().toString();



                        firstlen = frstname.trim().length();
                        lastlen = lastname.trim().length();
                        addreslen = aaaddress.trim().length();
                        midlen =  midname.trim().length();

                        contactlen = cccontact.trim().length();


                        if ( firstlen == 0 || lastlen == 0 || addreslen == 0 || contactlen == 0) {

                            if (firstlen == 0){
                                Toast.makeText(UserProfileActivity.this, "enter firstname", Toast.LENGTH_SHORT).show();

                            }
                            if (lastlen == 0){
                                Toast.makeText(UserProfileActivity.this, "enter lastname", Toast.LENGTH_SHORT).show();

                            }
                            if (addreslen == 0){
                                Toast.makeText(UserProfileActivity.this, "enter address", Toast.LENGTH_SHORT).show();

                            }
                            if (contactlen == 0){
                                Toast.makeText(UserProfileActivity.this, "enter contact", Toast.LENGTH_SHORT).show();

                            }
                        }
                        if ( addreslen != 0 && contactlen != 0 && lastlen != 0 && firstlen != 0) {
                            if (midlen == 0) {
                                midname = " ";
                            }
                            pd.show();



                                final String lin = "loginstuff/updatefullname.php";

                                RequestQueue requestQueue = Volley.newRequestQueue(Objects.<Context>requireNonNull(getApplicationContext()));

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("error ", response);
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);


                                                    if (jsonObject.names().get(0).equals("success")) {

                                                        dialog.dismiss();
                                                        pd.dismiss();
                                                        Intent intent = new Intent(mActivity,UserProfileActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);


                                                    }
                                                } catch (JSONException e) {
                                                    pd.dismiss();
                                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.userProfileRelativeLayout), "Email Password error", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pd.dismiss();
                                        Snackbar snackbar = Snackbar.make(findViewById(R.id.userDisplayer), "No Internet Connection", Snackbar.LENGTH_LONG);
                                        snackbar.show();

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("email", useremail2);
                                        params.put("username", username2);
                                        params.put("firstname", frstname);
                                        params.put("middlename", midname);
                                        params.put("lastname", lastname);
                                        params.put("address", aaaddress);
                                        params.put("contact", cccontact);

                                        return params;
                                    }
                                };

                                requestQueue.add(stringRequest);
                            }



                    }
                });




            }
        });

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this ,MainActivity.class);
                startActivity(intent);
            }
        });



        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(SignupActivity.this, "upload photo", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(UserProfileActivity.this,changePic);
                popupMenu.getMenuInflater().inflate(R.menu.upload_option,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(UserProfileActivity.this, "item Clicked" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        if(menuItem.getItemId() == R.id.chooseCamera){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent , CAMERA_REQUEST);
                        }

                        if (menuItem.getItemId() == R.id.chooseGallery){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                            startActivityForResult(intent,GALLERY_REQUEST );
                        }


                        return true;
                    }
                });
                popupMenu.show();

            }
        });

        savPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Saving Photo");
                pd.setMessage("PLease wait.....");
                pd.show();
                String lin = "updatePhoto.php";

                RequestQueue queue = Volley.newRequestQueue(mActivity);
                StringRequest request= new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {


                   //     Toast.makeText(mActivity, "response " + response, Toast.LENGTH_SHORT).show();

                            Log.i("errrrrrrrrr" ,response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("success"))
                            {
                                pd.dismiss();
                                savPic.setVisibility(View.GONE);
                                getids();
                                Intent in = new Intent(mActivity, UserProfileActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);


                            }
                            else
                            {
                                Toast.makeText(mActivity, "no data found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Log.i("catchessss" , e.getMessage());
                            Toast.makeText(mActivity, "Catch error " + e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.userprofilemainlayout),"No Internet Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();

                        Log.i("volley" , error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("username",username2);
                        map.put("email",useremail2);
                        map.put("image",imageString);
                        return map;

                    }
                };
                queue.add(request);

            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userPhoto.setImageBitmap(photo);
            savPic.setVisibility(View.VISIBLE);

            ByteArrayOutputStream bit = new ByteArrayOutputStream();
            assert photo != null;
            photo.compress(Bitmap.CompressFormat.JPEG,100,bit);
            byte[] imageBytes = bit.toByteArray();
            imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        }


        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK &&  data != null && data.getData() != null){

            Uri photoUri = data.getData();
            if (photoUri != null){
                try{
                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
                    userPhoto.setImageBitmap(currentImage);
                    savPic.setVisibility(View.VISIBLE);

                    ByteArrayOutputStream bit = new ByteArrayOutputStream();
                    currentImage.compress(Bitmap.CompressFormat.JPEG,100,bit);
                    byte[] imageBytes = bit.toByteArray();
                    imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getId(){


        String lin = "loginstuff/getUserDetail.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            id = obj1.getString("id");
                            uname = obj1.getString("username");
                             uemail= obj1.getString("email");
                            fname = obj1.getString("firstname");
                            lname = obj1.getString("lastname");
                            mname = obj1.getString("middlename");
                            uphoto = obj1.getString("image");
                            uaddres = obj1.getString("address");
                            ucontact = obj1.getString("contact_no");

                            if (mname == null){

                                String fullnamee = fname+lname;
                                fullName.setText(fullnamee);
                            }
                            else {

                                String fullnamee = fname + " " + mname + " " + lname;
                                fullName.setText(fullnamee);
                            }
                            userAddress.setText(uaddres);
                            userContact.setText(ucontact);
                            useremail.setText(uemail);
                            username.setText(uname);

                            imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
                            imageLoader.get(InternetUrl.ServiceTYpe.URL +uphoto,ImageLoader.getImageListener(userPhoto,R.mipmap.ic_launcher, R.drawable.profile));

                    /*        Glide.with(getApplicationContext())
                                    .load(InternetUrl.ServiceTYpe.URL + uphoto)
                                    .into(userPhoto);
*/
                        } catch (JSONException e) {
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.userDisplayer),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.userDisplayer),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", useremail2);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getids(){


        String lint = "loginstuff/getphoto.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject obj1 = new JSONObject(response);

                            userimage = obj1.getString("image");

                            sm.getUserImage(userimage);

                        } catch (JSONException e) {
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.loginLinear),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", username2);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


    }
}
