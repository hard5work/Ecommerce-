package com.example.anis.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anis.ecommerce.R;
import com.example.anis.ecommerce.category_stuff.MainActivity;
import com.example.anis.ecommerce.category_stuff.MensWearActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViwHolder> {
    private Context mContext;
    private List<Product> mProductList;
    MainActivity ma;

    public CategoryAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public CategoryViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_grid_view, parent, false);
        return new CategoryViwHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViwHolder holder, final int position) {

        holder.itemName.setText(mProductList.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if(pos == 0)
                {
                    Intent one = new Intent(mContext,MensWearActivity.class);
                    mContext.startActivity(one);

                }
                if(pos == 1)
                {
                    Intent one = new Intent(mContext,MainActivity.class);
                    mContext.startActivity(one);
                }if(pos == 2)
                {
                    Intent one = new Intent(mContext,MainActivity.class);
                    mContext.startActivity(one);
                }if(pos == 3)
                {
                    Toast.makeText(mContext, "I am Coming soon", Toast.LENGTH_SHORT).show();
                }if(pos == 4)
                {
                    Toast.makeText(mContext, "I am Coming soon", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgView.setImageDrawable(mContext.getResources().getDrawable(mProductList.get(position).getImage()));



    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

class CategoryViwHolder extends RecyclerView.ViewHolder{
    TextView itemName;
    ImageView imgView;
    CardView cardView;

    public CategoryViwHolder(View itemView) {
        super(itemView);
        imgView = (ImageView) itemView.findViewById(R.id.cImage);
        itemName = (TextView)itemView.findViewById(R.id.cTitle);
        cardView = (CardView) itemView.findViewById(R.id.catcardView);
    }
}
}
