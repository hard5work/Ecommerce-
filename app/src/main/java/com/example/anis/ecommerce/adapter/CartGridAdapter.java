package com.example.anis.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anis.ecommerce.image_view.FullImageActivity;
import com.example.anis.ecommerce.R;

import java.util.List;


public class CartGridAdapter extends RecyclerView.Adapter<CartGridAdapter.ProductViwHolder> {
    private Context mContext;
    private List<Product> mProductList;

    public CartGridAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public ProductViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_view, parent, false);
        return new ProductViwHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViwHolder holder, int position) {
        holder.imgView.setImageDrawable(mContext.getResources().getDrawable(mProductList.get(position).getImage()));
        holder.itemName.setText(mProductList.get(position).getTitle());
        holder.itemPrice.setText(String.valueOf(mProductList.get(position).getPrice()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext,FullImageActivity.class);
                mIntent.putExtra("Title", mProductList.get(holder.getAdapterPosition()).getTitle());
                mIntent.putExtra("Image",mProductList.get(holder.getAdapterPosition()).getImage());
                mIntent.putExtra("Price", mProductList.get(holder.getAdapterPosition()).getPrice());
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
    class ProductViwHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPrice;
        Button btnCrat;
        ImageView imgView;
        CardView cardView;

        public ProductViwHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.galleryImage);
            itemName = (TextView)itemView.findViewById(R.id.galleryItemTitle);
            itemPrice = (TextView) itemView.findViewById(R.id.galleryPrice);
            btnCrat = (Button) itemView.findViewById(R.id.galleryCart);
            cardView = (CardView) itemView.findViewById(R.id.galleryCardView);
        }
    }
}
