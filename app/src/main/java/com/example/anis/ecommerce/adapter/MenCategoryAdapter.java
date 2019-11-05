package com.example.anis.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.category_stuff.MenCategoryClass;
import com.example.anis.ecommerce.category_stuff.MensWearActivity;

import java.util.List;

public class MenCategoryAdapter extends RecyclerView.Adapter<MenCategoryAdapter.CategoryMenViwHolder> {
    private Context mContext;
    private List<Product> mProductList;
    MainActivity ma;

    public MenCategoryAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public CategoryMenViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menscategory_cart_item, parent, false);
        return new CategoryMenViwHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryMenViwHolder holder, int position) {



        holder.itemName.setText(mProductList.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = holder.getAdapterPosition();
                if(pos == 0)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "0");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);

                }
                if(pos == 1)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "1");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 2)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "2");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 3)
                { Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "3");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 4)
                { Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "4");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
                if(pos == 5)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "5");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);

                }
                if(pos == 6)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "6");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 7)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "7");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 8)
                { Intent intent = new Intent(mContext,MenCategoryClass.class);

                    intent.putExtra("one" , "8");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }if(pos == 9)
                { Intent intent = new Intent(mContext,MenCategoryClass.class);

                    intent.putExtra("one" , "9");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
                if(pos == 10)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "10");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);

                }
                if(pos == 11)
                {
                    Intent intent = new Intent(mContext,MenCategoryClass.class);
                    intent.putExtra("one" , "11");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            }
        });


        holder.imgView.setImageDrawable(mContext.getResources().getDrawable(mProductList.get(position).getImage()));



    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class CategoryMenViwHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        ImageView imgView;
        CardView cardView;

        public CategoryMenViwHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.catmenImage);
            itemName = (TextView)itemView.findViewById(R.id.catmenTitle);
            cardView = (CardView) itemView.findViewById(R.id.mencatcardView);
        }
    }
}