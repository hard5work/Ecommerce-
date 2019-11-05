package com.example.anis.ecommerce.adminadapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;
import com.example.anis.ecommerce.login_stuff.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProductAdapter extends RecyclerView.Adapter<ViewProductAdapter.ViewProductAdapterView>{

    private Context mContext;
    private List<Product> productList;
    ArrayAdapter<String> adapterCol, adapterSiz;

   /* public ViewProductAdapter(Context mContext, List<Product> productList, List<String> colorList, List<String> sizeList) {
        this.mContext = mContext;
        this.productList = productList;
        this.colorList = colorList;
        this.sizeList = sizeList;
    }*/

  /*  public ViewProductAdapter(Context mContext, List<Product> productList, ArrayAdapter<String> adapterCol, ArrayAdapter<String> adapterSiz) {
        this.mContext = mContext;
        this.productList = productList;
        this.adapterCol = adapterCol;
        this.adapterSiz = adapterSiz;
    }*/
    public ViewProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewProductAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.admin_view_product_cardview , null ,false);
        ViewProductAdapterView mholder= new ViewProductAdapterView(view);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewProductAdapterView holder, int position) {
        final int pos = holder.getAdapterPosition();
        final Product product = productList.get(pos);



        Glide.with(mContext).load(InternetUrl.ServiceTYpe.URL + product.getAllImage()).into(holder.imageView);

        holder.viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "ShOw Product Detail", Toast.LENGTH_SHORT).show();

            }
        });
        holder.id.setText(String.valueOf(product.getId()));
        holder.title.setText(product.getTitle());
        String pricee = "Rs " + String.valueOf(product.getPrice());
        holder.price.setText(pricee);



        final String productid = String.valueOf(product.getId());


            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    product.getColorList());
                    holder.color.setAdapter(adapter);

       //     holder.color.setAdapter(new ArrayAdapter<>(mContext,R.layout.support_simple_spinner_dropdown_item,product.getColorList()));

     //   holder.color.setAdapter(adapterCol);
            holder.color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    holder.color.getItemAtPosition(holder.color.getSelectedItemPosition()).toString();
                    ((TextView) holder.color.getChildAt(0)).setGravity(Gravity.CENTER);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });




            holder.viewCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(mContext,holder.viewCard);
                    popupMenu.getMenuInflater().inflate(R.menu.long_press_admin,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Toast.makeText(mContext, "item Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            if(menuItem.getItemId() == R.id.updateTable){
                                Toast.makeText(mContext, "Update Table", Toast.LENGTH_SHORT).show();

                            }

                            if (menuItem.getItemId() == R.id.deleteTable){
                                Toast.makeText(mContext, "Delete Table", Toast.LENGTH_SHORT).show();
                            }


                            return true;
                        }
                    });
                    popupMenu.show();

        return false;
                }
            });



            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(mContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    product.getSizeList());
            holder.size.setAdapter(adapter2);

     //   holder.size.setAdapter(adapterSiz);

        holder.size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    holder.size.getItemAtPosition(holder.size.getSelectedItemPosition()).toString();
                    ((TextView) holder.size.getChildAt(0)).setGravity(Gravity.CENTER);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });





      /*  {
            colorList = new ArrayList<>();
            String lin = "fulldetail/viewProductColor.php";
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i  = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            String prod;
                            prod = jsonObject.getString("color");
                            colorList.add(prod);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                                R.layout.support_simple_spinner_dropdown_item,
                                colorList);
                        holder.color.setAdapter(adapter);

                       holder.color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                holder.color.getItemAtPosition(holder.color.getSelectedItemPosition()).toString();
                                ((TextView) holder.color.getChildAt(0)).setGravity(Gravity.CENTER);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });




                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("productid" , productid);
                    return map;
                }
            };

            requestQueue.add(stringRequest);

        }




        {
            sizeList = new ArrayList<>();
            String lin = "fulldetail/viewProductSize.php";
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, InternetUrl.ServiceTYpe.URL + lin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i  = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            String prod;
                            prod = jsonObject.getString("size");
                            sizeList.add(prod);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,R.layout.support_simple_spinner_dropdown_item,sizeList);
                        holder.size.setAdapter(adapter);
                        holder.size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                holder.size.getItemAtPosition(holder.size.getSelectedItemPosition()).toString();
                                ((TextView) holder.size.getChildAt(0)).setGravity(Gravity.CENTER);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });



                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("productid" , productid);
                    return map;
                }
            };

            requestQueue.add(stringRequest);

        }
*/





    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewProductAdapterView extends RecyclerView.ViewHolder {

        CardView viewCard;
        TextView title, id, price;
        Spinner color,size;
        ImageView imageView;
        public ViewProductAdapterView(View itemView) {
            super(itemView);

            viewCard = itemView.findViewById(R.id.view_product_cardview);
            id = itemView.findViewById(R.id.productId);
            title = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            imageView= itemView.findViewById(R.id.productImage);
            color = itemView.findViewById(R.id.viewCOlorSpinner);
            size = itemView.findViewById(R.id.viewSizeSpinner);
        }
    }
}
