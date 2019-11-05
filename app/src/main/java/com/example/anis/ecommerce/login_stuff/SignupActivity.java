package com.example.anis.ecommerce.login_stuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.category_stuff.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    EditText enterUser, enterEmail , enterPass ,enterFrstName, enterLastName, enterMidName, enterAddress, enterContact;
    ImageView userIMage;
    FloatingActionButton btnUpload, btnsignup ;
    String un,pw,useremail ,frstname,midname, lastname,address, contact;
    TextInputLayout uerror, error1, error2 ,error3,error4,error5,error6,error7;
    SessionManager sm;
    private static final int CAMERA_REQUEST = 123;
    private static final int GALLERY_REQUEST = 321;
    View view;
    Bitmap bitmap;
    String imageString;
    ProgressDialog pd;
    String id, userimage ,getUsername;

    String lin = "loginstuff/register.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        enterUser = findViewById(R.id.uenterUsername);
        enterEmail = findViewById(R.id.uenterEmail);
        enterPass = findViewById(R.id.uenterPassword);

        enterFrstName = findViewById(R.id.uenterfirstname);
        enterLastName = findViewById(R.id.uenterlastname);
        enterMidName = findViewById(R.id.uentermidname);

        enterAddress = findViewById(R.id.uenterAddress);
        enterContact = findViewById(R.id.uenterContact);
        userIMage = findViewById(R.id.userImage);

        btnsignup = findViewById(R.id.ubtnSignup);
        btnUpload = findViewById(R.id.ubtnupload);

        pd = new ProgressDialog(this);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSignup);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
      /*  sm = new SessionManager(SignupActivity.this);
        if(sm.isLoggedIn()){
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            this.finish();
        }*/



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(SignupActivity.this, "upload photo", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(SignupActivity.this,btnUpload);
                popupMenu.getMenuInflater().inflate(R.menu.upload_option,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(SignupActivity.this, "item Clicked" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
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


     //   imageToString();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                un= enterUser.getText().toString();
                pw = enterPass.getText().toString();
                useremail = enterEmail.getText().toString();

                frstname= enterFrstName.getText().toString();
                lastname = enterLastName.getText().toString();
                midname = enterMidName.getText().toString();
                address= enterAddress.getText().toString();
                contact = enterContact.getText().toString();




                uerror = findViewById(R.id.errorText);
                error1 = findViewById(R.id.errorText1);
                error2 = findViewById(R.id.errorText2);


                error3 = findViewById(R.id.errorText3);
                error4 = findViewById(R.id.errorText4);

                error5 = findViewById(R.id.errorText7);
                error6 = findViewById(R.id.errorText8);

                error7 = findViewById(R.id.errorText9);

                int user , pass, email, firstlen, lastlen,midlen, addreslen ,contactlen;
                user = un.trim().length();
                pass = pw.trim().length();
                email = useremail.trim().length();

                firstlen = frstname.trim().length();
                lastlen = lastname.trim().length();
                addreslen = address.trim().length();
                midlen =  midname.trim().length();

                contactlen = contact.trim().length();



                if (user == 0 || pass == 0 || email == 0 || firstlen == 0 || lastlen == 0 || addreslen == 0 || contactlen == 0) {
                    if (user == 0) {
                        uerror.setError("This field needs to be filled");

                    }
                    if (pass == 0) {
                        error2.setError("This field needs to be Filled");

                    }
                    if (email == 0) {
                        error1.setError("This field needs to be Filled");

                    }
                    if (firstlen == 0){
                        error5.setError("THis field needs to be Filled");

                    }
                    if (lastlen == 0){
                        error7.setError("THis field needs to be Filled");

                    }
                    if (addreslen == 0){
                        error3.setError("THis field needs to be Filled");

                    }
                    if (contactlen == 0){
                        error4.setError("THis field needs to be Filled");

                    }
                }
                if (user != 0 && pass != 0 && email != 0 && addreslen != 0 && contactlen != 0 && lastlen != 0 && firstlen != 0) {
                    if (midlen == 0){
                        midname = " ";
                    }


             //       Toast.makeText(SignupActivity.this, imageString, Toast.LENGTH_SHORT).show();
                    if (imageString != null)
                    Toast.makeText(SignupActivity.this, "length "+ imageString.length(), Toast.LENGTH_SHORT).show();
                    pd.setTitle("Processing...");
                    pd.setMessage("Please wait.");
                    pd.setCancelable(true);
                    pd.setIndeterminate(true);
                    pd.show();
                    signUpAdapter();
                  //  getId();


                }




            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userIMage.setImageBitmap(photo);

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
                    userIMage.setImageBitmap(currentImage);

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

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
*/



    public void signUpAdapter(){
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        StringRequest request= new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                Toast.makeText(SignupActivity.this, "response " + response, Toast.LENGTH_SHORT).show();

           //     Log.i("errrrrrrrrr" ,response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String test = enterEmail.getText().toString();

                    String test2 = enterUser.getText().toString();
                    if (jsonObject.names().get(0).equals("success"))
                    {
                            pd.dismiss();
                            sm.createLoginSession(test);
                            sm.create(test2);
                            getId();
                            Toast.makeText(SignupActivity.this, "Succesfully logged in", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(in);
                            SignupActivity.this.finish();



                    }
                    else if (jsonObject.names().get(0).equals("exists"))
                    {
                        pd.dismiss();

                        error1.setError("email already exists, Please choose another email");
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                    }

            } catch (JSONException e) {
                    pd.dismiss();
                    Log.i("catchessss" , e.getMessage());
                    Toast.makeText(SignupActivity.this, "Catch error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(SignupActivity.this, "volley " + error, Toast.LENGTH_SHORT).show();
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.signupRelative),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("username",un);
                map.put("password",pw);
                map.put("email",useremail);
                map.put("firstname",frstname);
                map.put("lastname",lastname);
                map.put("middlename",midname);
                map.put("address",address);
                map.put("number",contact);
                map.put("test",imageString);
                return map;

            }
        };
        queue.add(request);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getId(){


        String lin = "loginstuff/getid.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error ", response);
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            id = obj1.getString("id");
                            getUsername = obj1.getString("username");
                            userimage = obj1.getString("image");
                            sm.inserid(id);
                       //     sm.create(getUsername);
                            sm.getUserImage(userimage);

                        } catch (JSONException e) {
                            Snackbar snackbar=  Snackbar.make(findViewById(R.id.signupRelative0),"Email Password error",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(findViewById(R.id.signupRelative0),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", useremail);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        //VolleySingleton.getmInstance(mCtx).addToRequestQueue(stringRequest);


    }
}
