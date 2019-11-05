package com.example.anis.ecommerce.adminpanel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.adapter.ProductAdapter;
import com.example.anis.ecommerce.adminadapter.ViewProductAdapter;
import com.example.anis.ecommerce.userprofile.UserProfileActivity;
import com.tapadoo.alerter.Alert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProducts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProducts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     TextInputEditText productNAME, productPRICE,  productTAGS , productCOLOR, productSIZE, productIMAGEname;
     Spinner productCategory, sizeID, colorID, imageID;
     ImageView imageView , colorVIEW, sizeVIEW, imageIMAGE;
     Button selectProduct,cancelAdd , addProducts , cancelSIZE, cancelCOLOR, cancelIMAGE, updateCOLOR;
     Button updateSIZE, selectIMAGE, uploadIMAGE;
     EditText productDESC;

     String prodNAME, prodPRICE, prodTAGS, prodDESC ,prodCOLOR, prodIMAGEname, prodIMAGE, prodSIZE;
     int prodNAMElen , prodPRICElen, prodTAGSlen , prodDESClen , prodCOLORlen, prodSIZElen,prodIMAGEnamelen;

     String[] category = {"K" , "M" , "F" , "M,F" , "K,M" ,"K,F" };
     List<String> catogoryList;
    ArrayAdapter<String> adapter;
    String spinnerValue , spinnerIDimage , spinnerIDcolor , spinnerIDsize;
    AlertDialog.Builder builder , builderIMAGE , builderCOLOR, builderSIZE;
    AlertDialog alertDialog , alertDialogIMAGE, alertDialogCOLOR, alertDialogSIZE;
    List<String> idList;
    ArrayAdapter<String> idAdapter;
    ViewProductAdapter adapterView;
    ProgressDialog pd;

    NotificationManager mNotificationManager  ;


    RecyclerView productRecycler;
    List<Product> productList;
    List<Product> colorL , sizeL;
    String[] color,size;
    Product product;
    String encodedImages , decodedImages;
    FloatingActionButton addPro;
    FloatingActionButton addProduct , addColor, addSize, addImages;
    LinearLayout layoutAddProd;
    LinearLayout layoutAddSize;
    LinearLayout layoutAddImage;
    LinearLayout layoutAddColor;
    private static final int STRING_REQUEST = 100;
    private static final int CAMERA_REQUEST = 200;
    private boolean fabExpanded = false; //to check if fav is open or close



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewProducts newInstance(String param1, String param2) {
        ViewProducts fragment = new ViewProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_products, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productRecycler = view.findViewById(R.id.viewProductRecycler);
        addPro = (FloatingActionButton) view.findViewById(R.id.addProductfab);
        addProduct = (FloatingActionButton) view.findViewById(R.id.addPrductFab);
        addColor = (FloatingActionButton) view.findViewById(R.id.addFabColor);
        addSize = (FloatingActionButton) view.findViewById(R.id.addFabSize);
        addImages = (FloatingActionButton) view.findViewById(R.id.addImagesFAB);
        layoutAddColor = (LinearLayout) view.findViewById(R.id.addColorFabLayout);
        layoutAddImage = (LinearLayout) view.findViewById(R.id.addImagesFabLayout);
        layoutAddProd = (LinearLayout) view.findViewById(R.id.addProFabLayout);
        layoutAddSize = (LinearLayout) view.findViewById(R.id.addSizeFabLayout);

        idList = new ArrayList<>();
        productRecycler.hasFixedSize();
        pd= new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setCancelable(false);

        productRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        loadProduct();
        getProductIds();

        closeSubMenusFab();
        addPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                }
                else {
                    openSubMenusFab();
                }

            }
        });
/*
        ****For adding the size of the product....................................///////
 */
        addSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewSize = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_size ,null , false);

               sizeID = viewSize.findViewById(R.id.spinnergetID);
                productSIZE = viewSize.findViewById(R.id.addSIZE);
                cancelSIZE = viewSize.findViewById(R.id.cancelSIZE);
                updateSIZE = viewSize.findViewById(R.id.updateSIZE);
                sizeVIEW = viewSize.findViewById(R.id.imageViewSIZE);

                sizeID.setAdapter(idAdapter);

                sizeID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinnerIDsize = sizeID.getItemAtPosition(sizeID.getSelectedItemPosition()).toString();

                        sizeVIEW.setVisibility(View.VISIBLE);
                      /*  RequestOptions requestOptions = new RequestOptions()
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE);*/

                        {
                            String ima= "getProductImage.php";
                            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    InternetUrl.ServiceTYpe.URL + ima,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject object =new JSONObject(response);

                                                Glide.with(viewSize).load(InternetUrl.ServiceTYpe.URL + object.getString("image"))
                                                        //  .apply(requestOptions)
                                                        .into(sizeVIEW);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.getMessage();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String , String> map = new HashMap<>();
                                    map.put("id" , spinnerIDsize);
                                    return map;
                                }
                            };
                            requestQueue.add(stringRequest);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                builderSIZE = new AlertDialog.Builder(getContext());
                builderSIZE.setView(viewSize);
                alertDialogSIZE = builderSIZE.create();
                alertDialogSIZE.setCancelable(false);
                alertDialogSIZE.show();

                cancelSIZE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sizeVIEW.setVisibility(View.GONE);
                        productList.clear();
                        loadProduct();
                        alertDialogSIZE.dismiss();
                    }
                });

                updateSIZE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prodSIZE = productSIZE.getText().toString();
                        prodSIZElen = prodSIZE.trim().length();
                        if (prodSIZElen == 0){
                            productSIZE.setError("No size declared");
                        }
                        if (prodSIZElen != 0){
                            {
                                String ima= "insertSize.php";
                                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        InternetUrl.ServiceTYpe.URL + ima,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try{
                                                    JSONObject object =new JSONObject(response);
                                                    Log.e("data" , response);

                                                    if (object.names().get(0).equals("success")){
                                                        AlertDialog.Builder enter = new AlertDialog.Builder(getContext());
                                                        enter.setTitle("Insert Again");
                                                        enter.setPositiveButton("Yes", null);
                                                        enter.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                productList.clear();
                                                                loadProduct();
                                                                alertDialogSIZE.dismiss();
                                                            }
                                                        });
                                                        AlertDialog dialog = enter.create();
                                                        dialog.show();


                                                    }

                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.getMessage();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String , String> map = new HashMap<>();
                                        map.put("productid" , spinnerIDsize);
                                        map.put("size" , prodSIZE);
                                        return map;
                                    }
                                };
                                requestQueue.add(stringRequest);

                            }
                        }

                    }
                });


            }
        });

        /*
         ****For adding the color of the product....................................///////
         */
        addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewColor = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_color ,null , false);
                colorID = viewColor.findViewById(R.id.spinnergetID);
                cancelCOLOR = viewColor.findViewById(R.id.cancelColor);
                updateCOLOR = viewColor.findViewById(R.id.updateCOLOR);
                productCOLOR = viewColor.findViewById(R.id.addCOLOR);
                colorVIEW = viewColor.findViewById(R.id.imageViewCOLOR);

                colorID.setAdapter(idAdapter);
                colorID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinnerIDcolor = colorID.getItemAtPosition(colorID.getSelectedItemPosition()).toString();
                        colorVIEW.setVisibility(View.VISIBLE);

                        {
                            String ima= "getProductImage.php";
                            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    InternetUrl.ServiceTYpe.URL + ima,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject object =new JSONObject(response);

                                                Glide.with(viewColor).load(InternetUrl.ServiceTYpe.URL + object.getString("image"))
                                                        //  .apply(requestOptions)
                                                        .into(colorVIEW);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.getMessage();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String , String> map = new HashMap<>();
                                    map.put("id" , spinnerIDcolor);
                                    return map;
                                }
                            };
                            requestQueue.add(stringRequest);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                builderCOLOR = new AlertDialog.Builder(getContext());
                builderCOLOR.setView(viewColor);
                alertDialogCOLOR = builderCOLOR.create();
                alertDialogCOLOR.setCancelable(false);
                alertDialogCOLOR.show();

                cancelCOLOR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productList.clear();
                        loadProduct();
                        alertDialogCOLOR.dismiss();
                    }
                });

                updateCOLOR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prodCOLOR = productCOLOR.getText().toString();
                        prodCOLORlen = prodCOLOR.trim().length();

                        if (prodCOLORlen == 0){
                            productCOLOR.setError("No color Entered");
                        }

                        if (prodCOLORlen != 0 ) {
                            {

                                String ima = "insertColor.php";
                                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        InternetUrl.ServiceTYpe.URL + ima,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject object = new JSONObject(response);
                                                    Log.e("data", response);

                                                    if (object.names().get(0).equals("success")) {
                                                        AlertDialog.Builder enter = new AlertDialog.Builder(getContext());
                                                        enter.setTitle("Insert Again");
                                                        enter.setPositiveButton("Yes", null);
                                                        enter.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                productList.clear();
                                                                loadProduct();
                                                                alertDialogCOLOR.dismiss();
                                                            }
                                                        });
                                                        AlertDialog dialog = enter.create();
                                                        dialog.show();


                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.getMessage();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("productid", spinnerIDcolor);
                                        map.put("color", prodCOLOR);
                                        return map;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            }

                            }


                    }
                });

            }
        });

        /*
         ****For adding the imagess of the product....................................///////
         */
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewImages = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_images ,null , false);

                imageID = viewImages.findViewById(R.id.spinnergetID);
                productIMAGEname = viewImages.findViewById(R.id.imageNAME);
                cancelIMAGE = viewImages.findViewById(R.id.cancelIMAGES);
                selectIMAGE = viewImages.findViewById(R.id.selectIMAGES);
                uploadIMAGE = viewImages.findViewById(R.id.uploadIMAGES);
                imageIMAGE = viewImages.findViewById(R.id.imageViewIMAGES);

                imageID.setAdapter(idAdapter);
                imageID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinnerIDimage = imageID.getItemAtPosition(imageID.getSelectedItemPosition()).toString();
                        imageIMAGE.setVisibility(View.VISIBLE);

                        {
                            String ima= "getProductImage.php";
                            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    InternetUrl.ServiceTYpe.URL + ima,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject object =new JSONObject(response);

                                                Glide.with(viewImages).load(InternetUrl.ServiceTYpe.URL + object.getString("image"))
                                                        //  .apply(requestOptions)
                                                        .into(imageView);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.getMessage();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String , String> map = new HashMap<>();
                                    map.put("id" , spinnerIDimage);
                                    return map;
                                }
                            };
                            requestQueue.add(stringRequest);

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                builderIMAGE = new AlertDialog.Builder(getContext());
                builderIMAGE.setView(viewImages);
                alertDialogIMAGE = builderIMAGE.create();
                alertDialogIMAGE.setCancelable(false);
                alertDialogIMAGE.show();

                cancelIMAGE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogIMAGE.dismiss();

                    }
                });

                selectIMAGE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent ,CAMERA_REQUEST);
                        productIMAGEname.setVisibility(View.VISIBLE);
                        uploadIMAGE.setVisibility(View.VISIBLE);
                    }
                });

                uploadIMAGE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        prodIMAGEname = productIMAGEname.getText().toString();
                        prodIMAGEnamelen = prodIMAGEname.trim().length();
                        if (prodIMAGEnamelen == 0){
                            productIMAGEname.setError("enter Product Name");
                        }
                        if (prodIMAGEnamelen != 0 ){
                            {    pd.show();

                                String ima = "uploadprodImages.php";
                                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        InternetUrl.ServiceTYpe.URL + ima,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject object = new JSONObject(response);
                                                    Log.e("data", response);

                                                    if (object.names().get(0).equals("success")) {
                                                        pd.dismiss();
                                                        AlertDialog.Builder enter = new AlertDialog.Builder(getContext());
                                                        enter.setTitle("Insert Again");
                                                        enter.setPositiveButton("Yes", null);
                                                        enter.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                productList.clear();
                                                                loadProduct();
                                                                alertDialogIMAGE.dismiss();
                                                            }
                                                        });
                                                        AlertDialog dialog = enter.create();
                                                        dialog.show();
                                                        productIMAGEname.setVisibility(View.GONE);
                                                        uploadIMAGE.setVisibility(View.GONE);


                                                    }

                                                } catch (Exception e) {
                                                    pd.dismiss();
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pd.dismiss();
                                        error.getMessage();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("productid", spinnerIDimage);
                                        map.put("name" , prodIMAGEname);
                                        map.put("image", encodedImages);
                                        return map;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            }

                        }
                    }
                });
            }
        });
        /*
         ****For adding product.....................................................................///////
         */

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_product_popup , null , false);


                productNAME = view1.findViewById(R.id.enterPRODUCTname);
                productPRICE =view1.findViewById(R.id.enterPRODUCTprice);
                productDESC = view1.findViewById(R.id.enterPRODUCTdesc);
                productTAGS = view1.findViewById(R.id.enterPRODUCTtags);
                productCategory = view1.findViewById(R.id.enterPRODUCTcategory);
                imageView = view1.findViewById(R.id.viewProductImage);
                selectProduct = view1.findViewById(R.id.selectIMAGES);
                cancelAdd = view1.findViewById(R.id.cancelUpload);
                addProducts= view1.findViewById(R.id.uploadProduct);

                adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                        R.layout.support_simple_spinner_dropdown_item , category);
                productCategory.setAdapter(adapter);

                productCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      spinnerValue = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(getContext(), spinnerValue, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                 builder = new AlertDialog.Builder(getContext());
                builder.setView(view1);
                alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                selectProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageView.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent ,STRING_REQUEST);
                    }
                });

                cancelAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                addProducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prodNAME= productNAME.getText().toString();
                        prodDESC = productDESC.getText().toString();
                        prodPRICE = productPRICE.getText().toString();
                        prodTAGS = productTAGS.getText().toString();

                        prodNAMElen = prodNAME.trim().length();
                        prodDESClen = prodDESC.trim().length();
                        prodPRICElen = prodPRICE.trim().length();
                        prodTAGSlen = prodTAGS.trim().length();

                        if (prodNAMElen == 0 || prodPRICElen == 0 || prodDESClen == 0 || prodTAGSlen == 0 ){
                            if (prodNAMElen == 0){
                                productNAME.setError("Enter Product Name");
                            }
                            if (prodPRICElen == 0){
                                productPRICE.setError("Enter Product Price");
                            }
                            if (prodDESClen == 0 ){
                                productDESC.setError("Description Length Zero");
                            }

                            if (prodTAGSlen == 0){
                                productTAGS.setError("Enter Product Tags");
                            }

                        }
                        if (prodNAMElen != 0 && prodTAGSlen != 0 && prodPRICElen != 0 && prodDESClen != 0 ){

                            addProduct();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STRING_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            Uri photoUri = data.getData();
            if (photoUri != null){
                try{
                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(),photoUri);
                    imageView.setImageBitmap(currentImage);



                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    currentImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] imageByes =byteArrayOutputStream.toByteArray();
                    encodedImages = Base64.encodeToString(imageByes, Base64.DEFAULT);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            Uri photoUri = data.getData();
            if (photoUri != null){
                try{
                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(),photoUri);
                    imageIMAGE.setImageBitmap(currentImage);



                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    currentImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] imageByes =byteArrayOutputStream.toByteArray();
                    encodedImages = Base64.encodeToString(imageByes, Base64.DEFAULT);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }  //closes FAB submenus
    private void closeSubMenusFab(){
        layoutAddSize.setVisibility(View.INVISIBLE);
        layoutAddProd.setVisibility(View.INVISIBLE);
        layoutAddImage.setVisibility(View.INVISIBLE);
        layoutAddColor.setVisibility(View.INVISIBLE);
        addPro.setImageResource(R.drawable.ic_add_white_24dp);

        fabExpanded = false;
    }

    //open FAB submenu
    private  void openSubMenusFab(){

        layoutAddSize.setVisibility(View.VISIBLE);
        layoutAddProd.setVisibility(View.VISIBLE);
        layoutAddImage.setVisibility(View.VISIBLE);
        layoutAddColor.setVisibility(View.VISIBLE);
        addPro.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }

    public void loadProduct(){
        productList = new ArrayList<>();
        String lin ="adminpanel/getAllproduct.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("resps" , response);


                try {
                    JSONArray array1 = new JSONArray(response);

                    for (int i = 0; i < array1.length(); i++) {

                        JSONObject obj1 = array1.getJSONObject(i);
                        Product product = new Product();
                        List<String> colorList, sizeList;
                        colorList = new ArrayList<>();
                        sizeList = new ArrayList<>();
                        product.setId(obj1.getInt("id"));
                        product.setTitle(obj1.getString("name"));
                        product.setAllImage(obj1.getString("image"));
                        product.setPrice(obj1.getDouble("price"));
                     //   product.setColor(obj1.getString("color"));
                     //   product.setSize(obj1.getString("size"));

                      //  JSONArray array = new JSONArray(obj1.get("color"));
                       JSONArray array = obj1.getJSONArray("color");

                        for (int j = 0; j < array.length(); j++) {

                            JSONObject object = array.getJSONObject(j);

                            colorList.add(object.getString("color"));


                        }


                        product.setColorList(colorList);
                        JSONArray array2 = obj1.getJSONArray("size");
                        for (int k = 0; k < array2.length(); k++) {
                            JSONObject object = array2.getJSONObject(k);
                            sizeList.add(object.getString("size"));

                        }

                        product.setSizeList(sizeList);
                        productList.add(product);

                    }

             //       ArrayAdapter<String> adaptercol = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),R.layout.support_simple_spinner_dropdown_item,colorList);
             //       ArrayAdapter<String> adaptersiz = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),R.layout.support_simple_spinner_dropdown_item,sizeList);

                    adapterView =  new ViewProductAdapter(getContext(),productList);
                    adapterView.notifyDataSetChanged();
                    productRecycler.setAdapter(adapterView);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar=  Snackbar.make(Objects.requireNonNull(getView()),"No Internet Connection",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        requestQueue.add(stringRequest);
    }
    @SuppressLint("ObsoleteSdkInt")
    public void notification(){
        int notify = 1;
        mNotificationManager = (NotificationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("New Product Available")
                .setAutoCancel(true)
                .setContentText("Testing Products");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String channelId1 = "1";
            String channelName1 = "channel1";

                NotificationChannel channel = new NotificationChannel(channelId1 , channelName1 , NotificationManager.IMPORTANCE_DEFAULT);

                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.setShowBadge(true);
                channel.enableVibration(true);

                builder.setChannelId(channelId1);

                if (mNotificationManager != null){
                    mNotificationManager.createNotificationChannel(channel);
                }

        }
        else
        {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        }

        Intent intent = new Intent(getActivity(), AdminDashboard.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Objects.requireNonNull(getContext()));
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent( 1, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        if (mNotificationManager != null){
            mNotificationManager.notify(notify , builder.build());
        }



    }

    public void addProduct(){
        pd.show();
        String addProduct = "insertprod.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + addProduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("resp" , response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")){
                                pd.dismiss();
                                Toast.makeText(getContext(), "result " + response,  Toast.LENGTH_SHORT).show();
                                productList.clear();
                                loadProduct();
                                alertDialog.dismiss();
                                notification();


                            }

                        }catch (Exception e)
                        {
                            pd.dismiss();
                            alertDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                alertDialog.dismiss();
                pd.dismiss();
                Log.e("volley add pro" , error.toString());
                Toast.makeText(getContext(), "Volley Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> map = new HashMap<>();
                map.put("name" , prodNAME);
                map.put("image" , encodedImages);
                map.put("price" , prodPRICE);
                map.put("longdesc" , prodDESC);
                map.put("tags", prodTAGS);
                map.put("category" , spinnerValue);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getProductIds(){
        String pIds = "getproductid.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + pIds,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for(int i = 0 ; i < array.length() ; i++){
                                JSONObject object = array.getJSONObject(i);
                                idList.add(object.getString("id"));

                            }
                            idAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, idList);



                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error" , error.toString());

            }
        });

        requestQueue.add(stringRequest);
    }


    public void getImage(final String id){

        String ima= "getProductImage.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InternetUrl.ServiceTYpe.URL + ima,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object =new JSONObject(response);
                             decodedImages = object.getString("image");

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> map = new HashMap<>();
                map.put("id" , id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
