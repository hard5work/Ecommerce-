package com.example.anis.ecommerce.adminpanel;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adminadapter.GridAdapterAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadMultipleImage extends AppCompatActivity {

    Button uploadBtn ,saveBtn;
    ArrayList<Uri> mArrayUri;
    ArrayList<Uri> mArrayList;
    GridView gridView;
    int PICK_MULTIPLE_IMAGE =1;
    String imageEncoded;
   List<String> imagesEncodedList;
   TextView testText;
    Uri mImageUri;


    String encodedImage;
    private GridAdapterAdmin gridAdapterAdmin;

    String lin = "tara.php";
   // String lin = "adminpanel/uploadimage.php";
    ProgressDialog pd;
    JSONObject jsonObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_product_multiple_desc);

        uploadBtn = (Button) findViewById(R.id.uploadProduct);
        saveBtn = (Button) findViewById(R.id.saveProduct);
        gridView = (GridView) findViewById(R.id.gridViewAdmin);
       // testText = findViewById(R.id.testText);

        jsonObject = new JSONObject();
        imagesEncodedList = new ArrayList<>();

        pd = new ProgressDialog(this);
        pd.setTitle("Uploading");
        pd.setMessage("Please Wait....");
        pd.setCancelable(false);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_MULTIPLE_IMAGE);

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
               // Toast.makeText(UploadMultipleImage.this, "test2 " + imagesEncodedList.size(), Toast.LENGTH_SHORT).show();


                final JSONArray jsonArray = new JSONArray();

                if (imagesEncodedList.isEmpty()){

                    Toast.makeText(UploadMultipleImage.this, "Please select some Images first", Toast.LENGTH_SHORT).show();

                }

                for (String encoded: imagesEncodedList){
                    jsonArray.put(encoded);
                //    Toast.makeText(UploadMultipleImage.this, "array " + jsonArray.length(), Toast.LENGTH_SHORT).show();
                }

              try{
                    jsonObject.put("imageList" , jsonArray);
                 Toast.makeText(UploadMultipleImage.this, "jsonObject " + jsonObject.length(), Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Log.e("JSONObject Here" , e.toString());
                }
                RequestQueue queue = Volley.newRequestQueue(UploadMultipleImage.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        InternetUrl.ServiceTYpe.URL + lin, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("pic" , jsonArray.toString());

                                Log.e("picss" , jsonObject.toString());
                                Log.e("Message from server1", response.toString());
                                pd.dismiss();
                                //   pd.dismiss();
                                Toast.makeText(UploadMultipleImage.this, "Image uploaded", Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Message from server2" , error.toString());
                        Toast.makeText(UploadMultipleImage.this, "Error Occured" + error, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> map = new HashMap<>();

                        map.put("imageList", jsonObject.toString());

                        return map;
                    }};

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        200*30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                queue.add(jsonObjectRequest);
           //     Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

              /*  RequestQueue queue = Volley.newRequestQueue(UploadMultipleImage.this);
                StringRequest request= new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(UploadMultipleImage.this, "response " + response, Toast.LENGTH_SHORT).show();

                                Log.i("errrrrrrrrr" ,response);
                                pd.dismiss();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (jsonObject.names().get(0).equals("success")) {


                                        pd.dismiss();

                                        Toast.makeText(UploadMultipleImage.this, "Succesfully uploaded in", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    pd.dismiss();
                                    Log.i("catchessss" , e.getMessage());
                                    Toast.makeText(UploadMultipleImage.this, "Catch error " + e, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Log.e("volley" , error.toString());
                        Toast.makeText(UploadMultipleImage.this, "volley " + error, Toast.LENGTH_SHORT).show();
                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.adminRelative),"No Internet Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();

                        map.put("imageList", jsonObject.toString());

                        return map;
                    }

                };
                queue.add(request);

               request.setRetryPolicy(new DefaultRetryPolicy(
                        200*30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
*/
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            //When an Image is Picked
            if (requestCode == PICK_MULTIPLE_IMAGE && resultCode == RESULT_OK && null != data){

                String[] filePathColumn = {MediaStore.Images.Media.DATA } ;
                imagesEncodedList = new ArrayList<String>();
                mArrayUri = new ArrayList<Uri>();
                imagesEncodedList.clear();
                if (data.getData() != null){
                     mImageUri = data.getData();

                    //GET the curser
                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null , null);
                    //MOve to first row
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageUri);
                    ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] imagesBytes = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(imagesBytes, Base64.DEFAULT);
                    imagesEncodedList.add(encodedImage);


                    cursor.close();

                    mArrayList = new ArrayList<Uri>();
                    mArrayList.add(mImageUri);
                    gridAdapterAdmin = new GridAdapterAdmin(getApplicationContext(),mArrayList);
                    gridView.setAdapter(gridAdapterAdmin);
                    gridView.setVerticalSpacing(gridView.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridView.getLayoutParams();
                    mlp.setMargins(0,gridView.getHorizontalSpacing(), 0,0);



                }
                else {
                    if (data.getClipData() != null){
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0 ; i<mClipData.getItemCount(); i++){

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            //Get the Curser
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null , null,null);

                            //move to first row
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded= cursor.getString(columnIndex);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                           encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

                            imagesEncodedList.add(encodedImage);
                //            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            gridAdapterAdmin = new GridAdapterAdmin(getApplicationContext(), mArrayUri);
                            gridView.setAdapter(gridAdapterAdmin);
                            gridView.setVerticalSpacing(gridView.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gridView.getLayoutParams();
                            mlp.setMargins(0,gridView.getHorizontalSpacing(), 0,0);


                        }
                        Log.v("LOG_TAG", "Selected Images " + mArrayUri.size());
                        Toast.makeText(this, "Selected Images " + mArrayUri.size() , Toast.LENGTH_SHORT).show();

                    }

                }
            }   else {
                Toast.makeText(this, "You Have not Picked IMAGES", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
/*
                for (String encoded: imagesEncodedList){

                    Toast.makeText(UploadMultipleImage.this, "test3 " + encoded, Toast.LENGTH_SHORT).show();
                    jsonArray.put(encoded);
                }

                try{
                    jsonObject.put("imageList" , jsonArray);
                }catch (JSONException e){
                    Log.e("JSONObject Here" , e.toString());
                    }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        InternetUrl.ServiceTYpe.URL + lin, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Message from server1", jsonObject.toString());
                                pd.dismiss();
                                     //   pd.dismiss();
                                Toast.makeText(UploadMultipleImage.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                                    //  JSONObject jsonObject = new JSONObject(response);



                                }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Message from server2" , error.toString());
                        Toast.makeText(UploadMultipleImage.this, "Error Occured" + error, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        200*30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);*/
/*

                for (String encoded: imagesEncodedList){

                    Toast.makeText(UploadMultipleImage.this, "test3 " + encoded, Toast.LENGTH_SHORT).show();
                    jsonArray.put(encoded);
                }

                try{
                    jsonObject.put("imageList" , jsonArray);
                }catch (JSONException e){
                    Log.e("JSONObject Here" , e.toString());
                    }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        InternetUrl.ServiceTYpe.URL + lin, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Message from server1", jsonObject.toString());
                                pd.dismiss();
                                     //   pd.dismiss();
                                        Toast.makeText(UploadMultipleImage.this, "Image uploaded", Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Message from server2" , error.toString());
                        Toast.makeText(UploadMultipleImage.this, "Error Occured" + error, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        200*30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

 */

 /*  RequestQueue queue = Volley.newRequestQueue(UploadMultipleImage.this);
                StringRequest request= new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(UploadMultipleImage.this, "response " + response, Toast.LENGTH_SHORT).show();

                                Log.i("errrrrrrrrr" ,response);
                                pd.dismiss();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (jsonObject.names().get(0).equals("success")) {


                                        pd.dismiss();

                                        Toast.makeText(UploadMultipleImage.this, "Succesfully uploaded in", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    pd.dismiss();
                                    Log.i("catchessss" , e.getMessage());
                                    Toast.makeText(UploadMultipleImage.this, "Catch error " + e, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(UploadMultipleImage.this, "volley " + error, Toast.LENGTH_SHORT).show();
                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.adminRelative),"No Internet Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();
                       for (String imagesList : imagesEncodedList) {
                            map.put("imageList", encodedImage);
                        }
                        return map;

                    }
                };
                queue.add(request);

*/
 /*
     RequestQueue queue = Volley.newRequestQueue(UploadMultipleImage.this);
                StringRequest request= new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(UploadMultipleImage.this, "response " + response, Toast.LENGTH_SHORT).show();

                                Log.i("errrrrrrrrr" ,response);
                                pd.dismiss();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (jsonObject.names().get(0).equals("success")) {


                                        pd.dismiss();

                                        Toast.makeText(UploadMultipleImage.this, "Succesfully uploaded in", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    pd.dismiss();
                                    Log.i("catchessss" , e.getMessage());
                                    Toast.makeText(UploadMultipleImage.this, "Catch error " + e, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(UploadMultipleImage.this, "volley " + error, Toast.LENGTH_SHORT).show();
                        Snackbar snackbar=  Snackbar.make(findViewById(R.id.adminRelative),"No Internet Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();

                            map.put("imageList",String.valueOf(imagesEncodedList));

                        return map;

                    }
                };
                queue.add(request);

  */
