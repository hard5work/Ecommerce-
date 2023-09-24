package com.example.anis.ecommerce.adminadapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.adapter.InternetUrl;
import com.example.anis.ecommerce.adapter.Product;

import java.util.List;

public class ViewUserAdapter extends RecyclerView.Adapter<ViewUserAdapter.ViewUserAdapterView>{
    List<Product> productList;
    Context mContext;

    public ViewUserAdapter( Context mContext, List<Product> productList) {
        this.productList = productList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewUserAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.admin_user_cardview, null , false);
        ViewUserAdapterView mHolder = new ViewUserAdapterView(view);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUserAdapterView holder, int position) {
        int pos = holder.getAdapterPosition();
        final Product product = productList.get(pos);

        holder.id.setText(String.valueOf(product.getId()));
        if ( product.getMidname() == null){
            String name = product.getFirstname() + " " + product.getLastname();
            holder.name.setText(name);
        }

        if (product.getMidname() != null){
            String name = product.getFirstname() + " " + product.getMidname() + " " + product.getLastname();
            holder.name.setText(name);
        }

        holder.email.setText(product.getEmail());

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(mContext)
                .load(InternetUrl.ServiceTYpe.URL + product.getAllImage())
                .apply(requestOptions)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userPic);
        holder.contact.setText(product.getContact());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewUserAdapterView extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView id,name,email,contact;
        ImageView userPic;

        public ViewUserAdapterView(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.userCARD);
            userPic = itemView.findViewById(R.id.userIMAGE);
            id = itemView.findViewById(R.id.userID);
            name = itemView.findViewById(R.id.userFULLNAME);
            email = itemView.findViewById(R.id.userEMAIL);
            contact = itemView.findViewById(R.id.userCONTACT);
        }
    }
}
