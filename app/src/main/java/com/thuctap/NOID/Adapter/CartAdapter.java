package com.thuctap.NOID.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<DBCart> cartItems;
    private DecimalFormat decimalFormat;

    public CartAdapter(ArrayList<DBCart> cartItems) {
        this.cartItems = cartItems;
        decimalFormat = new DecimalFormat("#,### đ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBCart cartItem = cartItems.get(position);
        holder.productCartName.setText(cartItem.getProductName());
        holder.productCartCount.setText("(" + String.valueOf(cartItem.getProductCount()) + "x) ");

        int totalProductPrice = Integer.parseInt(cartItem.getProductPrice());
        int updatedPrice = Integer.parseInt(cartItem.getProductPrice()) * cartItem.getProductCount();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(updatedPrice);
        holder.productCartPrice.setText(formattedPrice);

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productCartName, productCartCount, productCartPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartName = itemView.findViewById(R.id.txtProductCartName);
            productCartCount = itemView.findViewById(R.id.txtProductCartCount);
            productCartPrice = itemView.findViewById(R.id.txtProductCartPrice);
        }


    }
}
