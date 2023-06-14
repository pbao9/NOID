package com.thuctap.NOID.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.R;

import java.util.List;

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.MyViewHolder> {
    private List<DBCart> cartList;

    public HistoryDetailAdapter(List<DBCart> cartList) {
        this.cartList = cartList;
    }

    public void setCartList(List<DBCart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DBCart cart = cartList.get(position);

        holder.txtProductName.setText(cart.getTensp());
        holder.txtProductPrice.setText(String.valueOf(cart.getGiasp()));
        holder.txtProductQuantity.setText(String.valueOf(cart.getSoluong()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice, txtProductQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductQuantity = itemView.findViewById(R.id.txtProductCartCount);
        }
    }
}

