package com.thuctap.NOID.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thuctap.NOID.Database.DBCart;
import com.thuctap.NOID.R;

import java.text.DecimalFormat;
import java.util.List;

public class PendingDetailAdapter extends RecyclerView.Adapter<PendingDetailAdapter.ViewHolder>{
    private List<DBCart> cartList;

    public PendingDetailAdapter(List<DBCart> cartList) {
        this.cartList = cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductCartCountPending, txtProductCartNamePending, txtProductCartPricePending, txtPriceBeforePending;

        public ViewHolder(View itemView) {
            super(itemView);
            txtProductCartCountPending = itemView.findViewById(R.id.txtProductCartCount);
            txtProductCartNamePending = itemView.findViewById(R.id.txtProductCartName);
            txtProductCartPricePending = itemView.findViewById(R.id.txtProductCartPrice);
            txtPriceBeforePending = itemView.findViewById(R.id.txtPriceBefore);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBCart cartItem = cartList.get(position);
        holder.txtProductCartCountPending.setText(String.valueOf(cartItem.getSoluong()) + "x");
        holder.txtProductCartNamePending.setText(cartItem.getTensp());

        double price = cartItem.getGiasp() * cartItem.getSoluong();
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        String formattedPrice = decimalFormat.format(price);
        holder.txtProductCartPricePending.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
