package com.thuctap.NOID.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thuctap.NOID.Database.DBProduct;
import com.thuctap.NOID.GUI.DetailActivity;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.List;

import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<DBProduct> productList;
    private DatabaseReference databaseReference;

    public ProductAdapter(List<DBProduct> productList) {

        this.productList = productList;
        databaseReference = FirebaseDatabase.getInstance().getReference("sanpham");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DBProduct dbProduct = productList.get(position);

        holder.txtProductName.setText(dbProduct.getName());
        double priceDouble = Double.parseDouble(dbProduct.getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedGiatiensp = decimalFormat.format(priceDouble);
        holder.txtProductPrice.setText(formattedGiatiensp);

        String imageUrl = dbProduct.getImageURl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(R.drawable.cafe_2);
        }

        holder.llnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("productId", dbProduct.getId());
                intent.putExtra("productName", dbProduct.getName());
                intent.putExtra("productDesc", dbProduct.getDesc());
                intent.putExtra("productPrice", dbProduct.getPrice());
                intent.putExtra("productImage", dbProduct.getImageURl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice;
        ImageView imgProduct;
        LinearLayout llnAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            llnAdd = itemView.findViewById(R.id.llnAdd);
        }
    }
}
